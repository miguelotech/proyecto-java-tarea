package com.tareams.composition_pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProductDTO {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("nombre")
    private String nombre;
    
    @JsonProperty("descripcion")
    private String descripcion;
    
    @JsonProperty("precio")
    private Double precio;
    
    @JsonProperty("stock")
    private Integer stock;
    
    @JsonProperty("categoria")
    private ResponseCategoriaDTO categoria;
}
