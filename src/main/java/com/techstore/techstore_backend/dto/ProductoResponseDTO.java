package com.techstore.techstore_backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String imagenUrl;
    private Boolean activo;
    private Long idCategoria;
    private String nombreCategoria;
}
