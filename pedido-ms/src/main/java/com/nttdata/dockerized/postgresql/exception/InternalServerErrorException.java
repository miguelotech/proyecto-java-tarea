package com.nttdata.dockerized.postgresql.exception;

/**
 * Excepción lanzada cuando ocurre un error interno del servidor
 */
public class InternalServerErrorException extends BaseException {
    
    public InternalServerErrorException(String message, String entityType, Object entityId) {
        super(message, "INTERNAL_SERVER_ERROR", entityType, entityId, "Ha ocurrido un error interno del servidor");
    }
    
    public InternalServerErrorException(String message, String entityType, Object entityId, String additionalInfo) {
        super(message, "INTERNAL_SERVER_ERROR", entityType, entityId, additionalInfo);
    }
    
    public InternalServerErrorException(String message, Throwable cause, String entityType, Object entityId) {
        super(message, cause, "INTERNAL_SERVER_ERROR", entityType, entityId, "Ha ocurrido un error interno del servidor");
    }
}


