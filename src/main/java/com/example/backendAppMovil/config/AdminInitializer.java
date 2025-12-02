package com.example.backendAppMovil.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.backendAppMovil.model.Rol;
import com.example.backendAppMovil.model.Usuario;
import com.example.backendAppMovil.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminInitializer {
    @Bean
    @Transactional
    public CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica si ya existe el admin para no duplicarlo
            // Usamos el email que quieras como 'username' de admin
            String emailAdmin = "admin@tienda.cl";

            if (usuarioRepository.findByUsername(emailAdmin).isEmpty()) {
                
                System.out.println("Creando usuario ADMINISTRADOR por defecto...");

                Usuario admin = Usuario.builder()
                        .nombre("Super Admin")
                        .username(emailAdmin)
                        .password(passwordEncoder.encode("Admin1234.*")) // <--- TU CONTRASEÑA SEGURA
                        .rut("253037695")
                        .direccion("Oficina Central")
                        .telefono(123456789)
                        .rol(Rol.ADMIN)
                        .build();

                usuarioRepository.save(admin);
                
                System.out.println("Admin creado: " + emailAdmin + " / Pass: Admin1234.*");
            } else {
                System.out.println("El usuario Admin ya existe.");
            }
        };
    }
}
