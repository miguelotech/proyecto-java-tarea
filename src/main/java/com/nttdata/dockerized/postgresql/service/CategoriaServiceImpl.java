package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.exception.BadRequestException;
import com.nttdata.dockerized.postgresql.exception.InternalServerErrorException;
import com.nttdata.dockerized.postgresql.exception.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.exception.ValidationException;
import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import com.nttdata.dockerized.postgresql.repository.CategoriaRepository;
import com.nttdata.dockerized.postgresql.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        if (id == null || id <= 0) {
            throw new BadRequestException(
                "El ID de la categoría debe ser un número positivo",
                "Categoria",
                id
            );
        }
        return categoriaRepository.findById(id);
    }

    @Override
    public Optional<Categoria> findByNombre(final String nombre) {
        return categoriaRepository.findAll().stream()
                .filter(categoria -> categoria.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    @Override
    @Transactional
    public Categoria save(final Categoria categoria) {
        try {
            // Validaciones de negocio
            validateCategoriaForSave(categoria);
            
            // Verificar si ya existe una categoría con el mismo nombre
            if (categoriaRepository.findAll().stream()
                    .anyMatch(c -> c.getNombre().equalsIgnoreCase(categoria.getNombre()))) {
                throw new BadRequestException(
                    "Ya existe una categoría con el nombre: " + categoria.getNombre(),
                    "Categoria",
                    null,
                    "El nombre de la categoría debe ser único"
                );
            }
            
            return categoriaRepository.save(categoria);
        } catch (Exception e) {
            log.error("Error al guardar categoría: {}", e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ValidationException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al guardar la categoría",
                "Categoria",
                null,
                e
            );
        }
    }

    @Override
    @Transactional
    public Optional<Categoria> update(final Long id, final Categoria categoria) {
        try {
            // Validar ID
            if (id == null || id <= 0) {
                throw new BadRequestException(
                    "El ID de la categoría debe ser un número positivo",
                    "Categoria",
                    id
                );
            }
            
            // Verificar si existe la categoría
            if (!categoriaRepository.existsById(id)) {
                throw new ResourceNotFoundException(
                    "No se encontró la categoría con ID: " + id,
                    "Categoria",
                    id
                );
            }
            
            // Validaciones de negocio
            validateCategoriaForUpdate(categoria);
            
            // Verificar si ya existe otra categoría con el mismo nombre
            if (categoriaRepository.findAll().stream()
                    .anyMatch(c -> c.getNombre().equalsIgnoreCase(categoria.getNombre()) && !c.getId().equals(id))) {
                throw new BadRequestException(
                    "Ya existe otra categoría con el nombre: " + categoria.getNombre(),
                    "Categoria",
                    id,
                    "El nombre de la categoría debe ser único"
                );
            }
            
            return Optional.of(categoriaRepository.save(this.setCategoriaIdAndReturn(categoria, id)));
        } catch (Exception e) {
            log.error("Error al actualizar categoría con ID {}: {}", id, e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ValidationException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al actualizar la categoría",
                "Categoria",
                id,
                e
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
                    "El ID de la categoría debe ser un número positivo",
                    "Categoria",
                    id
                );
            }
            
            // Verificar si existe la categoría
            if (!categoriaRepository.existsById(id)) {
                throw new ResourceNotFoundException(
                    "No se encontró la categoría con ID: " + id,
                    "Categoria",
                    id
                );
            }
            
            // Verificar si la categoría tiene productos asociados
            Optional<Categoria> categoria = categoriaRepository.findById(id);
            if (categoria.isPresent() && categoria.get().getProductos() != null && !categoria.get().getProductos().isEmpty()) {
                throw new BadRequestException(
                    "No se puede eliminar la categoría porque tiene productos asociados",
                    "Categoria",
                    id,
                    "Primero elimine o mueva los productos de esta categoría"
                );
            }
            
            return this.performDelete(id);
        } catch (Exception e) {
            log.error("Error al eliminar categoría con ID {}: {}", id, e.getMessage(), e);
            if (e instanceof BadRequestException || e instanceof ResourceNotFoundException) {
                throw e;
            }
            throw new InternalServerErrorException(
                "Error interno al eliminar la categoría",
                "Categoria",
                id,
                e
            );
        }
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
    
    /**
     * Valida una categoría antes de guardarla
     */
    private void validateCategoriaForSave(Categoria categoria) {
        if (categoria == null) {
            throw new ValidationException(
                "La categoría no puede ser nula",
                "Categoria",
                List.of("La categoría es requerida")
            );
        }
        
        ValidationUtils.validateFields("Categoria",
            () -> ValidationUtils.validateNotNullOrEmpty(categoria.getNombre(), "nombre", "Categoria"),
            () -> ValidationUtils.validateStringLength(categoria.getNombre(), "nombre", 2, 100, "Categoria")
        );
    }
    
    /**
     * Valida una categoría antes de actualizarla
     */
    private void validateCategoriaForUpdate(Categoria categoria) {
        if (categoria == null) {
            throw new ValidationException(
                "La categoría no puede ser nula",
                "Categoria",
                List.of("La categoría es requerida")
            );
        }
        
        ValidationUtils.validateFields("Categoria",
            () -> ValidationUtils.validateNotNullOrEmpty(categoria.getNombre(), "nombre", "Categoria"),
            () -> ValidationUtils.validateStringLength(categoria.getNombre(), "nombre", 2, 100, "Categoria")
        );
    }
}
