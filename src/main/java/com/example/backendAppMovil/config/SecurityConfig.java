package com.example.backendAppMovil.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.example.backendAppMovil.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf( csrf ->
                csrf
                .disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authRequest ->
                authRequest
                .requestMatchers("/auth/**").permitAll() //Reg

                .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").authenticated() //Si o si tener una cuenta
                .requestMatchers(HttpMethod.GET, "/api/v1/plataformas/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/generos/**").authenticated()

                //Solo ADMIN puede crear, editar o borrar productos
                .requestMatchers(HttpMethod.POST, "/api/v1/productos/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/productos/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/productos/**").hasAuthority("ADMIN")

                // Plataformas
                .requestMatchers(HttpMethod.POST, "/api/v1/plataformas/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/plataformas/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/plataformas/**").hasAuthority("ADMIN")

                //Generos
                .requestMatchers(HttpMethod.POST, "/api/v1/generos/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/generos/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/generos/**").hasAuthority("ADMIN")

                // El usuario normal solo puede comprar y ver SUS pedidos
                .requestMatchers("/api/v1/pedidos/checkout").authenticated()
                .requestMatchers("/api/v1/pedidos/mis-pedidos").authenticated()

                // El ADMIN puede ver TODOS los pedidos (el endpoint GET general)
                .requestMatchers(HttpMethod.GET, "/api/v1/pedidos").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/pedidos/**").hasAuthority("ADMIN")

                // Permitimos ver detalles si estás logueado (la lógica de "si es mi pedido" se puede validar en el service)
                .requestMatchers("/api/v1/detalles/**").authenticated()

                // Para permitir que los usuarios puedan ver sus datos.
                .requestMatchers("/api/v1/usuarios/**").authenticated()

                // Pero solo el ADMIN puede crear, editar o borrar usuarios manualmente
                .requestMatchers(HttpMethod.POST, "/api/v1/usuarios/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/usuarios/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                )
            .sessionManagement(sessionManager ->
                sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
