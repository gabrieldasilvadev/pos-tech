package br.com.postech.soat.payment.application;

import br.com.postech.soat.payment.application.usecases.ProcessPaymentNotificationUseCase;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.infrastructure.paymentgateway.FakeCheckoutClient;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Process Payment Notification Service Tests")
class ProcessPaymentNotificationUseCaseTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private FakeCheckoutClient fakeCheckoutClient;

    @InjectMocks
    private ProcessPaymentNotificationUseCase service;

    @Test
    @DisplayName("Should process approved payment notification successfully")
    void shouldHandleApprovedPaymentSuccessfully() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        Payment payment = mock(Payment.class);

        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn("Payment details for ID: uuid, Status: APPROVED, Amount: 100.0");
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        service.execute(paymentId);

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

        assertThrows(RuntimeException.class, () -> service.execute(paymentId));
        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, never()).findById(any());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when payment details are null")
    void shouldThrowExceptionWhenPaymentDetailsAreNull() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.execute(paymentId));
        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, never()).findById(any());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should process declined payment notification successfully")
    void shouldHandleDeclinedPaymentSuccessfully() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        Payment payment = mock(Payment.class);

        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn("Payment details for ID: uuid, Status: DECLINED, Amount: 100.0");
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        service.execute(paymentId);

        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(payment, times(1)).decline();
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    @DisplayName("Should process failed payment notification successfully")
    void shouldHandleFailedPaymentSuccessfully() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        Payment payment = mock(Payment.class);

        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn("Payment details for ID: uuid, Status: FAILED, Amount: 100.0");
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        service.execute(paymentId);

        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(payment, times(1)).fail();
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    @DisplayName("Should default to approved for unknown payment status")
    void shouldDefaultToApprovedForUnknownPaymentStatus() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        Payment payment = mock(Payment.class);

        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn("Payment details for ID: uuid, Status: UNKNOWN, Amount: 100.0");
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        service.execute(paymentId);

        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(payment, times(1)).finish(); // Should default to approved/finish for unknown statuses
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    @DisplayName("Should default to approved when status cannot be parsed")
    void shouldDefaultToApprovedWhenStatusCannotBeParsed() {
        PaymentId paymentId = PaymentId.of(UUID.randomUUID());
        Payment payment = mock(Payment.class);

        when(fakeCheckoutClient.getPaymentDetails(paymentId)).thenReturn("Invalid payment response without status");
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        service.execute(paymentId);

        verify(fakeCheckoutClient, times(1)).getPaymentDetails(paymentId);
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(payment, times(1)).finish(); // Should default to approved/finish
        verify(paymentRepository, times(1)).save(payment);
    }
}
