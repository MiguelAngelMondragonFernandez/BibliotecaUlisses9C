package mx.edu.utez.controlaccess.controller;

import lombok.Data;
import mx.edu.utez.controlaccess.model.RequestDto;
import mx.edu.utez.controlaccess.model.UserBean;
import mx.edu.utez.controlaccess.model.UserDto;
import mx.edu.utez.controlaccess.service.UserService;
import mx.edu.utez.controlaccess.utils.Encript;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Data
public class UserController {

    private final UserService userService;
    private final Encript encriptTool;

    @GetMapping("/user/")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAll();
    }


    @GetMapping("/user/{idEncripted}")
    public ResponseEntity<?> getOneUser(@PathVariable String idEncripted) throws Exception {
        Optional<Long> value = encriptTool.decryptValue(idEncripted, Long.class);
        Long id = (Long) value.get();
        System.out.println(id);
        return userService.getById(id);
    }

    @PostMapping("/user/")
    public ResponseEntity<?> createUser(@RequestBody RequestDto req) throws Exception {
        Optional<?> object = encriptTool.decryptObject(req.getData(), UserDto.class);
        UserDto user = (UserDto) object.get();
        return userService.save(user);
    }

    @PutMapping("/user/{idEncripted}")
    public ResponseEntity<?> updateUser(@PathVariable String idEncripted, @RequestBody RequestDto req) throws Exception {
        Optional<Long> value = encriptTool.decryptValue(idEncripted, Long.class);
        Long id = (Long) value.get();
        Optional<?> object = encriptTool.decryptObject(req.getData(), UserBean.class);
        UserBean user = (UserBean) object.get();
        return userService.update(user);
    }

    @DeleteMapping("/user/{idEncripted}")
    public ResponseEntity<?> deleteUser(@PathVariable String idEncripted) throws Exception {
        Optional<Long> value = encriptTool.decryptValue(idEncripted, Long.class);
        Long id = (Long) value.get();
        return userService.delete(id);
    }

}
