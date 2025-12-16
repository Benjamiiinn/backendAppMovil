package com.example.backendAppMovil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendAppMovil.model.Plataforma;
import com.example.backendAppMovil.service.PlataformaService;

@RestController
@RequestMapping("/api/v1/plataformas")
public class PlataformaController {

    @Autowired
    private PlataformaService plataformaService;

    @GetMapping
    public ResponseEntity<List<Plataforma>> listarTodas() {
        return ResponseEntity.ok(plataformaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plataforma> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(plataformaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Plataforma> crear(@RequestBody Plataforma plataforma) {
        return ResponseEntity.ok(plataformaService.save(plataforma));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plataforma> actualizar(@PathVariable int id, @RequestBody Plataforma plataforma) {
        return ResponseEntity.ok(plataformaService.update(id, plataforma));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        plataformaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
