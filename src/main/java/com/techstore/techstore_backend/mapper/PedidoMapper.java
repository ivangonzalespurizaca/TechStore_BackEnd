package com.techstore.techstore_backend.mapper;

import com.techstore.techstore_backend.dto.CarritoResponseDTO;
import com.techstore.techstore_backend.dto.PedidoResponseDTO;
import com.techstore.techstore_backend.entity.DetallePedido;
import com.techstore.techstore_backend.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    @Mapping(source = "usuario.nombre", target = "clienteNombre")
    @Mapping(source = "detalles", target = "detalles") // MapStruct usará el CarritoMapper para cada item
    PedidoResponseDTO toResponseDto(Pedido pedido);

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    @Mapping(source = "producto.imagenUrl", target = "imagenUrl")
    @Mapping(source = "precioUnitario", target = "precioUnitario")
    @Mapping(source = "subtotal", target = "subtotal")
    CarritoResponseDTO toDetalleDto(DetallePedido detalle);
}
