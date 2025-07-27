package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.payment.domain.entity.Payment;
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
@Profile("mercadoPagoClient")
public class MercadoPagoClient implements CheckoutClient {
    private final Logger logger = LoggerFactory.getLogger(MercadoPagoClient.class);

    @Value("${mercadopago.payer-email:test@example.com}")
    private String payerEmail;

    @Override
    public String createPayment(Payment payment) throws MPException, MPApiException {
        logger.info("Creating payment with ID: {}", payment.getId());
        PaymentClient paymentClient = new PaymentClient();
        final PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
            .transactionAmount(payment.getAmount())
            .description("Order " + payment.getOrderId().getValue())
            .paymentMethodId(payment.getMethod().name().toLowerCase())
            .externalReference(payment.getId().getValue().toString())
            .payer(PaymentPayerRequest.builder().email(payerEmail).build())
            .build();

            final com.mercadopago.resources.payment.Payment mercadoPagoPaymentResult = paymentClient.create(paymentRequest);
            logger.info("Payment created successfully with ID: {}", mercadoPagoPaymentResult.getId());
            return mercadoPagoPaymentResult.getPointOfInteraction().getTransactionData().getTicketUrl();
    }
}
