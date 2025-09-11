package com.nttdata.dockerized.postgresql.exception;

import java.util.List;

/**
 * Excepción personalizada para errores de validación de campos
 */
public class ValidationException extends BaseException {
    
    private final List<String> validationErrors;
    
    public ValidationException(String message, String entityType, List<String> validationErrors) {
        super(message, "VALIDATION_ERROR", entityType, null, 
              String.format("Errores de validación en %s: %s", entityType, String.join(", ", validationErrors)));
        this.validationErrors = validationErrors;
    }
    
    public ValidationException(String message, String entityType, Object entityId, List<String> validationErrors) {
        super(message, "VALIDATION_ERROR", entityType, entityId, 
              String.format("Errores de validación en %s con ID %s: %s", entityType, entityId, String.join(", ", validationErrors)));
        this.validationErrors = validationErrors;
    }
    
    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
