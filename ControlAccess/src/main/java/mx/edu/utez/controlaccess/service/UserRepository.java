package mx.edu.utez.controlaccess.service;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.edu.utez.controlaccess.model.UserBean;

public interface UserRepository extends JpaRepository<UserBean, Long> {

    boolean existsByEmail(String email);

}
