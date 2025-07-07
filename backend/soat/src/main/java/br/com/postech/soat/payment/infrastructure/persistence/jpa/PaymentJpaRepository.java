package br.com.postech.soat.payment.infrastructure.persistence.jpa;

import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.infrastructure.persistence.entity.PaymentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {
    List<PaymentEntity> findByStatus(PaymentStatus status);
}
