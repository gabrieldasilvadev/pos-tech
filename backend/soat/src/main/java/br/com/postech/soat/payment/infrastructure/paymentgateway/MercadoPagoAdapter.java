package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.application.gateway.GatewayOperationResult;
import br.com.postech.soat.payment.application.gateway.PaymentGateway;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Monitorable
public class MercadoPagoAdapter implements PaymentGateway {
    private final FakeCheckoutClient fakeCheckoutClient;
    private final Logger logger = LoggerFactory.getLogger(MercadoPagoAdapter.class);

    @Override
    public GatewayOperationResult processPayment(Payment payment) {
        logger.info("Processing payment with MercadoPagoAdapter for payment ID: {}", payment.getId());
        String paymentProcessed = fakeCheckoutClient.createPayment(payment);
        if (Objects.isNull(paymentProcessed) || paymentProcessed.trim().isEmpty()) {
            logger.error("Payment processing failed for payment ID: {}", payment.getId());
            return GatewayOperationResult.FAILURE;
        }
        logger.info("Payment processed successfully for payment ID: {}", payment.getId());
        return GatewayOperationResult.SUCCESS;
    }
}
