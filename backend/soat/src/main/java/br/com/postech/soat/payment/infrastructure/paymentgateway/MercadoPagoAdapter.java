package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.gateway.GatewayOperationResult;
import br.com.postech.soat.payment.application.gateway.PaymentGateway;
import br.com.postech.soat.payment.domain.entity.Payment;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Monitorable
public class MercadoPagoAdapter implements PaymentGateway {
    private final CheckoutClient checkoutClient;
    private final Logger logger = LoggerFactory.getLogger(MercadoPagoAdapter.class);

    @Override
    public GatewayOperationResult processPayment(Payment payment) {
        logger.info("Processing payment with MercadoPagoAdapter for payment ID: {}", payment.getId());
        String paymentProcessed;
        try {
            paymentProcessed = checkoutClient.createPayment(payment);
        } catch (Exception e) {
            logger.error("Payment processing failed for payment ID: {}", payment.getId());
            return GatewayOperationResult.FAILURE;
        }

        if (Objects.isNull(paymentProcessed) || paymentProcessed.trim().isEmpty()) {
            logger.error("Payment processing failed for payment ID: {}", payment.getId());
            return GatewayOperationResult.FAILURE;
        }

        logger.info("Payment processed successfully for payment ID: {}", payment.getId());
        return GatewayOperationResult.SUCCESS;
    }
}
