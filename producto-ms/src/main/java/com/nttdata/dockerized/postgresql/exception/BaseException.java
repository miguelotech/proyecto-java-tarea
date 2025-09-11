package com.nttdata.dockerized.postgresql.exception;

import lombok.Getter;

/**
 * Clase base para excepciones personalizadas del sistema
 * Incluye parámetros adicionales para mayor información de contexto
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    private final String errorCode;
    private final String entityType;
    private final Object entityId;
    private final String additionalInfo;
    
    protected BaseException(String message, String errorCode, String entityType, Object entityId, String additionalInfo) {
        super(message);
        this.errorCode = errorCode;
        this.entityType = entityType;
        this.entityId = entityId;
        this.additionalInfo = additionalInfo;
    }
    
    protected BaseException(String message, Throwable cause, String errorCode, String entityType, Object entityId, String additionalInfo) {
        super(message, cause);
        this.errorCode = errorCode;
        this.entityType = entityType;
        this.entityId = entityId;
        this.additionalInfo = additionalInfo;
    }
}


