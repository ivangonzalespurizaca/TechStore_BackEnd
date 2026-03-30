package com.techstore.techstore_backend.service.impl;

import com.techstore.techstore_backend.dto.CarritoRequestDTO;
import com.techstore.techstore_backend.dto.CarritoResponseDTO;
import com.techstore.techstore_backend.entity.Carrito;
import com.techstore.techstore_backend.entity.Producto;
import com.techstore.techstore_backend.entity.Usuario;
import com.techstore.techstore_backend.mapper.CarritoMapper;
import com.techstore.techstore_backend.repository.CarritoRepository;
import com.techstore.techstore_backend.repository.ProductoRepository;
import com.techstore.techstore_backend.repository.UsuarioRepository;
import com.techstore.techstore_backend.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService{

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarritoMapper carritoMapper;

    @Override
    @Transactional
    public void agregarProducto(CarritoRequestDTO dto) {
        // 1. Obtener usuario del Token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Verificar si el producto ya está en el carrito para este usuario
        Optional<Carrito> carritoExistente = carritoRepository.findByUsuarioIdAndProductoId(usuario.getId(), dto.getProductoId());

        if (carritoExistente.isPresent()) {
            // Si ya existe, solo aumentamos la cantidad
            Carrito item = carritoExistente.get();
            int cantidadFinal = item.getCantidad() + dto.getCantidad();

            if (cantidadFinal > item.getProducto().getStock()) {
                throw new RuntimeException("No hay suficiente stock disponible");
            }

            item.setCantidad(cantidadFinal);
            carritoRepository.save(item);
        } else {
            // Si no existe, buscamos el producto y creamos el registro
            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            Carrito nuevoItem = Carrito.builder()
                    .usuario(usuario)
                    .producto(producto)
                    .cantidad(dto.getCantidad())
                    .build();

            carritoRepository.save(nuevoItem);
        }
    }

    @Override
    public List<CarritoResponseDTO> listarMiCarrito() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return carritoRepository.findByUsuarioId(usuario.getId()).stream()
                .map(carritoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void eliminarProducto(Long productoId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        carritoRepository.deleteByUsuarioIdAndProductoId(usuario.getId(), productoId);
    }

    @Transactional
    @Override
    public void limpiarCarrito() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        carritoRepository.deleteByUsuarioId(usuario.getId());
    }
}
