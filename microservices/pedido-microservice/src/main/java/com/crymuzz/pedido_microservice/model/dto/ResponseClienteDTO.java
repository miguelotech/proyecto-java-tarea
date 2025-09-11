package com.crymuzz.pedido_microservice.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseClienteDTO {
    private Long id;
    private String nombre;
    private String email;
    private LocalDate fechaRegistro;
    private boolean activo;
}
