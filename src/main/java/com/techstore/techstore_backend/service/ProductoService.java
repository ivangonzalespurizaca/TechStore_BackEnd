package com.techstore.techstore_backend.service;

import com.techstore.techstore_backend.dto.ProductoRequestDTO;
import com.techstore.techstore_backend.dto.ProductoResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {
    List<ProductoResponseDTO> listarTodo();
    List<ProductoResponseDTO> buscarPorCategoria(Long idCat);
    ProductoResponseDTO obtenerPorId(Long id);
    ProductoResponseDTO guardarConImagen(ProductoRequestDTO saveDTO, MultipartFile archivo);
    void eliminarLogico(Long id);
}
