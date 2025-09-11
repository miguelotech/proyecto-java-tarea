package com.nttdata.dockerized.postgresql.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso específico
 */
public class ResourceNotFoundException extends BaseException {
    
    public ResourceNotFoundException(String message, String entityType, Object entityId) {
        super(message, "RESOURCE_NOT_FOUND", entityType, entityId, "El recurso solicitado no existe");
    }
    
    public ResourceNotFoundException(String message, String entityType, Object entityId, String additionalInfo) {
        super(message, "RESOURCE_NOT_FOUND", entityType, entityId, additionalInfo);
    }
}


