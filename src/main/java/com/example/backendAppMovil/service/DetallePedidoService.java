package com.example.backendAppMovil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendAppMovil.model.DetallePedido;
import com.example.backendAppMovil.repository.DetallePedidoRepository;

import jakarta.transaction.Transactional;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    // Obtener todos los detalles de un pedido específico
    @Transactional
    public List<DetallePedido> buscarPorIdPedido(Integer idPedido) {
        return detallePedidoRepository.findByPedidoId(idPedido);
    }

    // Obtener un detalle individual por su propio ID
    @Transactional
    public DetallePedido buscarPorId(Integer id) {
        return detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado con ID: " + id));
    }
}
