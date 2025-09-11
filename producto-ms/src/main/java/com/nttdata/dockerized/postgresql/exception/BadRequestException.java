package com.nttdata.dockerized.postgresql.exception;

/**
 * Excepción lanzada cuando la solicitud es malformada o contiene datos inválidos
 */
public class BadRequestException extends BaseException {
    
    public BadRequestException(String message, String entityType, Object entityId) {
        super(message, "BAD_REQUEST", entityType, entityId, "La solicitud contiene datos inválidos");
    }
    
    public BadRequestException(String message, String entityType, Object entityId, String additionalInfo) {
        super(message, "BAD_REQUEST", entityType, entityId, additionalInfo);
    }
}


