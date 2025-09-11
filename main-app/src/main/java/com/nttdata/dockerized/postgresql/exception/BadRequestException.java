package com.nttdata.dockerized.postgresql.exception;

/**
 * Excepción personalizada para solicitudes malformadas (400)
 */
public class BadRequestException extends BaseException {
    
    public BadRequestException(String message, String entityType, Object entityId) {
        super(message, "BAD_REQUEST", entityType, entityId, 
              String.format("Solicitud inválida para %s con ID %s", entityType, entityId));
    }
    
    public BadRequestException(String message, String entityType, Object entityId, String additionalInfo) {
        super(message, "BAD_REQUEST", entityType, entityId, additionalInfo);
    }
    
    public BadRequestException(String message, String entityType) {
        super(message, "BAD_REQUEST", entityType, null, 
              String.format("Solicitud inválida para %s", entityType));
    }
}
