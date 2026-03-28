package com.techstore.techstore_backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String clienteNombre;
    private String celular;
    private String direccion;
    private String estado;
    private BigDecimal total;
    private List<CarritoResponseDTO> detalles;
}
