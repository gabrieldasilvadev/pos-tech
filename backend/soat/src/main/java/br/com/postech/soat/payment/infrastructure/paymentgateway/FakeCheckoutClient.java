package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;

public interface FakeCheckoutClient {
    String createPayment(Payment payment);

    String getPaymentDetails(PaymentId paymentId);
}
