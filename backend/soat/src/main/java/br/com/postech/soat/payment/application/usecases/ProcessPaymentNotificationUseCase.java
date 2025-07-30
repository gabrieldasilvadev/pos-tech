package br.com.postech.soat.payment.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.paymentgateway.CheckoutClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Monitorable
@RequiredArgsConstructor
public class ProcessPaymentNotificationUseCase {

    private final PaymentRepository paymentRepository;
    private final CheckoutClient checkoutClient;

    public void execute(PaymentId paymentId) {

        final String paymentProcessed = checkoutClient.getPaymentDetails(paymentId);

        if (paymentProcessed == null || paymentProcessed.trim().isEmpty()) {
            throw new RuntimeException("Failed to process payment notification");
        }

        final Payment payment = paymentRepository.findById(paymentId);

        // Parse payment status from fake gateway response
        PaymentStatus gatewayStatus = parsePaymentStatusFromResponse(paymentProcessed);

        // Update payment based on gateway status
        switch (gatewayStatus) {
            case APPROVED -> {
                payment.finish();
            }
            case DECLINED -> {
                payment.decline();
            }
            case FAILED -> {
                payment.fail();
            }
            default -> {
                throw new IllegalStateException("Unexpected payment status: " + gatewayStatus);
            }
        }

        paymentRepository.save(payment);
    }

    private PaymentStatus parsePaymentStatusFromResponse(String paymentProcessed) {
        // Parse the status from the fake gateway response
        // Example: "Payment details for ID: uuid, Status: APPROVED, Amount: 100.0"
        if (paymentProcessed.contains("Status: DECLINED")) {
            return PaymentStatus.DECLINED;
        } else if (paymentProcessed.contains("Status: FAILED")) {
            return PaymentStatus.FAILED;
        } else if (paymentProcessed.contains("Status: APPROVED")) {
            return PaymentStatus.APPROVED;
        } else {
            return PaymentStatus.APPROVED; // Default to approved for backward compatibility
        }
    }
}
