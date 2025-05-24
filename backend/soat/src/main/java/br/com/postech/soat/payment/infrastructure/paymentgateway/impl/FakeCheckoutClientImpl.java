package br.com.postech.soat.payment.infrastructure.paymentgateway.impl;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Monitorable
public class FakeCheckoutClientImpl implements FakeCheckoutClient {

    private final Logger logger = LoggerFactory.getLogger(FakeCheckoutClientImpl.class);

    @Override
    public String createPayment(Payment payment) {
        logger.info("Creating payment with ID: {}", payment.getId().getValue());
        return "Your payment was processed successfully!";
    }

    @Override
    public String getPaymentDetails(PaymentId paymentId) {
        logger.info("Fetching payment details for ID: {}", paymentId.getValue());
        return "Payment details for ID: " + paymentId.getValue();
    }
}
