package com.example.backprestamo.kernel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private Object data;
    private HttpStatus status;
    private boolean error;
    private String message;

    //RECIBE LA INFO
    public ApiResponse(Object data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    //NO RECIBE LA INFO, ERROR
    public ApiResponse(HttpStatus status, boolean error ,String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
