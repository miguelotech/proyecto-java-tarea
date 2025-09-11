package com.crymuzz.pedido_microservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class CustomErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> fieldErrors;

    public CustomErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public CustomErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, Map<String, String> fieldErrors) {
        this(timestamp, status, error, message, path);
        this.fieldErrors = fieldErrors;
    }
}
