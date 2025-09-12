package com.tareams.composition_pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDetallePedidoDTO {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("cantidad")
    private Integer cantidad;
    
    @JsonProperty("precioUnitario")
    private Double precioUnitario;
    
    @JsonProperty("subtotal")
    private Double subtotal;
    
    @JsonProperty("producto")
    private ResponseProductDTO producto;
}
