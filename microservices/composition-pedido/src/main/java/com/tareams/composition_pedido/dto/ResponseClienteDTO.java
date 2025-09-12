package com.tareams.composition_pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseClienteDTO {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("nombre")
    private String nombre;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("telefono")
    private String telefono;
    
    @JsonProperty("direccion")
    private String direccion;
}
