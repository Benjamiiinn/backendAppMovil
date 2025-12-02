package com.example.backendAppMovil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendAppMovil.model.DetallePedido;
import com.example.backendAppMovil.service.DetallePedidoService;

@RestController
@RequestMapping("/api/v1/detalles")
public class DetallePedidoController {
    
    @Autowired
    private DetallePedidoService detallePedidoService;

    // Endpoint: GET /api/v1/detalles/pedido/5
    // Descripción: Devuelve la lista de juegos que se compraron en el pedido #5
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<DetallePedido>> obtenerPorPedido(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(detallePedidoService.buscarPorIdPedido(idPedido));
    }

    // Endpoint: GET /api/v1/detalles/10
    // Descripción: Devuelve la info de una línea específica de compra
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detallePedidoService.buscarPorId(id));
    }
}
