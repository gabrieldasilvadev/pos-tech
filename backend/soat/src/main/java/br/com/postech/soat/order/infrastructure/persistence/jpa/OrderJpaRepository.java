package br.com.postech.soat.order.infrastructure.persistence.jpa;

import br.com.postech.soat.order.domain.entity.OrderStatus;
import br.com.postech.soat.order.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    
    @Query("""
        SELECT o FROM OrderEntity o 
        WHERE o.status IN :activeStatusList
        ORDER BY 
            CASE o.status 
                WHEN 'DONE' THEN 1
                WHEN 'IN_PREPARATION' THEN 2  
                WHEN 'RECEIVED' THEN 3
                ELSE 4
            END,
            o.createdAt ASC
        """)
    List<OrderEntity> findActiveOrdersSorted(List<OrderStatus> activeStatusList);
}
