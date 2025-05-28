package br.com.postech.soat.payment.core.domain.model;

import java.util.Arrays;

public enum PaymentStatus {
    PENDING,
    APPROVED,
    FINISHED,
    DECLINED,
    FAILED;

    public static PaymentStatus entryOf(String paymentStatus) {
        return Arrays.stream(PaymentStatus.values())
            .filter(status -> status.name().equalsIgnoreCase(paymentStatus))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid payment status: " + paymentStatus));
    }
}
