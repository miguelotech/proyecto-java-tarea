package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import com.nttdata.dockerized.postgresql.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public List<Categoria> listAllActive() {
        return categoriaRepository.findAll(); // Simplificado - no hay campo active
    }

    @Override
    public Optional<Categoria> findById(final Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Optional<Categoria> findByNombre(final String nombre) {
        return categoriaRepository.findAll().stream()
                .filter(categoria -> categoria.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    @Override
    public Categoria save(final Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Optional<Categoria> update(final Long id, final Categoria categoria) {
        return categoriaRepository.existsById(id) 
            ? Optional.of(categoriaRepository.save(this.setCategoriaIdAndReturn(categoria, id)))
            : Optional.empty();
    }

    @Override
    public boolean deleteById(final Long id) {
        return categoriaRepository.existsById(id) ? this.performDelete(id) : false;
    }

    @Override
    public boolean existsById(final Long id) {
        return categoriaRepository.existsById(id);
    }

    @Override
    public List<Categoria> findCategoriasConProductos() {
        return categoriaRepository.findAll().stream()
                .filter(categoria -> categoria.getProductos() != null && !categoria.getProductos().isEmpty())
                .toList();
    }

    private Categoria setCategoriaIdAndReturn(final Categoria categoria, final Long id) {
        categoria.setId(id);
        return categoria;
    }

    private boolean performDelete(final Long id) {
        categoriaRepository.deleteById(id);
        return true;
    }
}
