package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}


