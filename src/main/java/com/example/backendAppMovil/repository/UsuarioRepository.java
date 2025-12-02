package com.example.backendAppMovil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendAppMovil.model.Usuario;

import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByRut(String rut);
    boolean existsByUsername(String username);
    boolean existsByRut(String rut);
}
