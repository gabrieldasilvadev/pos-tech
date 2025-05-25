package br.com.postech.soat.payment.core.application.services;

import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import br.com.postech.soat.payment.core.domain.model.PaymentStatus;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Process Payment Notification Service Tests")
class ProcessPaymentNotificationServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private FakeCheckoutClient fakeCheckoutClient;

    @InjectMocks
    private ProcessPaymentNotificationService service;

    @Test
    @DisplayName("Should process payment notification successfully")
    void shouldProcessPaymentNotificationSuccessfully() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        Payment payment = mock(Payment.class);

        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn("Payment details");
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        service.processPaymentNotification(paymentId);

        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(payment, times(1)).finish();
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    @DisplayName("Should throw exception when payment details are empty")
    void shouldThrowExceptionWhenPaymentDetailsAreEmpty() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn("");

        assertThrows(RuntimeException.class, () -> service.processPaymentNotification(paymentId));
        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, never()).findById(any());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when payment details are null")
    void shouldThrowExceptionWhenPaymentDetailsAreNull() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.processPaymentNotification(paymentId));
        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, never()).findById(any());
        verify(paymentRepository, never()).save(any());
    }
}
