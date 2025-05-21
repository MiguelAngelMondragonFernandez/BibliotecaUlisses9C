package mx.edu.utez.controlaccess.service;

import jakarta.transaction.Transactional;
import mx.edu.utez.controlaccess.model.UserBean;
import mx.edu.utez.controlaccess.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RecoveryService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CustomResponse customResponse;

    @Autowired
    private UserRepository repository;

    public RecoveryService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Transactional
    public ResponseEntity<?> sendRecoveryEmail(String mail) throws Exception{
        UserBean user = repository.findByEmail(mail);
        if(user == null) {
            return customResponse.get400Response(400, "Ha ocurrido un error enviando el correo", "Bad Request");
        } else {
            String to = user.getEmail();
            String subject = "Password Recovery";
            String text = "Please click the link to reset your password.\n"+
                    "http://localhost:5173/reset-password/"+user.getToken_id().getToken()+"\n" +
                    "If you did not request this email, please ignore it.\n" ;

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom("20223tn026+noreply@utez.edu.mx");

            mailSender.send(message);
            return customResponse.get200Response("El correo fue enviado correctamente", null);
        }
    }

}
