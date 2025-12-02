package com.example.backendAppMovil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backendAppMovil.model.Usuario;
import com.example.backendAppMovil.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional
    public Usuario guardar(Usuario usuario) {
        // Encriptamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizar(Integer id, Usuario usuarioDetalles) {
        Usuario usuario = buscarPorId(id);

        usuario.setNombre(usuarioDetalles.getNombre());
        usuario.setUsername(usuarioDetalles.getUsername());
        usuario.setRut(usuarioDetalles.getRut());
        usuario.setDireccion(usuarioDetalles.getDireccion());
        usuario.setTelefono(usuarioDetalles.getTelefono());
        
        // Solo actualizamos la contraseña si viene una nueva y no está vacía
        if (usuarioDetalles.getPassword() != null && !usuarioDetalles.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDetalles.getPassword()));
        }
        
        // Opcional: Actualizar Rol si es necesario
        if (usuarioDetalles.getRol() != null) {
            usuario.setRol(usuarioDetalles.getRol());
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: El usuario no existe");
        }
        usuarioRepository.deleteById(id);
    }
}
