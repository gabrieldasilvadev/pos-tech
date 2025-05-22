package br.com.postech.soat.payment.adapters.out.gateway;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.ports.out.GatewayOperationResult;
import br.com.postech.soat.payment.core.ports.out.PaymentGateway;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Monitorable
public class MercadoPagoAdapter implements PaymentGateway {
    private final FakeCheckoutClient fakeCheckoutClient;

    @Override
    public GatewayOperationResult processPayment(Payment payment) {
        String paymentProcessed = fakeCheckoutClient.createPayment(payment);
        if (Objects.isNull(paymentProcessed) || paymentProcessed.trim().isEmpty()) {
            return GatewayOperationResult.FAILURE;
        }
        return GatewayOperationResult.SUCCESS;
    }
}
