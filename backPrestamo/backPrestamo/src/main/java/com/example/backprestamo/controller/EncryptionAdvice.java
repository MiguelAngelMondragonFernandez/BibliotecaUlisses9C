package com.example.backprestamo.controller;

import com.example.backprestamo.kernel.ApiResponse;
import com.example.backprestamo.utils.CryptoUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

@RestControllerAdvice
public class EncryptionAdvice implements ResponseBodyAdvice<Object> {

    private final String KEY = "HelloUnhappyReoN";
    private final String IV = "HelloUnhappyReoN";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // aplica a todas las respuestas
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof ApiResponse apiResponse) {
            try {
                if (apiResponse.getData() == null) {
                    return apiResponse; // no cifres si no hay data
                }
                System.out.println("Datos a Cifrar" + apiResponse.getData());
                Object data = apiResponse.getData();
                if (data instanceof Optional<?> optional) {
                    data = optional.orElse(null); // desenvuelve el Optional
                }
                String encrypted = CryptoUtils.encrypt(KEY, IV, data);
                apiResponse.setData(encrypted);
                return apiResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "EncryptionError");
            }
        }

        return body;
    }
}
