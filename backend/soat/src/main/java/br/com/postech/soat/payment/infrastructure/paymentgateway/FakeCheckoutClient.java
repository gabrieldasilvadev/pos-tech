package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Monitorable
@Profile("fakeCheckoutClient")
public class FakeCheckoutClient implements CheckoutClient {

    private final Logger logger = LoggerFactory.getLogger(FakeCheckoutClient.class);
    private final Map<String, Payment> paymentStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @Value("${payment.gateway.simulation.delay.enabled:true}")
    private boolean simulateDelay;

    @Value("${payment.gateway.simulation.delay.min:100}")
    private int minDelayMs;

    @Value("${payment.gateway.simulation.delay.max:1000}")
    private int maxDelayMs;

    @Value("${payment.gateway.simulation.failure.rate:0.1}")
    private double failureRate;

    @Value("${payment.gateway.simulation.timeout.rate:0.05}")
    private double timeoutRate;

    @Value("${payment.gateway.simulation.decline.rate:0.15}")
    private double declineRate;

    public String createPayment(Payment payment) {
        logger.info("Creating payment with ID: {}", payment.getId().getValue());

        paymentStore.put(payment.getId().getValue().toString(), payment);

        simulateNetworkDelay();

        if (shouldSimulateTimeout()) {
            logger.warn("Simulating timeout for payment ID: {}", payment.getId().getValue());
            throw new RuntimeException("Payment gateway timeout");
        }

        if (shouldSimulateFailure()) {
            logger.warn("Simulating failure for payment ID: {}", payment.getId().getValue());
            return "";
        }

        logger.info("Payment created successfully with ID: {}", payment.getId().getValue());
        return "Your payment was processed successfully!";
    }

    public String getPaymentDetails(PaymentId paymentId) {
        logger.info("Fetching payment details for ID: {}", paymentId.getValue());

        simulateNetworkDelay();

        if (shouldSimulateTimeout()) {
            logger.warn("Simulating timeout for payment details request ID: {}", paymentId.getValue());
            throw new RuntimeException("Payment gateway timeout");
        }

        if (shouldSimulateFailure()) {
            logger.warn("Simulating failure for payment details request ID: {}", paymentId.getValue());
            return "";
        }

        Payment payment = paymentStore.get(paymentId.getValue().toString());
        if (payment != null) {
            return String.format("Payment details for ID: %s, Status: %s, Amount: %s",
                paymentId.getValue(),
                payment.getStatus(),
                payment.getAmount());
        }

        PaymentStatus simulatedStatus = shouldSimulateDecline() ? PaymentStatus.DECLINED : PaymentStatus.APPROVED;
        return "Payment details for ID: " + paymentId.getValue() + ", Status: " + simulatedStatus;
    }

    private void simulateNetworkDelay() {
        if (!simulateDelay) return;

        try {
            int delayMs = minDelayMs + random.nextInt(maxDelayMs - minDelayMs);
            logger.debug("Simulating network delay of {}ms", delayMs);
            TimeUnit.MILLISECONDS.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Network delay simulation interrupted", e);
        }
    }

    private boolean shouldSimulateFailure() {
        return random.nextDouble() < failureRate;
    }

    private boolean shouldSimulateTimeout() {
        return random.nextDouble() < timeoutRate;
    }

    private boolean shouldSimulateDecline() {
        return random.nextDouble() < declineRate;
    }
}
