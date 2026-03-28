package com.techstore.techstore_backend.mapper;

import com.techstore.techstore_backend.dto.ProductoRequestDTO;
import com.techstore.techstore_backend.dto.ProductoResponseDTO;
import com.techstore.techstore_backend.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    @Mapping(source = "categoria.id", target = "idCategoria")
    @Mapping(source = "categoria.nombre", target = "nombreCategoria")
    ProductoResponseDTO toResponseDto(Producto producto);

    @Mapping(source = "idCategoria", target = "categoria.id")
    Producto toEntity(ProductoRequestDTO requestDto);
}
