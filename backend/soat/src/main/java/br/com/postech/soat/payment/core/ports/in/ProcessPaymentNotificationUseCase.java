package br.com.postech.soat.payment.core.ports.in;

import br.com.postech.soat.payment.core.domain.model.PaymentId;

public interface ProcessPaymentNotificationUseCase {
    void processPaymentNotification(PaymentId paymentId);
}
