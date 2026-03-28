package com.techstore.techstore_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CarritoResponseDTO {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private String imagenUrl;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private BigDecimal subtotal;
}
