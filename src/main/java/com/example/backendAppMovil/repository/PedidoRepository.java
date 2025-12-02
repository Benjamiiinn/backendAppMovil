package com.example.backendAppMovil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendAppMovil.model.Pedido;
import com.example.backendAppMovil.model.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuario(Usuario usuario);
}
