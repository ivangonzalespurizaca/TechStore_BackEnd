package com.techstore.techstore_backend.repository;

import com.techstore.techstore_backend.entity.Pedido;
import com.techstore.techstore_backend.entity.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
    List<Pedido> findAllByOrderByFechaDesc();
    List<Pedido> findByEstadoOrderByFechaDesc(EstadoPedido estado);
}
