package br.com.postech.soat.payment.domain.entity;

import java.util.Arrays;

public enum PaymentMethod {
    PIX;

    public static PaymentMethod entryOf(String paymentMethod) {
        return Arrays.stream(PaymentMethod.values())
            .filter(method -> method.name().equalsIgnoreCase(paymentMethod))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid payment method: " + paymentMethod));
    }
}
