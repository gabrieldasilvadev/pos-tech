package br.com.postech.soat.payment.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.command.InitiatePaymentCommand;
import br.com.postech.soat.payment.application.gateway.GatewayOperationResult;
import br.com.postech.soat.payment.application.gateway.PaymentGateway;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentId;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class InitiatePaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;
    private final Logger logger = LoggerFactory.getLogger(InitiatePaymentUseCase.class);

    public PaymentId process(InitiatePaymentCommand command) {
        final Payment payment = Payment.initiate(
            command.orderId(),
            command.customerId(),
            command.paymentMethod(),
            command.amount());

        final GatewayOperationResult result = paymentGateway.processPayment(payment);

        if (result.equals(GatewayOperationResult.FAILURE)) {
            logger.error("Payment processing failed for payment ID: {}", payment.getId());
            payment.fail();
            paymentRepository.save(payment);
            return payment.getId();
        }

        if (result.equals(GatewayOperationResult.SUCCESS)) {
            payment.approve();
            paymentRepository.save(payment);
            logger.info("Payment processed successfully for payment ID: {}", payment.getId());
        }

        return payment.getId();
    }
}
