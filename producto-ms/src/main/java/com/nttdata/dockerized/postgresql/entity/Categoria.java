package com.nttdata.dockerized.postgresql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad Categoría - Clasificación de productos
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Lista de productos de esta categoría
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;
}


