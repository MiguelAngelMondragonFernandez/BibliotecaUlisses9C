package mx.edu.utez.controlaccess.service;

import lombok.Data;
import mx.edu.utez.controlaccess.model.UserBean;
import mx.edu.utez.controlaccess.model.UserDto;
import mx.edu.utez.controlaccess.utils.CustomResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
            if(users.isEmpty()){
                return customResponse.get400Response(400, "No hay usuarios", "No se encontraron usuarios");
            } else {
                return customResponse.get200Response("Usuarios Encontrados", users);
            }
        } catch (Exception e) {
            return customResponse.get500Response("Error", "error al obtener los usuarios");
        }
    }

    public ResponseEntity<?> getById(Long id) throws Exception {
        try{
            Optional<UserBean> user = service.findById(id);
            if(user.isEmpty()){
                return customResponse.get400Response(400, "No hay usuarios", "No se encontraron usuarios");
            } else {
                return customResponse.get200Response("Usuario Encontrado", user.get());
            }
        } catch (Exception e) {
            return customResponse.get500Response("Error al obtener el usuario", e.getMessage());
        }
    }

    public ResponseEntity<?> save(UserDto save){
        try{
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

    public ResponseEntity<?> update(UserBean user){
        try{
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
