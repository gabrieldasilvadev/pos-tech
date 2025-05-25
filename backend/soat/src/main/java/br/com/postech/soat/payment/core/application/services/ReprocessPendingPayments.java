package br.com.postech.soat.payment.core.application.services;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.ports.out.GatewayOperationResult;
import br.com.postech.soat.payment.core.ports.out.PaymentGateway;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class ReprocessPendingPayments {

    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(ReprocessPendingPayments.class);

    public void reprocess() {
        List<Payment> pendingPayments = paymentRepository.findPendingPayments();
        logger.info("Reprocessing {} pending payments", pendingPayments.size());
        for (Payment payment : pendingPayments) {
            GatewayOperationResult result = paymentGateway.processPayment(payment);

            if (result == GatewayOperationResult.FAILURE) {
                logger.error("Payment processing failed for payment ID: {}", payment.getId());
                payment.fail();
                paymentRepository.save(payment);
                payment.approve();
                return;
            }

            payment.approve();
            logger.info("Payment processed successfully for payment ID: {}", payment.getId());

            paymentRepository.save(payment);
        }
    }
}
