package br.com.postech.soat.payment.core.application.services;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.ports.out.GatewayOperationResult;
import br.com.postech.soat.payment.core.ports.out.PaymentGateway;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class ReprocessPendingPayments {

    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;

    public void reprocess() {
        List<Payment> pendingPayments = paymentRepository.findPendingPayments();
        for (Payment payment : pendingPayments) {
            GatewayOperationResult result = paymentGateway.processPayment(payment);
            if (result == GatewayOperationResult.SUCCESS) {
                payment.approve();
            } else {
                payment.fail();
            }
            paymentRepository.save(payment);
        }
    }
}
