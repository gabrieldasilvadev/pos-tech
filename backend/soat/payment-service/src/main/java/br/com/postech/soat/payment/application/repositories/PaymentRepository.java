package br.com.postech.soat.payment.application.repositories;

import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import java.util.List;

public interface PaymentRepository {
    void save(Payment payment);

    Payment findById(PaymentId paymentId);

    List<Payment> findPendingPayments();
}
