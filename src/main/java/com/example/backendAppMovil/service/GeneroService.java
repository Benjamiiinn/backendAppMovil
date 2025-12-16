package com.example.backendAppMovil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendAppMovil.model.Genero;
import com.example.backendAppMovil.repository.GeneroRepository;

import jakarta.transaction.Transactional;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Transactional
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    @Transactional
    public Genero findById(int id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + id));
    }

    @Transactional
    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }

    @Transactional
    public Genero update(int id, Genero generoDetails) {
        Genero genero = findById(id);
        genero.setNombre(generoDetails.getNombre());
        return generoRepository.save(genero);
    }

    @Transactional
    public void deleteById(int id) {
        if (!generoRepository.existsById(id)) {
            throw new RuntimeException("Género no encontrado");
        }
        generoRepository.deleteById(id);
    }
}
