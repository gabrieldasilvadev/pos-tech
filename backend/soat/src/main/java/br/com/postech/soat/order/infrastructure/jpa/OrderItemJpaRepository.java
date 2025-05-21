package br.com.postech.soat.order.infrastructure.jpa;

import br.com.postech.soat.order.infrastructure.jpa.entity.OrderItemEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, UUID> {
}
