package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.entity.Categoria;
import com.nttdata.dockerized.postgresql.entity.Producto;
import com.nttdata.dockerized.postgresql.exception.BadRequestException;
import com.nttdata.dockerized.postgresql.exception.InternalServerErrorException;
import com.nttdata.dockerized.postgresql.exception.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.exception.ValidationException;
import com.nttdata.dockerized.postgresql.repository.CategoriaRepository;
import com.nttdata.dockerized.postgresql.repository.ProductoRepository;
import com.nttdata.dockerized.postgresql.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Producto> listAll() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> listAllDisponibles() {
        return productoRepository.findAll(); // no hay campo disponible
    }

    @Override
    public Optional<Producto> findById(final Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException(
                "El ID del producto debe ser un número positivo",
                "Producto",
                id
            );
        }
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> findByCategoria(final Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public List<Producto> findByCategoriaNombre(final String categoriaNombre) {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getCategoria().getNombre().equalsIgnoreCase(categoriaNombre))
                .toList();
    }

    @Override
    public List<Producto> findByPrecioRango(final BigDecimal precioMin, final BigDecimal precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }

    @Override
    public List<Producto> findByNombre(final String nombre) {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }

    @Override
    public List<Producto> findProductosEnStock() {
        return productoRepository.findAll(); // Simplificado - no hay campo stock
    }

    @Override
    @Transactional
    public Producto save(final Producto producto, final Long categoriaId) {
        try {
            // Validar ID de categoría
            if (categoriaId == null || categoriaId <= 0) {
                throw new BadRequestException(
                    "El ID de la categoría debe ser un número positivo",
                    "Producto",
                    categoriaId
                );
            }
            
            // Validaciones de negocio
            validateProductoForSave(producto);
            
            // Verificar si la categoría existe
            return categoriaRepository.findById(categoriaId)
                    .map(categoria -> {
                        producto.setCategoria(categoria);
                        return productoRepository.save(producto);
                    })
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró la categoría con ID: " + categoriaId,
                        "Categoria",
                        categoriaId
                    ));
        } catch (Exception e) {
            log.error("Error al guardar producto: {}", e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ValidationException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al guardar el producto",
                e,
                "Producto",
                null
            );
        }
    }

    @Override
    @Transactional
    public Optional<Producto> update(final Long id, final Producto producto, final Long categoriaId) {
        try {
            // Validar ID del producto
            if (id == null || id <= 0) {
                throw new BadRequestException(
                    "El ID del producto debe ser un número positivo",
                    "Producto",
                    id
                );
            }
            
            // Validar ID de categoría
            if (categoriaId == null || categoriaId <= 0) {
                throw new BadRequestException(
                    "El ID de la categoría debe ser un número positivo",
                    "Producto",
                    categoriaId
                );
            }
            
            // Verificar si existe el producto
            if (!productoRepository.existsById(id)) {
                throw new ResourceNotFoundException(
                    "No se encontró el producto con ID: " + id,
                    "Producto",
                    id
                );
            }
            
            // Validaciones de negocio
            validateProductoForUpdate(producto);
            
            // Verificar si la categoría existe
            return Optional.of(categoriaRepository.findById(categoriaId)
                    .map(categoria -> {
                        producto.setId(id);
                        producto.setCategoria(categoria);
                        return productoRepository.save(producto);
                    })
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró la categoría con ID: " + categoriaId,
                        "Categoria",
                        categoriaId
                    )));
        } catch (Exception e) {
            log.error("Error al actualizar producto con ID {}: {}", id, e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ValidationException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al actualizar el producto",
                e,
                "Producto",
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
                    "El ID del producto debe ser un número positivo",
                    "Producto",
                    id
                );
            }
            
            // Verificar si existe el producto
            if (!productoRepository.existsById(id)) {
                throw new ResourceNotFoundException(
                    "No se encontró el producto con ID: " + id,
                    "Producto",
                    id
                );
            }
            
            return this.performDelete(id);
        } catch (Exception e) {
            log.error("Error al eliminar producto con ID {}: {}", id, e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al eliminar el producto",
                e,
                "Producto",
                id
            );
        }
    }

    @Override
    public boolean existsById(final Long id) {
        return productoRepository.existsById(id);
    }

    @Override
    public Optional<Producto> asignarCategoria(final Long productoId, final Long categoriaId) {
        return productoRepository.findById(productoId)
                .flatMap(producto -> categoriaRepository.findById(categoriaId)
                        .map(categoria -> {
                            producto.setCategoria(categoria);
                            return productoRepository.save(producto);
                        }));
    }

    private boolean performDelete(final Long id) {
        productoRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida un producto antes de guardarlo
     */
    private void validateProductoForSave(Producto producto) {
        if (producto == null) {
            throw new ValidationException(
                "El producto no puede ser nulo",
                "Producto",
                null,
                List.of("El producto es requerido")
            );
        }
        
        ValidationUtils.validateFields("Producto",
            () -> ValidationUtils.validateNotNullOrEmpty(producto.getNombre(), "nombre", "Producto"),
            () -> ValidationUtils.validateStringLength(producto.getNombre(), "nombre", 2, 100, "Producto"),
            () -> ValidationUtils.validatePositiveNumber(producto.getPrecio(), "precio", "Producto")
        );
    }
    
    /**
     * Valida un producto antes de actualizarlo
     */
    private void validateProductoForUpdate(Producto producto) {
        if (producto == null) {
            throw new ValidationException(
                "El producto no puede ser nulo",
                "Producto",
                null,
                List.of("El producto es requerido")
            );
        }
        
        ValidationUtils.validateFields("Producto",
            () -> ValidationUtils.validateNotNullOrEmpty(producto.getNombre(), "nombre", "Producto"),
            () -> ValidationUtils.validateStringLength(producto.getNombre(), "nombre", 2, 100, "Producto"),
            () -> ValidationUtils.validatePositiveNumber(producto.getPrecio(), "precio", "Producto")
        );
    }
}
