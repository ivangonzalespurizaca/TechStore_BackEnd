package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.PedidoRequestDTO;
import com.techstore.techstore_backend.dto.PedidoResponseDTO;
import com.techstore.techstore_backend.entity.enums.EstadoPedido;

import java.util.List;

public interface PedidoService {
    PedidoResponseDTO registrarPedido(PedidoRequestDTO dto);
    List<PedidoResponseDTO> listarTodos();
    List<PedidoResponseDTO> listarMisPedidos();
    List<PedidoResponseDTO> listarPorEstado(EstadoPedido estado);
    PedidoResponseDTO cambiarEstado(Long id, EstadoPedido nuevoEstado);
}
