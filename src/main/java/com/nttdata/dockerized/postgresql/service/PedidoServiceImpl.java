package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.dto.PedidoSaveRequestDto;
import com.nttdata.dockerized.postgresql.model.entity.DetallePedido;
import com.nttdata.dockerized.postgresql.model.entity.Pedido;
import com.nttdata.dockerized.postgresql.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClientService clientService;
    private final ProductoService productoService;

    @Override
    public List<Pedido> listAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> findById(final Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> findByClientId(final Long clientId) {
        return pedidoRepository.findByClientId(clientId);
    }

    @Override
    public List<Pedido> findByEstado(final Pedido.EstadoPedido estado) {
        return pedidoRepository.findAll().stream()
                .filter(pedido -> pedido.getEstado().equals(estado))
                .toList();
    }

    @Override
    public List<Pedido> findByFechaRango(final LocalDateTime fechaInicio, final LocalDateTime fechaFin) {
        return pedidoRepository.findAll().stream()
                .filter(pedido -> pedido.getFechaPedido().isAfter(fechaInicio) && pedido.getFechaPedido().isBefore(fechaFin))
                .toList();
    }

    @Override
    @Transactional
    public Pedido crearPedido(final PedidoSaveRequestDto pedidoSaveRequestDto) {
        return clientService.findById(pedidoSaveRequestDto.getClientId())
                .map(client -> {
                    final Pedido pedido = Pedido.builder()
                            .client(client)
                            .fechaPedido(LocalDateTime.now())
                            .estado(Pedido.EstadoPedido.PENDIENTE)
                            .build();

                    final List<DetallePedido> detalles = this.crearDetallesPedido(pedidoSaveRequestDto, pedido);
                    pedido.setDetalles(detalles);

                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + pedidoSaveRequestDto.getClientId()));
    }

    @Override
    @Transactional
    public Optional<Pedido> updateEstado(final Long id, final Pedido.EstadoPedido estado) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setEstado(estado);
                    return pedidoRepository.save(pedido);
                });
    }

    @Override
    public Optional<Pedido> update(final Long id, final Pedido pedido) {
        return pedidoRepository.existsById(id)
                ? Optional.of(pedidoRepository.save(this.setPedidoIdAndReturn(pedido, id)))
                : Optional.empty();
    }

    @Override
    public boolean deleteById(final Long id) {
        return pedidoRepository.existsById(id) ? this.performDelete(id) : false;
    }

    @Override
    public boolean existsById(final Long id) {
        return pedidoRepository.existsById(id);
    }

    @Override
    public Long countPedidosByClient(final Long clientId) {
        return (long) pedidoRepository.findByClientId(clientId).size();
    }

    @Override
    public List<Pedido> findHistorialByClient(final Long clientId) {
        return pedidoRepository.findByClientIdOrderByFechaPedidoDesc(clientId);
    }

    private List<DetallePedido> crearDetallesPedido(final PedidoSaveRequestDto pedidoSaveRequestDto, final Pedido pedido) {
        return pedidoSaveRequestDto.getDetalles().stream()
                .map(detalleRequest -> 
                    productoService.findById(detalleRequest.getProductoId())
                            .map(producto -> DetallePedido.builder()
                                    .pedido(pedido)
                                    .producto(producto)
                                    .cantidad(detalleRequest.getCantidad())
                                    .precioUnitario(producto.getPrecio())
                                    .build())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleRequest.getProductoId()))
                )
                .collect(Collectors.toList());
    }


    private Pedido setPedidoIdAndReturn(final Pedido pedido, final Long id) {
        pedido.setId(id);
        return pedido;
    }

    private boolean performDelete(final Long id) {
        pedidoRepository.deleteById(id);
        return true;
    }
}
