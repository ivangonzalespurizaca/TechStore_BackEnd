package com.techstore.techstore_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PedidoRequestDTO {

    @NotBlank(message = "El celular es obligatorio")
    @Size(min = 9, max = 20, message = "El celular debe tener entre 9 y 20 dígitos")
    private String celular;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
}
