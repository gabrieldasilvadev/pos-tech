package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentId;

public interface FakeCheckoutClient {
    String createPayment(Payment payment);

    String getPaymentDetails(PaymentId paymentId);
}
