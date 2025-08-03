package br.com.postech.soat.payment.infrastructure.http;

import br.com.postech.soat.payment.application.usecases.ProcessPaymentNotificationUseCase;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("MercadoPago Webhook Controller Tests")
class MercadoPagoWebhookControllerTest {

    @Mock
    private ProcessPaymentNotificationUseCase notificationService;

    @InjectMocks
    private MercadoPagoWebhookController controller;

    @Test
    @DisplayName("Should process payment notification when topic is payment")
    void shouldProcessPaymentNotificationWhenTopicIsPayment() {
        String paymentId = UUID.randomUUID().toString();
        String topic = "payment";

        ResponseEntity<Void> response = controller.receive(paymentId, topic);

        verify(notificationService, times(1)).execute(PaymentId.of(paymentId));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should not process payment notification when topic is not payment")
    void shouldNotProcessPaymentNotificationWhenTopicIsNotPayment() {
        String paymentId = UUID.randomUUID().toString();
        String topic = "other_topic";

        ResponseEntity<Void> response = controller.receive(paymentId, topic);

        verify(notificationService, never()).execute(any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}