package com.nttdata.dockerized.postgresql.controller;

import com.nttdata.dockerized.postgresql.exception.ResourceNotFoundException;
import com.nttdata.dockerized.postgresql.model.dto.CategoriaDto;
import com.nttdata.dockerized.postgresql.model.dto.CategoriaSaveRequestDto;
import com.nttdata.dockerized.postgresql.model.dto.CategoriaSaveResponseDto;
import com.nttdata.dockerized.postgresql.model.dto.CategoriaUpdateRequestDto;
import com.nttdata.dockerized.postgresql.model.entity.Categoria;
import com.nttdata.dockerized.postgresql.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para CategoriaController
 * Cubre todos los métodos: findAll, findById, save, update, delete
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - CategoriaController")
class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private Categoria categoria;
    private CategoriaDto categoriaDto;
    private CategoriaSaveRequestDto saveRequestDto;
    private CategoriaUpdateRequestDto updateRequestDto;
    private CategoriaSaveResponseDto saveResponseDto;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        categoria = Categoria.builder()
                .id(1L)
                .nombre("Electrónicos")
                .build();

        categoriaDto = new CategoriaDto();
        categoriaDto.setId(1L);
        categoriaDto.setNombre("Electrónicos");

        saveRequestDto = new CategoriaSaveRequestDto();
        saveRequestDto.setNombre("Electrónicos");

        updateRequestDto = new CategoriaUpdateRequestDto();
        updateRequestDto.setNombre("Electrónicos Actualizado");

        saveResponseDto = new CategoriaSaveResponseDto();
        saveResponseDto.setId(1L);
        saveResponseDto.setNombre("Electrónicos");
    }

    // ========== PRUEBAS EXITOSAS (Successfully) ==========

    @Test
    @DisplayName("findAll - Debe retornar lista de categorías exitosamente")
    void findAll_ShouldReturnListOfCategorias_Successfully() {
        // Given
        List<Categoria> categorias = Arrays.asList(categoria);
        when(categoriaService.listAll()).thenReturn(categorias);

        // When
        List<CategoriaDto> result = categoriaController.getAllCategorias();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getNombre()).isEqualTo("Electrónicos");
        
        verify(categoriaService).listAll();
    }

    @Test
    @DisplayName("findById - Debe retornar categoría por ID exitosamente")
    void findById_ShouldReturnCategoriaById_Successfully() {
        // Given
        Long categoriaId = 1L;
        when(categoriaService.findById(categoriaId)).thenReturn(Optional.of(categoria));

        // When
        CategoriaDto result = categoriaController.getCategoriaById(categoriaId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNombre()).isEqualTo("Electrónicos");
        
        verify(categoriaService).findById(categoriaId);
    }

    @Test
    @DisplayName("save - Debe crear categoría exitosamente")
    void save_ShouldCreateCategoria_Successfully() {
        // Given
        when(categoriaService.save(any(Categoria.class))).thenReturn(categoria);

        // When
        CategoriaSaveResponseDto result = categoriaController.save(saveRequestDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNombre()).isEqualTo("Electrónicos");
        
        verify(categoriaService).save(any(Categoria.class));
    }

    @Test
    @DisplayName("update - Debe actualizar categoría exitosamente")
    void update_ShouldUpdateCategoria_Successfully() {
        // Given
        Long categoriaId = 1L;
        Categoria categoriaActualizada = Categoria.builder()
                .id(1L)
                .nombre("Electrónicos Actualizado")
                .build();

        when(categoriaService.update(eq(categoriaId), any(Categoria.class))).thenReturn(Optional.of(categoriaActualizada));

        // When
        CategoriaSaveResponseDto result = categoriaController.update(categoriaId, updateRequestDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNombre()).isEqualTo("Electrónicos Actualizado");
        
        verify(categoriaService).update(eq(categoriaId), any(Categoria.class));
    }

    @Test
    @DisplayName("delete - Debe eliminar categoría exitosamente")
    void delete_ShouldDeleteCategoria_Successfully() {
        // Given
        Long categoriaId = 1L;
        when(categoriaService.deleteById(categoriaId)).thenReturn(true);

        // When
        categoriaController.delete(categoriaId);

        // Then
        verify(categoriaService).deleteById(categoriaId);
    }

    // ========== PRUEBAS DE ERROR (Exception Cases) ==========

    @Test
    @DisplayName("findById - Debe lanzar ResourceNotFoundException cuando categoría no existe")
    void findById_ShouldThrowResourceNotFoundException_WhenCategoriaNotFound() {
        // Given
        Long categoriaId = 999L;
        when(categoriaService.findById(categoriaId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> categoriaController.getCategoriaById(categoriaId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No se encontró la categoría con ID: 999");
        
        verify(categoriaService).findById(categoriaId);
    }

    @Test
    @DisplayName("update - Debe lanzar ResourceNotFoundException cuando categoría no existe para actualizar")
    void update_ShouldThrowResourceNotFoundException_WhenCategoriaNotFound() {
        // Given
        Long categoriaId = 999L;
        when(categoriaService.update(eq(categoriaId), any(Categoria.class))).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> categoriaController.update(categoriaId, updateRequestDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No se encontró la categoría con ID: 999");
        
        verify(categoriaService).update(eq(categoriaId), any(Categoria.class));
    }
}
