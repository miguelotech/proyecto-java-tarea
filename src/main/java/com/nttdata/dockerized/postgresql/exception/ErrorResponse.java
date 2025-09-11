package com.nttdata.dockerized.postgresql.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Clase para representar respuestas de error estandarizadas
 */
@Data
@Builder
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String errorCode;
    private String entityType;
    private Object entityId;
    private String additionalInfo;
    private List<String> validationErrors;
    private Map<String, String> fieldErrors;
    private String path;
}
