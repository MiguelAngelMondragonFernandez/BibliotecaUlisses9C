package mx.edu.utez.controlaccess.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomResponse {
    private Map<String, Object> body;

    private Encript encript = new Encript();

    public ResponseEntity<?> getOkResponse(Object data) throws Exception {
        body = new HashMap<>();
        body.put("message", "Operación exitosa");
        body.put("status", "OK");
        if(data != null) {
            body.put("data", encript.encryptObject(data));
        }

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<?> getCreatedResponse(Object message) {
        body = new HashMap<>();
        body.put("message", message);
        body.put("status", "CREATED");

        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    public ResponseEntity<?> get200Response(String message, Object data) throws Exception {
        body = new HashMap<>();
        body.put("message", message);
        body.put("status", "OK");
        body.put("data", encript.encryptObject(data));

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<?> get204Response(Object message) {
        body = new HashMap<>();
        body.put("message", message);
        body.put("status", "NO CONTENT");

        return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> get500Response(String title, String message) {
        body = new HashMap<>();
        body.put("message", title);
        body.put("details", message);
        body.put("status", "INTERNAL SERVER ERROR");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> get401Response(Object message) {
        body = new HashMap<>();
        body.put("message", message);
        body.put("status", "UNAUTHORIZED");

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> get403Response(Object message) {
        body = new HashMap<>();
        body.put("message", message);
        body.put("status", "FORBIDDEN");

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> get404Response(Integer code, String title, String message) {
        body = new HashMap<>();
        body.put("message", title);
        body.put("details", message);
        body.put("status", "NOT FOUND");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> get400Response(int code, String message, String status) {
        body = new HashMap<>();
        body.put("code", code);
        body.put("message", message);
        body.put("status", status);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
