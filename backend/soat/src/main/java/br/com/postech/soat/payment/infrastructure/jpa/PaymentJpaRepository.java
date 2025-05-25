package br.com.postech.soat.payment.infrastructure.jpa;

import br.com.postech.soat.payment.core.domain.model.PaymentStatus;
import br.com.postech.soat.payment.infrastructure.jpa.entity.PaymentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {
  List<PaymentEntity> findByStatus(PaymentStatus status);
}
