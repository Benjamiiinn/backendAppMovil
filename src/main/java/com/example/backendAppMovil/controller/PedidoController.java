package com.example.backendAppMovil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendAppMovil.dto.CompraRequest;
import com.example.backendAppMovil.model.Pedido;
import com.example.backendAppMovil.repository.PedidoRepository;
import com.example.backendAppMovil.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodosLosPedidos() {
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    // POST /api/v1/pedidos/checkout
    @PostMapping("/checkout")
    public ResponseEntity<?> realizarCompra(@RequestBody CompraRequest request) {
        try {
            Pedido pedido = pedidoService.realizarCompra(request);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            // Si falla el stock, devolvemos error 400 con el mensaje
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/v1/pedidos/mis-pedidos
    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<Pedido>> historial() {
        return ResponseEntity.ok(pedidoService.misPedidos());
    }
}
