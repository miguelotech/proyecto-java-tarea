package com.tareams.composition_pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePedidoDTO {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("fechaPedido")
    private LocalDateTime fechaPedido;
    
    @JsonProperty("estado")
    private String estado;
    
    @JsonProperty("total")
    private Double total;
    
    @JsonProperty("cliente")
    private ResponseClienteDTO cliente;
    
    @JsonProperty("detalles")
    private List<ResponseDetallePedidoDTO> detalles;
}
