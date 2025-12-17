package com.example.backendAppMovil.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backendAppMovil.jwt.JwtService;
import com.example.backendAppMovil.model.Rol;
import com.example.backendAppMovil.model.Usuario;
import com.example.backendAppMovil.repository.UsuarioRepository;
import com.example.backendAppMovil.util.RutUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.getToken(usuario);
        
        return AuthResponse.builder()
            .token(token)
            .role(usuario.getRol().name())
            .userId(usuario.getId())
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (!RutUtils.validarRut(request.getRut())) {
            throw new IllegalArgumentException("El RUT ingresado no es valido");
        }

        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .nombre(request.getNombre())
                .rut(request.getRut())
                .rol(Rol.CLIENTE)
                .build();

        usuarioRepository.save(usuario);

        return AuthResponse.builder()
            .token(jwtService.getToken(usuario))
            .role(usuario.getRol().name())
            .userId(usuario.getId())
            .build();
    }

    public AuthResponse registerAdmin(RegisterRequest request) {
        if (!RutUtils.validarRut(request.getRut())) {
            throw new IllegalArgumentException("El RUT ingresado no es valido");
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .nombre(request.getNombre())
                .rut(request.getRut())
                .rol(Rol.ADMIN)
                .build();
        
        usuarioRepository.save(usuario);

        return AuthResponse.builder()
            .token(jwtService.getToken(usuario))
            .build();
    }

}
