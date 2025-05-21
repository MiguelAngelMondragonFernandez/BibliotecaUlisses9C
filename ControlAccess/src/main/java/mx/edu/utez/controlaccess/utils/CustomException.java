package mx.edu.utez.controlaccess.utils;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int statusCode;

    public CustomException(String errorCode, int statusCode) {
        super(errorCode);
        this.statusCode = statusCode;
    }

}
