package com.techstore.techstore_backend.service.impl;

import com.techstore.techstore_backend.dto.CategoriaResponseDTO;
import com.techstore.techstore_backend.mapper.CategoriaMapper;
import com.techstore.techstore_backend.repository.CategoriaRepository;
import com.techstore.techstore_backend.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDto)
                .toList();
    }
}
