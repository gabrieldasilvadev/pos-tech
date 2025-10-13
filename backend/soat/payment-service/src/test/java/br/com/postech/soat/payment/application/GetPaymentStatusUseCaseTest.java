package br.com.postech.soat.payment.application;

import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.application.usecases.GetPaymentStatusUseCase;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Get Payment Status Use Case Tests")
class GetPaymentStatusUseCaseTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private GetPaymentStatusUseCase useCase;

    @Test
    @DisplayName("Should return PENDING status when payment is pending")
    void shouldReturnPendingStatusWhenPaymentIsPending() {
        // Arrange
        PaymentId paymentId = PaymentId.of("123e4567-e89b-12d3-a456-426614174000");
        Payment payment = createPayment(paymentId, PaymentStatus.PENDING);
        
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        // Act
        PaymentStatus result = useCase.execute(paymentId);

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.PENDING, result);
        verify(paymentRepository).findById(paymentId);
    }

    @Test
    @DisplayName("Should return APPROVED status when payment is approved")
    void shouldReturnApprovedStatusWhenPaymentIsApproved() {
        // Arrange
        PaymentId paymentId = PaymentId.of("123e4567-e89b-12d3-a456-426614174000");
        Payment payment = createPayment(paymentId, PaymentStatus.APPROVED);
        
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        // Act
        PaymentStatus result = useCase.execute(paymentId);

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.APPROVED, result);
        verify(paymentRepository).findById(paymentId);
    }

    @Test
    @DisplayName("Should return FINISHED status when payment is finished")
    void shouldReturnFinishedStatusWhenPaymentIsFinished() {
        // Arrange
        PaymentId paymentId = PaymentId.of("123e4567-e89b-12d3-a456-426614174000");
        Payment payment = createPayment(paymentId, PaymentStatus.FINISHED);
        
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        // Act
        PaymentStatus result = useCase.execute(paymentId);

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.FINISHED, result);
        verify(paymentRepository).findById(paymentId);
    }

    @Test
    @DisplayName("Should return FAILED status when payment failed")
    void shouldReturnFailedStatusWhenPaymentFailed() {
        // Arrange
        PaymentId paymentId = PaymentId.of("123e4567-e89b-12d3-a456-426614174000");
        Payment payment = createPayment(paymentId, PaymentStatus.FAILED);
        
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        // Act
        PaymentStatus result = useCase.execute(paymentId);

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.FAILED, result);
        verify(paymentRepository).findById(paymentId);
    }

    @Test
    @DisplayName("Should return DECLINED status when payment is declined")
    void shouldReturnDeclinedStatusWhenPaymentIsDeclined() {
        // Arrange
        PaymentId paymentId = PaymentId.of("123e4567-e89b-12d3-a456-426614174000");
        Payment payment = createPayment(paymentId, PaymentStatus.DECLINED);
        
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        // Act
        PaymentStatus result = useCase.execute(paymentId);

        // Assert
        assertNotNull(result);
        assertEquals(PaymentStatus.DECLINED, result);
        verify(paymentRepository).findById(paymentId);
    }

    private Payment createPayment(PaymentId paymentId, PaymentStatus status) {
        return Payment.builder()
                .paymentId(paymentId.getValue())
                .orderId(new OrderId(UUID.fromString("456e7890-e89b-12d3-a456-426614174001")))
                .customerId(new CustomerId(UUID.fromString("789e0123-e89b-12d3-a456-426614174002")))
                .amount(BigDecimal.valueOf(100.50))
                .status(status)
                .method(PaymentMethod.PIX)
                .createdAt(Instant.now())
                .processedAt(status == PaymentStatus.PENDING ? null : Instant.now())
                .build();
    }
}
