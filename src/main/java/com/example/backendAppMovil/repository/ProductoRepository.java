package com.example.backendAppMovil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendAppMovil.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    List<Producto> findByPlataformaNombre(String nombre);
    List<Producto> findByGeneroNombre(String nombre);
    List<Producto> findByNombre(String nombre);
}
