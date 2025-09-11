package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.dto.PedidoSaveRequestDto;
import com.nttdata.dockerized.postgresql.entity.DetallePedido;
import com.nttdata.dockerized.postgresql.entity.Pedido;
import com.nttdata.dockerized.postgresql.exception.BadRequestException;
import com.nttdata.dockerized.postgresql.exception.InternalServerErrorException;
import com.nttdata.dockerized.postgresql.exception.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.exception.ValidationException;
import com.nttdata.dockerized.postgresql.repository.DetallePedidoRepository;
import com.nttdata.dockerized.postgresql.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    @Override
    public List<Pedido> listAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> findById(final Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException(
                "El ID del pedido debe ser un número positivo",
                "Pedido",
                id
            );
        }
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
        try {
            // Validar DTO
            validatePedidoSaveRequest(pedidoSaveRequestDto);
            
            final Pedido pedido = Pedido.builder()
                    .clientId(pedidoSaveRequestDto.getClientId())
                    .fechaPedido(LocalDateTime.now())
                    .estado(Pedido.EstadoPedido.PENDIENTE)
                    .build();

            final Pedido savedPedido = pedidoRepository.save(pedido);
            
            // Crear detalles del pedido
            final List<DetallePedido> detalles = this.crearDetallesPedido(pedidoSaveRequestDto, savedPedido);
            detallePedidoRepository.saveAll(detalles);
            
            savedPedido.setDetalles(detalles);
            return savedPedido;
        } catch (Exception e) {
            log.error("Error al crear pedido: {}", e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ValidationException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al crear el pedido",
                e,
                "Pedido",
                null
            );
        }
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
    @Transactional
    public Optional<Pedido> update(final Long id, final Pedido pedido) {
        try {
            // Validar ID
            if (id == null || id <= 0) {
                throw new BadRequestException(
                    "El ID del pedido debe ser un número positivo",
                    "Pedido",
                    id
                );
            }
            
            // Verificar si existe el pedido
            if (!pedidoRepository.existsById(id)) {
                throw new ResourceNotFoundException(
                    "No se encontró el pedido con ID: " + id,
                    "Pedido",
                    id
                );
            }
            
            // Validaciones de negocio
            validatePedidoForUpdate(pedido);
            
            return Optional.of(pedidoRepository.save(this.setPedidoIdAndReturn(pedido, id)));
        } catch (Exception e) {
            log.error("Error al actualizar pedido con ID {}: {}", id, e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ValidationException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al actualizar el pedido",
                e,
                "Pedido",
                id
            );
        }
    }

    @Override
    @Transactional
    public boolean deleteById(final Long id) {
        try {
            // Validar ID
            if (id == null || id <= 0) {
                throw new BadRequestException(
                    "El ID del pedido debe ser un número positivo",
                    "Pedido",
                    id
                );
            }
            
            // Verificar si existe el pedido
            if (!pedidoRepository.existsById(id)) {
                throw new ResourceNotFoundException(
                    "No se encontró el pedido con ID: " + id,
                    "Pedido",
                    id
                );
            }
            
            return this.performDelete(id);
        } catch (Exception e) {
            log.error("Error al eliminar pedido con ID {}: {}", id, e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al eliminar el pedido",
                e,
                "Pedido",
                id
            );
        }
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
                .map(detalleRequest -> DetallePedido.builder()
                        .pedido(pedido)
                        .productoId(detalleRequest.getProductoId())
                        .cantidad(detalleRequest.getCantidad())
                        .precioUnitario(BigDecimal.ZERO) // Se establecerá cuando se consulte el producto
                        .build())
                .toList();
    }

    private Pedido setPedidoIdAndReturn(final Pedido pedido, final Long id) {
        pedido.setId(id);
        return pedido;
    }

    private boolean performDelete(final Long id) {
        pedidoRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida un PedidoSaveRequestDto antes de crear el pedido
     */
    private void validatePedidoSaveRequest(PedidoSaveRequestDto pedidoSaveRequestDto) {
        if (pedidoSaveRequestDto == null) {
            throw new ValidationException(
                "El DTO del pedido no puede ser nulo",
                "Pedido",
                null,
                List.of("El DTO del pedido es requerido")
            );
        }
        
        if (pedidoSaveRequestDto.getClientId() == null || pedidoSaveRequestDto.getClientId() <= 0) {
            throw new ValidationException(
                "El ID del cliente debe ser un número positivo",
                "Pedido",
                null,
                List.of("El ID del cliente es requerido y debe ser mayor que 0")
            );
        }
        
        if (pedidoSaveRequestDto.getDetalles() == null || pedidoSaveRequestDto.getDetalles().isEmpty()) {
            throw new ValidationException(
                "El pedido debe tener al menos un detalle",
                "Pedido",
                null,
                List.of("El pedido debe contener al menos un producto")
            );
        }
    }
    
    /**
     * Valida un pedido antes de actualizarlo
     */
    private void validatePedidoForUpdate(Pedido pedido) {
        if (pedido == null) {
            throw new ValidationException(
                "El pedido no puede ser nulo",
                "Pedido",
                null,
                List.of("El pedido es requerido")
            );
        }
        
        if (pedido.getEstado() == null) {
            throw new ValidationException(
                "El estado del pedido es requerido",
                "Pedido",
                null,
                List.of("El estado del pedido no puede ser nulo")
            );
        }
    }
}
