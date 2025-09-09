package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Producto;
import com.nttdata.dockerized.postgresql.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

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
    public Producto save(final Producto producto, final Long categoriaId) {
        return categoriaService.findById(categoriaId)
                .map(categoria -> {
                    producto.setCategoria(categoria);
                    return productoRepository.save(producto);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));
    }

    @Override
    public Optional<Producto> update(final Long id, final Producto producto, final Long categoriaId) {
        return productoRepository.existsById(id)
                ? categoriaService.findById(categoriaId)
                    .map(categoria -> {
                        producto.setId(id);
                        producto.setCategoria(categoria);
                        return productoRepository.save(producto);
                    })
                : Optional.empty();
    }

    @Override
    public boolean deleteById(final Long id) {
        return productoRepository.existsById(id) ? this.performDelete(id) : false;
    }

    @Override
    public boolean existsById(final Long id) {
        return productoRepository.existsById(id);
    }

    @Override
    public Optional<Producto> asignarCategoria(final Long productoId, final Long categoriaId) {
        return productoRepository.findById(productoId)
                .flatMap(producto -> categoriaService.findById(categoriaId)
                        .map(categoria -> {
                            producto.setCategoria(categoria);
                            return productoRepository.save(producto);
                        }));
    }

    private boolean performDelete(final Long id) {
        productoRepository.deleteById(id);
        return true;
    }
}
