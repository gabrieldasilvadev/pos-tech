package br.com.postech.soat.order.infrastructure.jpa;

import br.com.postech.soat.order.infrastructure.jpa.entity.OrderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}
