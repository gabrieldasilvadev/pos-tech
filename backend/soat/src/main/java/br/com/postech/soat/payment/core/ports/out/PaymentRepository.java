package br.com.postech.soat.payment.core.ports.out;

import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import java.util.List;

public interface PaymentRepository {
    void save(Payment payment);
    Payment findById(PaymentId paymentId);
    List<Payment> findPendingPayments();
}
