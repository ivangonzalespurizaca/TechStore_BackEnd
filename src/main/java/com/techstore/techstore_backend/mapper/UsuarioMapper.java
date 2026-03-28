package com.techstore.techstore_backend.mapper;

import com.techstore.techstore_backend.dto.UsuarioResponseDTO;
import com.techstore.techstore_backend.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponseDTO toDto(Usuario usuario);
}
