package mx.edu.utez.controlaccess.utils;


public class CustomException extends RuntimeException {

    private final int statusCode;

    public CustomException(String errorCode, int statusCode) {
        super(errorCode);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
