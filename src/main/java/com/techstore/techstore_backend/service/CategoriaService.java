package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaResponseDTO> listarTodas();
}
