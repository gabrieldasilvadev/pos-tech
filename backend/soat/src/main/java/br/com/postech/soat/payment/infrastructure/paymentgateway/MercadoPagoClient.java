package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mercadoPagoClient & !test")
public class MercadoPagoClient implements CheckoutClient {
    private final Logger logger = LoggerFactory.getLogger(MercadoPagoClient.class);
    private final PaymentClient paymentClient;

    @Value("${mercadopago.payer-email:test@example.com}")
    private String payerEmail;

    public MercadoPagoClient() {
        this(new PaymentClient());
    }

    public MercadoPagoClient(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Override
    public String createPayment(Payment payment) {
        logger.info("Creating payment with ID: {}", payment.getId());
        final PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
            .transactionAmount(payment.getAmount())
            .description("Order " + payment.getOrderId().getValue())
            .paymentMethodId(payment.getMethod().name().toLowerCase())
            .externalReference(payment.getId().getValue().toString())
            .payer(PaymentPayerRequest.builder().email(payerEmail).build())
            .build();

        final com.mercadopago.resources.payment.Payment mercadoPagoPaymentResult;
        try {
            mercadoPagoPaymentResult = paymentClient.create(paymentRequest);
        } catch (MPException |MPApiException e) {
            logger.error("Payment creation failed for payment ID: {}", payment.getId(), e);
            throw new RuntimeException(e);
        }
        logger.info("Payment created successfully with ID: {}", mercadoPagoPaymentResult.getId());
            return mercadoPagoPaymentResult.getPointOfInteraction().getTransactionData().getTicketUrl();
    }

    @Override
    public String getPaymentDetails(PaymentId paymentId) {
        return "processed";
    }
}
