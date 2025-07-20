package br.com.postech.soat.payment.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Monitorable
@RequiredArgsConstructor
public class ProcessPaymentNotificationUseCase {

    private final PaymentRepository paymentRepository;
    private final FakeCheckoutClient fakeCheckoutClient;
    private final Logger logger = LoggerFactory.getLogger(ProcessPaymentNotificationUseCase.class);

    public void execute(PaymentId paymentId) {
        logger.info("Processing payment notification for payment ID: {}", paymentId.getValue());

        final String paymentDetails = fakeCheckoutClient.getPaymentDetails(paymentId);
        if (paymentDetails == null || paymentDetails.trim().isEmpty()) {
            logger.error("Failed to get payment details for payment ID: {}", paymentId.getValue());
            throw new RuntimeException("Failed to process payment notification");
        }

        final Payment payment = paymentRepository.findById(paymentId);
        logger.info("Found payment with status: {} for ID: {}", payment.getStatus(), paymentId.getValue());

        // Parse payment status from fake gateway response
        PaymentStatus gatewayStatus = parsePaymentStatusFromResponse(paymentDetails);
        logger.info("Gateway reported status: {} for payment ID: {}", gatewayStatus, paymentId.getValue());

        // Update payment based on gateway status
        switch (gatewayStatus) {
            case APPROVED -> {
                payment.finish();
                logger.info("Payment finished successfully for ID: {}", paymentId.getValue());
            }
            case DECLINED -> {
                payment.decline();
                logger.info("Payment declined for ID: {}", paymentId.getValue());
            }
            case FAILED -> {
                payment.fail();
                logger.info("Payment failed for ID: {}", paymentId.getValue());
            }
            default -> {
                logger.warn("Unexpected payment status from gateway: {} for ID: {}", gatewayStatus, paymentId.getValue());
                throw new IllegalStateException("Unexpected payment status: " + gatewayStatus);
            }
        }

        paymentRepository.save(payment);
        logger.info("Payment notification processed successfully for ID: {}", paymentId.getValue());
    }

    private PaymentStatus parsePaymentStatusFromResponse(String paymentDetails) {
        // Parse the status from the fake gateway response
        // Example: "Payment details for ID: uuid, Status: APPROVED, Amount: 100.0"
        if (paymentDetails.contains("Status: DECLINED")) {
            return PaymentStatus.DECLINED;
        } else if (paymentDetails.contains("Status: FAILED")) {
            return PaymentStatus.FAILED;
        } else if (paymentDetails.contains("Status: APPROVED")) {
            return PaymentStatus.APPROVED;
        } else {
            logger.warn("Could not parse status from payment details: {}", paymentDetails);
            return PaymentStatus.APPROVED; // Default to approved for backward compatibility
        }
    }
}
