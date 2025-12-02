package com.example.backendAppMovil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendAppMovil.model.Inventario;
import com.example.backendAppMovil.repository.InventarioRepository;

import jakarta.transaction.Transactional;

@Service
public class InventarioService {

    @Autowired // Inyección directa
    private InventarioRepository inventarioRepository;

    @Transactional
    public Optional<Inventario> obtenerPorRawgId(Integer rawgId) {
        return inventarioRepository.findByRawgId(rawgId);
    }

    @Transactional // Si falla al guardar, hace rollback
    public Inventario crear(Inventario juego) {
        if (inventarioRepository.findByRawgId(juego.getRawgId()).isPresent()) {
            throw new RuntimeException("El juego con RAWG ID " + juego.getRawgId() + " ya existe en el inventario.");
        }
        return inventarioRepository.save(juego);
    }

    @Transactional
    public Inventario actualizar(Integer rawgId, Inventario datosNuevos) {
        Inventario existente = inventarioRepository.findByRawgId(rawgId)
                .orElseThrow(() -> new RuntimeException("No se encontró el juego con RAWG ID: " + rawgId));
        
        existente.setPrecio(datosNuevos.getPrecio());
        existente.setStock(datosNuevos.getStock());
        
        return inventarioRepository.save(existente);
    }
}
