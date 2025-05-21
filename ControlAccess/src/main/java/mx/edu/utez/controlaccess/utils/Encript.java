package mx.edu.utez.controlaccess.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Optional;

@Service
public class Encript {

    public String encryptObject(Object objeto) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec("HelloUnhappyReoN".getBytes("UTF-8"), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("HelloUnhappyReoN".getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Convertir el objeto a JSON
        Gson gson = new Gson();
        String json = gson.toJson(objeto);
        byte[] encrypted = cipher.doFinal(json.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public Optional<?> decryptObject(String textoCifrado, Class clase) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec("HelloUnhappyReoN".getBytes("UTF-8"), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("HelloUnhappyReoN".getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(textoCifrado);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        String json = new String(decryptedBytes, "UTF-8");
        Gson gson = new Gson();
        return Optional.ofNullable(gson.fromJson(json, clase));
    }
    public String encryptValue(Optional<?> valor) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec("HelloUnhappyReoN".getBytes("UTF-8"), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("HelloUnhappyReoN".getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encrypted = cipher.doFinal(valor.get().toString().getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public <T> Optional<T> decryptValue(String textoCifrado, Class<T> clase) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec("HelloUnhappyReoN".getBytes("UTF-8"), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("HelloUnhappyReoN".getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(textoCifrado);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        ObjectMapper mapper = new ObjectMapper();
        return Optional.of(mapper.readValue(decryptedBytes, clase));
    }

}
