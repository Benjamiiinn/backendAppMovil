package com.example.backendAppMovil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_pedido")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnore
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer precioUnitario; 

    @Column(nullable = false)
    private Integer cantidad;

    @Column(columnDefinition = "TEXT")
    private String licencias;

    public Integer getSubtotal() {
        return this.precioUnitario* this.cantidad;
    }
}
