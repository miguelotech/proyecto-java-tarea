package com.crymuzz.producto_microservice.model.entity;

    import jakarta.persistence.*;
    import lombok.*;


    @Entity
    @Table(name = "categorias")
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public class Categoria {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(length = 50, nullable = false, unique = true)
        private String nombre;

    }
