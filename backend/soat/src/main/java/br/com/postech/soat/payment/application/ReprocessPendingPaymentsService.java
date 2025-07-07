package br.com.postech.soat.payment.application;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.application.gateway.PaymentGateway;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class ReprocessPendingPaymentsService {

    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(ReprocessPendingPaymentsService.class);

    public void reprocess() {
        List<Payment> pendingPayments = paymentRepository.findPendingPayments();
        logger.info("Reprocessing {} pending payments", pendingPayments.size());
        for (Payment payment : pendingPayments) {
            GatewayOperationResult result = paymentGateway.processPayment(payment);

            if (result == GatewayOperationResult.FAILURE) {
                logger.error("Payment processing failed for payment ID: {}", payment.getId());
                payment.fail();
                paymentRepository.save(payment);
                return;
            }

            payment.approve();
            logger.info("Payment processed successfully for payment ID: {}", payment.getId());

            paymentRepository.save(payment);
        }
    }
}
