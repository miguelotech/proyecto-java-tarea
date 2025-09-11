package com.crymuzz.pedido_microservice.exception;

import lombok.Getter;

// EXCEPCION POR CAMPO O ATRIBUTO

@Getter
public class DuplicateCategoryException extends RuntimeException{

    private final String name;

    public DuplicateCategoryException(String name) {
        super("La categoría de nombre "+name+" está tratando de ser duplicada");
        this.name = name;
    }

}
