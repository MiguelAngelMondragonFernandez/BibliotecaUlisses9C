package mx.edu.utez.controlaccess.controller;

import lombok.Data;
import mx.edu.utez.controlaccess.model.RequestDto;
import mx.edu.utez.controlaccess.model.UserBean;
import mx.edu.utez.controlaccess.model.UserDto;
import mx.edu.utez.controlaccess.service.UserService;
import mx.edu.utez.controlaccess.utils.CustomResponse;
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
    private final CustomResponse customResponse;

    @GetMapping("/user/")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAll();
    }


    @GetMapping("/user/{idEncripted}")
    public ResponseEntity<?> getOneUser(@PathVariable String idEncripted) throws Exception {
        String idTemp = idEncripted.replace('_','/');
        Optional<Long> value = encriptTool.decryptValue(idTemp, Long.class);
        Long id;
        if(value.isPresent()){
            id = value.get();
        } else {
            return customResponse.get400Response(400, "Ha ocurrido un error al envíar el dato", "Bad Request" );
        }
        return userService.getById(id);
    }

    @PostMapping("/user/")
    public ResponseEntity<?> createUser(@RequestBody RequestDto req) throws Exception {
        Optional<?> object = encriptTool.decryptObject(req.getData(), UserDto.class);
        UserDto user = new UserDto();
        if(object.isPresent()){
            user = (UserDto) object.get();
        } else {
            return customResponse.get400Response(400, "Ha ocurrido un error al envíar el dato", "Bad Request" );
        }
        return userService.save(user);
    }

    @PutMapping("/user/{idEncripted}")
    public ResponseEntity<?> updateUser(@PathVariable String idEncripted, @RequestBody RequestDto req) throws Exception {
        Optional<?> object = encriptTool.decryptObject(req.getData(), UserDto.class);
        Optional<Long> value = encriptTool.decryptValue(idEncripted, Long.class);
        UserDto user;
        if(object.isPresent()){
            user = (UserDto) object.get();
        } else {
            return customResponse.get400Response(400, "Ha ocurrido un error al envíar la información", "Bad Request" );
        }
        Long id;
        if(value.isPresent()){
            id = value.get();
        } else {
            return customResponse.get400Response(400, "Ha ocurrido un error al envíar el dato", "Bad Request" );
        }
        return userService.update(user, id);
    }

    @DeleteMapping("/user/{idEncripted}")
    public ResponseEntity<?> deleteUser(@PathVariable String idEncripted) throws Exception {
        Optional<Long> value = encriptTool.decryptValue(idEncripted, Long.class);
        Long id = value.get();
        if (id == null) {
            return customResponse.get400Response(400, "No se encontró el usuario con ese id", "Bad Request" );
        }
        return userService.delete(id);
    }

}
