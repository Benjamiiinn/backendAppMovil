package com.example.backendAppMovil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendAppMovil.model.Inventario;
import com.example.backendAppMovil.repository.InventarioRepository;
import com.example.backendAppMovil.service.InventarioService;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioRepository inventarioRepository;

    @GetMapping("/{rawgId}")
    public ResponseEntity<Inventario> obtenerInfoJuego(@PathVariable Integer rawgId) {
        return inventarioService.obtenerPorRawgId(rawgId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<Inventario>> listarInventarioCompleto() {
        return ResponseEntity.ok(inventarioRepository.findAll());
    }

    // POST /api/v1/inventario (Para cargar datos desde Postman)
    @PostMapping
    public ResponseEntity<?> crearJuego(@RequestBody Inventario juego) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.crear(juego));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{rawgId}")
    public ResponseEntity<?> actualizarJuego(@PathVariable Integer rawgId, @RequestBody Inventario juego) {
        try {
            return ResponseEntity.ok(inventarioService.actualizar(rawgId, juego));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}