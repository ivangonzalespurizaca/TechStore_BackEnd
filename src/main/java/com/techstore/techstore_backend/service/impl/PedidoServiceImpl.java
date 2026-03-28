package com.techstore.techstore_backend.service.impl;

import com.techstore.techstore_backend.dto.PedidoRequestDTO;
import com.techstore.techstore_backend.dto.PedidoResponseDTO;
import com.techstore.techstore_backend.entity.*;
import com.techstore.techstore_backend.entity.enums.EstadoPedido;
import com.techstore.techstore_backend.mapper.CarritoMapper;
import com.techstore.techstore_backend.mapper.PedidoMapper;
import com.techstore.techstore_backend.repository.CarritoRepository;
import com.techstore.techstore_backend.repository.PedidoRepository;
import com.techstore.techstore_backend.repository.ProductoRepository;
import com.techstore.techstore_backend.repository.UsuarioRepository;
import com.techstore.techstore_backend.service.PedidoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final PedidoMapper pedidoMapper;
    private final CarritoMapper carritoMapper;
    private final UsuarioRepository usuarioRepository;
    private final CarritoRepository carritoRepository;

    @Override
    @Transactional // Si algo falla, se hace Rollback
    public PedidoResponseDTO registrarPedido(PedidoRequestDTO dto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Carrito> itemsCarrito = carritoRepository.findByUsuarioId(usuario.getId());
        if (itemsCarrito.isEmpty()) {
            throw new RuntimeException("No hay productos en el carrito para procesar el pedido");
        }

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setUsuario(usuario);
        pedido.setCelular(dto.getCelular());
        pedido.setDireccion(dto.getDireccion());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal totalAcumulado = BigDecimal.ZERO;

        for (Carrito item : itemsCarrito){
            Producto producto = item.getProducto();

            // Regla de negocio vital para evitar ventas sin producto físico
            if(producto.getStock() < item.getCantidad()){
                throw new RuntimeException("Stock insuficiente para producto: " + producto.getNombre() + ". Stock disponible: " + producto.getStock());
            }

            // Descontamos el stock del producto
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            // Creamos el detalle del pedido
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            // Calculamos el subtotal del detalle (precio unitario * cantidad)
            BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal(item.getCantidad()));
            detalle.setSubtotal(subtotal);

            totalAcumulado = totalAcumulado.add(subtotal);
            detalles.add(detalle);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(totalAcumulado);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        carritoRepository.deleteByUsuarioId(usuario.getId()); // Limpiamos el carrito después de guardar el pedido
        return pedidoMapper.toResponseDto(pedidoGuardado);
    }

    @Override
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAllByOrderByFechaDesc().stream()
                .map(pedidoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<PedidoResponseDTO> listarMisPedidos() {
        // Obtener el email del usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario por email para obtener su ID
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        return pedidoRepository.findByUsuarioIdOrderByFechaDesc(usuario.getId()).stream()
                .map(pedidoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<PedidoResponseDTO> listarPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstadoOrderByFechaDesc(estado).stream()
                .map(pedidoMapper::toResponseDto)
                .toList();
    }

    @Override
    public PedidoResponseDTO cambiarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));

        pedido.setEstado(nuevoEstado);
        return pedidoMapper.toResponseDto(pedidoRepository.save(pedido));
    }
}