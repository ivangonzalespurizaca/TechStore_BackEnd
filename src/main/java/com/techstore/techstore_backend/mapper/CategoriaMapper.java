package com.techstore.techstore_backend.mapper;

import com.techstore.techstore_backend.dto.CategoriaResponseDTO;
import com.techstore.techstore_backend.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaResponseDTO toDto(Categoria categoria);
}
