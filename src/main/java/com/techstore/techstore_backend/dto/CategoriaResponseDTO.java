package com.techstore.techstore_backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoriaResponseDTO {
    private Long id;
    private String nombre;
}
