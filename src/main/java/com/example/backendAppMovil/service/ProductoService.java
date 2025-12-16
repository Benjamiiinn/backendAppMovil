package com.example.backendAppMovil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendAppMovil.model.Genero;
import com.example.backendAppMovil.model.Plataforma;
import com.example.backendAppMovil.model.Producto;
import com.example.backendAppMovil.repository.GeneroRepository;
import com.example.backendAppMovil.repository.PlataformaRepository;
import com.example.backendAppMovil.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Transactional
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Transactional
    public List<Producto> findByPlataforma(String nombrePlataforma) {
        return productoRepository.findByPlataformaNombre(nombrePlataforma);
    }

    @Transactional
    public List<Producto> findByGenero(String nombreGenero) {
        return productoRepository.findByGeneroNombre(nombreGenero);
    }

    @Transactional
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Transactional
    public Producto findById(int id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public Producto save(Producto producto) {
        if (producto.getPlataforma() != null && producto.getPlataforma().getId() != null) {
            Plataforma plat = plataformaRepository.findById(producto.getPlataforma().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setPlataforma(plat);
        }

        if (producto.getGenero() != null && producto.getGenero().getId() != null) {
            Genero gen = generoRepository.findById(producto.getGenero().getId())
                .orElseThrow(() -> new RuntimeException("Genero no encontrado"));
            producto.setGenero(gen);
        }

        return productoRepository.save(producto);
    }

    @Transactional
    public void descontarStock(int id, int cantidad) {
        Producto producto = this.findById(id);

        if (producto.getStock() == null || producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(int id, Producto productoDetails) {
        Producto producto = this.findById(id);

        producto.setNombre(productoDetails.getNombre());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setImagen(productoDetails.getImagen()); 
        producto.setStock(productoDetails.getStock());
        
        // Actualizar relaciones si vienen en la petición
        if (productoDetails.getPlataforma() != null && productoDetails.getPlataforma().getId() != null) {
            Plataforma plat = plataformaRepository.findById(productoDetails.getPlataforma().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setPlataforma(plat);
        }

        if (productoDetails.getGenero() != null && productoDetails.getGenero().getId() != null) {
            Genero gen = generoRepository.findById(productoDetails.getGenero().getId())
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));
            producto.setGenero(gen);
        }

        return productoRepository.save(producto);
    }

    @Transactional
    public void deleteById(int id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El producto con ID " + id + " no existe");
        }
        productoRepository.deleteById(id);
    }

}
