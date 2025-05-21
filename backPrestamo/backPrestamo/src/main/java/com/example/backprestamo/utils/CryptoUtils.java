package com.example.backprestamo.utils;

import com.google.gson.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;

public class CryptoUtils {

    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(localDate.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public static String encrypt(String llave, String iv, Object objeto) throws Exception {
        if (llave == null || iv == null || llave.length() != 16 || iv.length() != 16) {
            throw new IllegalArgumentException("La llave y el IV deben tener exactamente 16 caracteres.");
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(llave.getBytes("UTF-8"), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);


        String json = gson.toJson(objeto);
        byte[] encrypted = cipher.doFinal(json.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static <T> Optional<T> decrypt(String textoCifrado, String llave, String iv, Class<T> clase) throws Exception {
        if (llave == null || iv == null || llave.length() != 16 || iv.length() != 16) {
            throw new IllegalArgumentException("La llave y el IV deben tener exactamente 16 caracteres.");
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(llave.getBytes("UTF-8"), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(textoCifrado);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        String json = new String(decryptedBytes, "UTF-8");
        Gson gson = new Gson();
        return Optional.ofNullable(gson.fromJson(json, clase));
    }
}
