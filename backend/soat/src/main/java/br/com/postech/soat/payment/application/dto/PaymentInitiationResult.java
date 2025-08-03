package br.com.postech.soat.payment.application.dto;

import br.com.postech.soat.payment.domain.valueobject.PaymentId;

public record PaymentInitiationResult(
    PaymentId paymentId,
    String paymentUrl
) {
}
