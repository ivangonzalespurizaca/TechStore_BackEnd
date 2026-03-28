package com.techstore.techstore_backend.controller;

import com.techstore.techstore_backend.dto.ProductoRequestDTO;
import com.techstore.techstore_backend.dto.ProductoResponseDTO;
import com.techstore.techstore_backend.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodo() {
        return ResponseEntity.ok(productoService.listarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping("/categoria/{idCat}")
    public ResponseEntity<List<ProductoResponseDTO>> filtrarPorCategoria(@PathVariable Long idCat) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(idCat));
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductoResponseDTO> guardar(
            @RequestPart("producto") @Valid ProductoRequestDTO dto,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.guardarConImagen(dto, archivo));
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestPart("producto") @Valid ProductoRequestDTO dto,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo) {

        dto.setId(id);

        return ResponseEntity.ok(productoService.guardarConImagen(dto, archivo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
