package mx.edu.utez.Servicio.Biblioteca_Categorias.utils;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int statusCode;

    public CustomException(String errorCode, int statusCode) {
        super(errorCode);
        this.statusCode = statusCode;
    }

}

