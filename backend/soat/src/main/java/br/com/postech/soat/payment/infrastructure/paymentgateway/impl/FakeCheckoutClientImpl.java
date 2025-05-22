package br.com.postech.soat.payment.infrastructure.paymentgateway.impl;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Monitorable
public class FakeCheckoutClientImpl implements FakeCheckoutClient {

    @Override
    public String createPayment(Payment payment) {
        return "Your payment was processed successfully!";
    }

    @Override
    public String getPaymentDetails(PaymentId paymentId) {
        return "Payment details for ID: " + paymentId.getValue();
    }
}
