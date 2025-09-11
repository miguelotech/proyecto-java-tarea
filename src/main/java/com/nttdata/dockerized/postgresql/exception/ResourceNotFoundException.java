package com.nttdata.dockerized.postgresql.exception;

/**
 * Excepción personalizada para recursos no encontrados (404)
 */
public class ResourceNotFoundException extends BaseException {
    
    public ResourceNotFoundException(String message, String entityType, Object entityId) {
        super(message, "RESOURCE_NOT_FOUND", entityType, entityId, 
              String.format("El recurso %s con ID %s no fue encontrado", entityType, entityId));
    }
    
    public ResourceNotFoundException(String message, String entityType, Object entityId, String additionalInfo) {
        super(message, "RESOURCE_NOT_FOUND", entityType, entityId, additionalInfo);
    }
}
