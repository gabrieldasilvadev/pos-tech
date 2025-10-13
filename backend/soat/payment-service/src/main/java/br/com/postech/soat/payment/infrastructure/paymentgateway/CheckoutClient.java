package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import org.springframework.lang.Nullable;

public interface CheckoutClient {
    @Nullable
    String createPayment(Payment paymentId);

    String getPaymentDetails(PaymentId paymentId);
}
