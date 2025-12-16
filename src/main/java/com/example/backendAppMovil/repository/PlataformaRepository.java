package com.example.backendAppMovil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendAppMovil.model.Plataforma;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Integer> {
    boolean existsByNombre(String nombre);
    Plataforma findByNombre(String nombre);
}
