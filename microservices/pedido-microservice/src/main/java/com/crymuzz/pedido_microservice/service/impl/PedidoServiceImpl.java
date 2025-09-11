package com.crymuzz.pedido_microservice.service.impl;


import com.crymuzz.pedido_microservice.client.ProductoClient;
import com.crymuzz.pedido_microservice.exception.ResourceNotFoundException;
import com.crymuzz.pedido_microservice.mapper.DetallePedidoMapper;
import com.crymuzz.pedido_microservice.mapper.PedidoMapper;
import com.crymuzz.pedido_microservice.model.dto.ApiResponse;
import com.crymuzz.pedido_microservice.model.dto.CreatePedidoDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponsePedidoDTO;
import com.crymuzz.pedido_microservice.model.dto.ResponseProductDTO;
import com.crymuzz.pedido_microservice.model.entity.Cliente;
import com.crymuzz.pedido_microservice.model.entity.DetallePedido;
import com.crymuzz.pedido_microservice.model.entity.Pedido;
import com.crymuzz.pedido_microservice.model.enums.EstadoPedido;
import com.crymuzz.pedido_microservice.repository.ClienteRepository;
import com.crymuzz.pedido_microservice.repository.PedidoRepository;
import com.crymuzz.pedido_microservice.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoMapper pedidoMapper;
    private final DetallePedidoMapper detallePedidoMapper;
    private final ProductoClient productoClient;

    @Transactional
    public ResponsePedidoDTO crearPedido(CreatePedidoDTO createPedidoDTO) {
        Cliente cliente = clienteRepository.findById(createPedidoDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Pedido pedido = pedidoMapper.toEntity(createPedidoDTO);
        pedido.setCliente(cliente);
        pedido.setFechaPedido(createPedidoDTO.getFechaPedido());
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setTotal(BigDecimal.ZERO);

        List<DetallePedido> detalles = createPedidoDTO.getDetalles().stream()
                .map(detalleDTO -> {
                    DetallePedido detalle = detallePedidoMapper.toEntity(detalleDTO);

                    ApiResponse<ResponseProductDTO> response = productoClient.obtenerProductoPorId(detalleDTO.getProductoId());
                    ResponseProductDTO producto = response.getData();

                    if (producto == null || producto.getPrecio() == null) {
                        throw new ResourceNotFoundException("El producto con ID " + detalleDTO.getProductoId() + " no tiene información válida");
                    }

                    detalle.setProductoId(producto.getId());
                    detalle.setPedido(pedido);

                    BigDecimal precioUnitario = producto.getPrecio();
                    detalle.setPrecioUnitario(precioUnitario);
                    detalle.setTotalDetalle(precioUnitario.multiply(BigDecimal.valueOf(detalle.getCantidad())));

                    return detalle;
                })
                .toList();

        pedido.setDetalles(detalles);

        BigDecimal totalPedido = detalles.stream()
                .map(DetallePedido::getTotalDetalle)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotal(totalPedido);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedidoGuardado);
    }




    @Override
    public List<ResponsePedidoDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoMapper.toDtoList(pedidos);
    }

    @Override
    public ResponsePedidoDTO obtenerPedidoId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        return pedidoMapper.toDto(pedido);
    }

}
