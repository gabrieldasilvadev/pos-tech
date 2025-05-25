package br.com.postech.soat.payment.core.ports.out;

import br.com.postech.soat.payment.core.domain.model.Payment;

public interface PaymentGateway {
    GatewayOperationResult processPayment(Payment payment);
}
