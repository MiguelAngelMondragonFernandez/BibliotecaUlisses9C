package mx.edu.utez.controlaccess.service;

import lombok.Data;
import mx.edu.utez.controlaccess.model.UserBean;
import mx.edu.utez.controlaccess.model.UserDto;
import mx.edu.utez.controlaccess.utils.CustomException;
import mx.edu.utez.controlaccess.utils.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserService{
    private final UserRepository service;
    private final CustomResponse customResponse;

    public ResponseEntity<?> getAll(){
        try{
            List<UserBean> users = service.findAll();
                return customResponse.get200Response("Usuarios Encontrados", users);
        } catch (Exception e) {
            return customResponse.get500Response("Error", "error al obtener los usuarios");
        }
    }

    public ResponseEntity<?> getById(Long id) {
        try{
            Optional<UserBean> user = service.findById(id);
            if(user.isPresent()){
                return customResponse.get200Response("Usuario encontrado", user.get());
            } else {
                return customResponse.get400Response(400, "No se encontró el usuario con ese id", "Bad Request" );
            }
        } catch (Exception e) {
            return customResponse.get500Response("Error", "error al obtener el usuario");
        }
    }

    public ResponseEntity<?> save(UserDto save){
        try{
            if(save.getEmail() == null || save.getPassword() == null || save.getState() == null){
                return customResponse.get400Response(400, "No pueden ir campos vacíos", "Bad Request" );
            }
            if(service.existsByEmail(save.getEmail())){
                return customResponse.get400Response(400, "Ya existe un usuario con ese correo", "Bad Request" );
            }

            // Validación de contraseña
            String password = save.getPassword();
            if (password.length() < 8 || password.length() > 20) {
                return customResponse.get400Response(400, "La contraseña debe tener entre 8 y 20 caracteres", "Bad Request");
            }
            if (!password.matches(".*[@$%&¿?*!].*")) {
                return customResponse.get400Response(400, "La contraseña debe contener al menos un caracter especial", "Bad Request");
            }
            if (!password.matches(".*[0-9].*")) {
                return customResponse.get400Response(400, "La contraseña debe contener al menos un número", "Bad Request");
            }
            if (!password.matches(".*[A-Z].*")) {
                return customResponse.get400Response(400, "La contraseña debe contener al menos una letra mayúscula", "Bad Request");
            }

            // Validación de correo
            String email = save.getEmail();
            if (email == null || email.isEmpty()) {
                return customResponse.get400Response(400, "El correo no puede estar vacío", "Bad Request");
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@utez\\.edu\\.mx$")) {
                return customResponse.get400Response(400, "El correo no es válido", "Bad Request");
            }
            UserBean user = new UserBean();
            user.setEmail(save.getEmail());
            user.setPassword(save.getPassword());
            user.setState(save.getState());
            UserBean savedUser = service.saveAndFlush(user);
            return customResponse.get200Response("Usuario guardado", savedUser);
        } catch (Exception e) {
            return customResponse.get500Response("Error", "error al guardar el usuario");
        }
    }

    public ResponseEntity<?> update(UserBean user, Long id){
        try{
            Optional<UserBean> userOptional = service.findById(id);
            if(userOptional.isEmpty()){
                return customResponse.get400Response(400, "No se encontró el usuario con ese id", "Bad Request" );
            }
            UserBean updatedUser = service.saveAndFlush(user);
            return customResponse.get200Response("Usuario actualizado", updatedUser);
        } catch (Exception e) {
            return customResponse.get500Response("Error", "error al actualizar el usuario");
        }
    }

    public ResponseEntity<?> delete(Long id){
        try{
            Optional<UserBean> user = service.findById(id);
            if(user.isPresent()){
                service.deleteById(id);
                return customResponse.get200Response("Usuario eliminado", user);
            } else {
                return customResponse.get400Response(400, "No hay usuarios", "No se encontraron usuarios");
            }
        } catch (Exception e) {
            return customResponse.get500Response("Error", "error al eliminar el usuario");
        }
    }


}
