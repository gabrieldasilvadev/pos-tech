package br.com.postech.soat.payment.application;

import br.com.postech.soat.payment.domain.entity.PaymentId;

public interface ProcessPaymentNotificationUseCase {
    void processPaymentNotification(PaymentId paymentId);
}
