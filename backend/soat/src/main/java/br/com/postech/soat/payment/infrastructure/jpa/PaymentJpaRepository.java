package br.com.postech.soat.payment.infrastructure.jpa;

import br.com.postech.soat.payment.infrastructure.jpa.entity.PaymentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {
}
