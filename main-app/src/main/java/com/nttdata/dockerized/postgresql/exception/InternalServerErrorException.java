package com.nttdata.dockerized.postgresql.exception;

/**
 * Excepción personalizada para errores internos del servidor (500)
 */
public class InternalServerErrorException extends BaseException {
    
    public InternalServerErrorException(String message, String entityType, Object entityId) {
        super(message, "INTERNAL_SERVER_ERROR", entityType, entityId, 
              String.format("Error interno del servidor procesando %s con ID %s", entityType, entityId));
    }
    
    public InternalServerErrorException(String message, String entityType, Object entityId, String additionalInfo) {
        super(message, "INTERNAL_SERVER_ERROR", entityType, entityId, additionalInfo);
    }
    
    public InternalServerErrorException(String message, String entityType, Throwable cause) {
        super(message, cause, "INTERNAL_SERVER_ERROR", entityType, null, 
              String.format("Error interno del servidor procesando %s", entityType));
    }
    
    public InternalServerErrorException(String message, String entityType, Object entityId, Throwable cause) {
        super(message, cause, "INTERNAL_SERVER_ERROR", entityType, entityId, 
              String.format("Error interno del servidor procesando %s con ID %s", entityType, entityId));
    }
}
