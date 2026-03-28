package com.techstore.techstore_backend.mapper;

import com.techstore.techstore_backend.dto.CarritoResponseDTO;
import com.techstore.techstore_backend.entity.Carrito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarritoMapper {
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    @Mapping(source = "producto.imagenUrl", target = "imagenUrl")
    @Mapping(source = "producto.precio", target = "precioUnitario")
    @Mapping(target = "subtotal", expression = "java(java.math.BigDecimal.valueOf(entity.getCantidad()).multiply(entity.getProducto().getPrecio()))")
    CarritoResponseDTO toDto(Carrito entity);
}
