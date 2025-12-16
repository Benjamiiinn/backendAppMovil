package com.example.backendAppMovil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backendAppMovil.model.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    boolean existsByNombre(String nombre);
}
