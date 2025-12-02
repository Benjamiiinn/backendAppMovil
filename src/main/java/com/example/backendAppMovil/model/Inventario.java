package com.example.backendAppMovil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(nullable = false, unique = true)
    private Integer rawgId; // Este ID viene de la API de RAWG 

    @Column(nullable = false)
    private Integer precio; // Tu precio en CLP (ej: 60000)

    @Column(nullable = false)
    private Integer stock;  // Cantidad disponible para vender

}
