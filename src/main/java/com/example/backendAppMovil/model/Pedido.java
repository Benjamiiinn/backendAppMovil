package com.example.backendAppMovil.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; 

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Integer total; // Total pagado

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String metodoPago;

    // Un pedido tiene muchos detalles (muchos juegos)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;
}
