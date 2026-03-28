package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.CarritoRequestDTO;
import com.techstore.techstore_backend.dto.CarritoResponseDTO;

import java.util.List;

public interface CarritoService {
    void agregarProducto(CarritoRequestDTO dto);
    List<CarritoResponseDTO> listarMiCarrito();
    void eliminarProducto(Long productoId);
    void limpiarCarrito();
}
