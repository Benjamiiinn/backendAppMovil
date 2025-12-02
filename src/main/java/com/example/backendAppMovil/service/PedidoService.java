package com.example.backendAppMovil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.backendAppMovil.dto.CompraRequest;
import com.example.backendAppMovil.model.DetallePedido;
import com.example.backendAppMovil.model.Inventario;
import com.example.backendAppMovil.model.Pedido;
import com.example.backendAppMovil.model.Usuario;
import com.example.backendAppMovil.repository.InventarioRepository;
import com.example.backendAppMovil.repository.PedidoRepository;
import com.example.backendAppMovil.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Pedido procesarCompra(CompraRequest request) {
        
        // 1. Identificar al usuario que está comprando
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Crear el pedido base
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("COMPLETADO");
        pedido.setMetodoPago(request.getMetodoPago());

        List<DetallePedido> detalles = new ArrayList<>();
        int totalCalculado = 0;

        // 3. Recorrer los items del carrito
        for (CompraRequest.ItemCompra item : request.getItems()) {
            
            // Buscar el juego en TU inventario
            Inventario juegoEnInventario = inventarioRepository.findByRawgId(item.getRawgId())
                    .orElseThrow(() -> new RuntimeException("Juego no disponible en tienda (ID: " + item.getRawgId() + ")"));

            // Validar Stock
            if (juegoEnInventario.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + item.getRawgId()); 
                // Al lanzar esta excepción, @Transactional deshace todo lo anterior automáticamente
            }

            // Descontar Stock
            juegoEnInventario.setStock(juegoEnInventario.getStock() - item.getCantidad());
            inventarioRepository.save(juegoEnInventario);

            // Crear el detalle de este item
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setRawgId(item.getRawgId());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(juegoEnInventario.getPrecio()); // Usamos el precio real de la BD

            detalles.add(detalle);
            totalCalculado += (juegoEnInventario.getPrecio() * item.getCantidad());
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(totalCalculado);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public List<Pedido> misPedidos() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        return pedidoRepository.findByUsuario(usuario);
    }
}
