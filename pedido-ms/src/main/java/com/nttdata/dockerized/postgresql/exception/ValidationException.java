package com.nttdata.dockerized.postgresql.exception;

import lombok.Getter;

import java.util.List;

/**
 * Excepción lanzada cuando fallan las validaciones de negocio
 */
@Getter
public class ValidationException extends BaseException {
    
    private final List<String> validationErrors;
    
    public ValidationException(String message, String entityType, Object entityId, List<String> validationErrors) {
        super(message, "VALIDATION_ERROR", entityType, entityId, "Error de validación en los datos proporcionados");
        this.validationErrors = validationErrors;
    }
    
    public ValidationException(String message, String entityType, Object entityId, String additionalInfo, List<String> validationErrors) {
        super(message, "VALIDATION_ERROR", entityType, entityId, additionalInfo);
        this.validationErrors = validationErrors;
    }
}


