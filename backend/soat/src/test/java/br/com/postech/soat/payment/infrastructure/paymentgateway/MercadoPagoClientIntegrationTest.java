package br.com.postech.soat.payment.infrastructure.paymentgateway;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.net.MPHttpClient;
import com.mercadopago.net.MPRequest;
import com.mercadopago.net.MPResponse;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MercadoPagoClientIntegrationTest {

    private static final String PAYMENT_RESPONSE = """
        {
          "id": 123456789,
          "status": "approved",
          "status_detail": "accredited",
          "payment_method_id": "pix",
          "point_of_interaction": {
            "transaction_data": {
              "ticket_url": "https://www.mercadopago.com/mlb/payments/ticket?foo=bar",
              "qr_code": "00020101021226820014br.gov.bcb.pix..."
            }
          }
        }
        """;

    private Payment payment;

    @BeforeEach
    void setUp() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        payment = Payment.initiate(orderId, customerId, PaymentMethod.PIX, new BigDecimal("10.00"));
    }

    @Test
    @DisplayName("Should parse ticket url from Mercado Pago response")
    void shouldParseTicketUrlFromResponse() {
        MercadoPagoConfig.setAccessToken("test-token");
        PaymentClient paymentClient = new PaymentClient(new FakeMPHttpClient(201, PAYMENT_RESPONSE));
        MercadoPagoClient client = new MercadoPagoClient(paymentClient);
        String ticketUrl = client.createPayment(payment);
        Assertions.assertEquals("https://www.mercadopago.com/mlb/payments/ticket?foo=bar", ticketUrl);
    }

    @Test
    @DisplayName("Should throw MPApiException on error response")
    void shouldThrowExceptionOnErrorResponse() {
        MercadoPagoConfig.setAccessToken("test-token");
        PaymentClient paymentClient = new PaymentClient(new FakeMPHttpClient(400, "{\"message\":\"error\"}"));
        MercadoPagoClient client = new MercadoPagoClient(paymentClient);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> client.createPayment(payment));
        Assertions.assertInstanceOf(MPApiException.class, thrown.getCause());
    }

    static class FakeMPHttpClient implements MPHttpClient {
        private final int status;
        private final String body;
        FakeMPHttpClient(int status, String body) {
            this.status = status;
            this.body = body;
        }
        @Override
        public MPResponse send(MPRequest request) throws MPApiException {
            MPResponse response = new MPResponse(status, Collections.emptyMap(), body);
            if (status >= 300) {
                throw new MPApiException("error", response);
            }
            return response;
        }
    }
}
