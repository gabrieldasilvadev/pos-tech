package br.com.postech.soat.payment.application.gateway;

import br.com.postech.soat.payment.domain.entity.Payment;

public interface PaymentGateway {
    GatewayOperationResult processPayment(Payment payment);
}
