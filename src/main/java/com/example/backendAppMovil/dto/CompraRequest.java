package com.example.backendAppMovil.dto;

import java.util.List;

import lombok.Data;

@Data
public class CompraRequest {
    
    private String metodoPago;
    
    private List<ItemCompra> items;
    
    @Data
    public static class ItemCompra {
        private Integer rawgId;
        private Integer cantidad;
    }

}
