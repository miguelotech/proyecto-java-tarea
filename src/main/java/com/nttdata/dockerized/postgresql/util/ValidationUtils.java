package com.nttdata.dockerized.postgresql.util;

import com.nttdata.dockerized.postgresql.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utilidades para validaciones de campos
 */
public class ValidationUtils {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[+]?[0-9\\s\\-()]{7,15}$"
    );
    
    /**
     * Valida que un campo no sea nulo o vacío
     */
    public static void validateNotNullOrEmpty(String value, String fieldName, String entityType) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(
                String.format("El campo '%s' es requerido", fieldName),
                entityType,
                List.of(String.format("El campo '%s' no puede ser nulo o vacío", fieldName))
            );
        }
    }
    
    /**
     * Valida que un campo numérico sea positivo
     */
    public static void validatePositiveNumber(Number value, String fieldName, String entityType) {
        if (value == null || value.doubleValue() <= 0) {
            throw new ValidationException(
                String.format("El campo '%s' debe ser un número positivo", fieldName),
                entityType,
                List.of(String.format("El campo '%s' debe ser mayor que 0", fieldName))
            );
        }
    }
    
    /**
     * Valida formato de email
     */
    public static void validateEmail(String email, String entityType) {
        if (email != null && !email.trim().isEmpty() && !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException(
                String.format("El formato del email '%s' no es válido", email),
                entityType,
                List.of("El email debe tener un formato válido (ejemplo@dominio.com)")
            );
        }
    }
    
    /**
     * Valida formato de teléfono
     */
    public static void validatePhone(String phone, String entityType) {
        if (phone != null && !phone.trim().isEmpty() && !PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException(
                String.format("El formato del teléfono '%s' no es válido", phone),
                entityType,
                List.of("El teléfono debe contener entre 7 y 15 dígitos")
            );
        }
    }
    
    /**
     * Valida longitud de string
     */
    public static void validateStringLength(String value, String fieldName, int minLength, int maxLength, String entityType) {
        if (value != null) {
            if (value.length() < minLength) {
                throw new ValidationException(
                    String.format("El campo '%s' debe tener al menos %d caracteres", fieldName, minLength),
                    entityType,
                    List.of(String.format("El campo '%s' es muy corto (mínimo %d caracteres)", fieldName, minLength))
                );
            }
            if (value.length() > maxLength) {
                throw new ValidationException(
                    String.format("El campo '%s' no puede exceder %d caracteres", fieldName, maxLength),
                    entityType,
                    List.of(String.format("El campo '%s' es muy largo (máximo %d caracteres)", fieldName, maxLength))
                );
            }
        }
    }
    
    /**
     * Valida múltiples campos de una vez
     */
    public static void validateFields(String entityType, ValidationRule... rules) {
        List<String> errors = new ArrayList<>();
        
        for (ValidationRule rule : rules) {
            try {
                rule.validate();
            } catch (ValidationException e) {
                errors.addAll(e.getValidationErrors());
            }
        }
        
        if (!errors.isEmpty()) {
            throw new ValidationException(
                String.format("Errores de validación en %s", entityType),
                entityType,
                errors
            );
        }
    }
    
    /**
     * Interfaz funcional para reglas de validación
     */
    @FunctionalInterface
    public interface ValidationRule {
        void validate();
    }
}
