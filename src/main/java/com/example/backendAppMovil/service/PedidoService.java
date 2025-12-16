package com.example.backendAppMovil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.backendAppMovil.dto.CompraRequest;
import com.example.backendAppMovil.model.DetallePedido;
import com.example.backendAppMovil.model.Producto;
import com.example.backendAppMovil.model.Pedido;
import com.example.backendAppMovil.model.Usuario;
import com.example.backendAppMovil.repository.PedidoRepository;
import com.example.backendAppMovil.repository.ProductoRepository;
import com.example.backendAppMovil.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public Pedido realizarCompra(CompraRequest request) {

        Usuario usuario = usuarioService.buscarPorId(request.getIdUsuario());

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("COMPLETADO");
        pedido.setMetodoPago(request.getMetodoPago());

        List<DetallePedido> detalles = new ArrayList<>();
        int totalCalculado = 0;

        for (CompraRequest.ItemCompra item : request.getItems()) {
            
            // Buscamos el producto en la BD (Ya no Inventario, sino Producto)
            Producto producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Descontamos stock
            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Sin stock para: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            List<String> codigos = IntStream.range(0, item.getCantidad())
                    .mapToObj(i -> generarCodigoUnico())
                    .collect(Collectors.toList());
            
            // Unimos los códigos en un texto (ej: "KEY-123, KEY-456")
            String licenciasGeneradas = String.join(", ", codigos);

            // Creamos el detalle y le asignamos las licencias
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecio());
            detalle.setLicencias(licenciasGeneradas);

            detalles.add(detalle);
            totalCalculado += (item.getPrecio() * item.getCantidad());
        }

        pedido.setTotal(totalCalculado);
        pedido.setDetalles(detalles);

        return pedidoRepository.save(pedido);
    }

    private String generarCodigoUnico() {
        return "KEY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public List<Pedido> misPedidos() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return pedidoRepository.findByUsuario(usuario);
    }
}
