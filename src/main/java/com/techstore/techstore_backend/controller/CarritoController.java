package com.techstore.techstore_backend.controller;

import com.techstore.techstore_backend.dto.CarritoRequestDTO;
import com.techstore.techstore_backend.dto.CarritoResponseDTO;
import com.techstore.techstore_backend.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {
    private final CarritoService carritoService;

    @PostMapping("/agregar")
    public ResponseEntity<Void> agregarAlCarrito(@Valid @RequestBody CarritoRequestDTO dto) {
        carritoService.agregarProducto(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CarritoResponseDTO>> listarMiCarrito() {
        return ResponseEntity.ok(carritoService.listarMiCarrito());
    }

    @DeleteMapping("/eliminar/{productoId}")
    public ResponseEntity<Void> eliminarDelCarrito(@PathVariable Long productoId) {
        carritoService.eliminarProducto(productoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/limpiar")
    public ResponseEntity<Void> limpiarCarrito() {
        carritoService.limpiarCarrito();
        return ResponseEntity.noContent().build();
    }
}
