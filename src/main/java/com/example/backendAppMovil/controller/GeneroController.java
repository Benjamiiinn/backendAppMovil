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

import com.example.backendAppMovil.model.Genero;
import com.example.backendAppMovil.service.GeneroService;

@RestController
@RequestMapping("/api/v1/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<Genero>> listarTodos() {
        return ResponseEntity.ok(generoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(generoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Genero> crear(@RequestBody Genero genero) {
        return ResponseEntity.ok(generoService.save(genero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> actualizar(@PathVariable int id, @RequestBody Genero genero) {
        return ResponseEntity.ok(generoService.update(id, genero));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        generoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
