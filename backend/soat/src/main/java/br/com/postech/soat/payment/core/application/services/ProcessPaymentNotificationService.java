package br.com.postech.soat.payment.core.application.services;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import br.com.postech.soat.payment.core.ports.in.ProcessPaymentNotificationUseCase;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Monitorable
@RequiredArgsConstructor
public class ProcessPaymentNotificationService implements ProcessPaymentNotificationUseCase {
    private final PaymentRepository paymentRepository;
    private final FakeCheckoutClient fakeCheckoutClient;

    @Override
    public void processPaymentNotification(PaymentId paymentId) {

        final String paymentProcessed = fakeCheckoutClient.getPaymentDetails(paymentId);
        if (paymentProcessed == null || paymentProcessed.trim().isEmpty()) {
            throw new RuntimeException("Failed to process payment notification");
        }

        final Payment payment = paymentRepository.findById(paymentId);

        payment.finish();
        paymentRepository.save(payment);
    }
}
