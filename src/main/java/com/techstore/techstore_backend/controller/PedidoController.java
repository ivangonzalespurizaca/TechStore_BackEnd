package com.techstore.techstore_backend.controller;

import com.techstore.techstore_backend.dto.PedidoRequestDTO;
import com.techstore.techstore_backend.dto.PedidoResponseDTO;
import com.techstore.techstore_backend.entity.enums.EstadoPedido;
import com.techstore.techstore_backend.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody PedidoRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.registrarPedido(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> listarMisPedidos() {
        return ResponseEntity.ok(pedidoService.listarMisPedidos());
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos(
            @RequestParam(required = false) EstadoPedido estado) {

        if (estado != null) {
            return ResponseEntity.ok(pedidoService.listarPorEstado(estado));
        }
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        EstadoPedido nuevoEstado = EstadoPedido.valueOf(body.get("estado").toUpperCase());

        return ResponseEntity.ok(pedidoService.cambiarEstado(id, nuevoEstado));
    }
}
