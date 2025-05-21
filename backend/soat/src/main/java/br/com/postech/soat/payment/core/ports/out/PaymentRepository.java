package br.com.postech.soat.payment.core.ports.out;

import br.com.postech.soat.payment.core.domain.model.Payment;

public interface PaymentRepository {
    void save(Payment payment);
}
