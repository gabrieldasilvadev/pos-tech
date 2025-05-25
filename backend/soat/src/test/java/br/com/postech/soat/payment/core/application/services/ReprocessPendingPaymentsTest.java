package br.com.postech.soat.payment.core.application.services;

import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.order.core.domain.model.OrderId;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentMethod;
import br.com.postech.soat.payment.core.domain.model.PaymentStatus;
import br.com.postech.soat.payment.core.ports.out.GatewayOperationResult;
import br.com.postech.soat.payment.core.ports.out.PaymentGateway;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Reprocess Pending Payments Tests")
class ReprocessPendingPaymentsTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private ReprocessPendingPayments reprocessPendingPayments;

    @Captor
    private ArgumentCaptor<Payment> paymentCaptor;

    @Test
    @DisplayName("Should reprocess pending payments successfully when gateway returns success")
    void shouldReprocessPendingPaymentsSuccessfullyWhenGatewayReturnsSuccess() {
        Payment pendingPayment = Payment.builder()
            .paymentId(UUID.randomUUID())
            .orderId(new OrderId(UUID.randomUUID()))
            .customerId(new CustomerId(UUID.randomUUID()))
            .amount(new BigDecimal("100.00"))
            .status(PaymentStatus.PENDING)
            .method(PaymentMethod.PIX)
            .createdAt(Instant.now())
            .build();

        when(paymentRepository.findPendingPayments()).thenReturn(List.of(pendingPayment));
        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(GatewayOperationResult.SUCCESS);

        reprocessPendingPayments.reprocess();

        verify(paymentRepository, times(1)).findPendingPayments();
        verify(paymentGateway, times(1)).processPayment(any(Payment.class));
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());

        Payment capturedPayment = paymentCaptor.getValue();
        assertEquals(PaymentStatus.APPROVED, capturedPayment.getStatus());
    }

    @Test
    @DisplayName("Should mark payment as failed when gateway returns failure")
    void shouldMarkPaymentAsFailedWhenGatewayReturnsFailure() {
        Payment pendingPayment = Payment.builder()
            .paymentId(UUID.randomUUID())
            .orderId(new OrderId(UUID.randomUUID()))
            .customerId(new CustomerId(UUID.randomUUID()))
            .amount(new BigDecimal("100.00"))
            .status(PaymentStatus.PENDING)
            .method(PaymentMethod.PIX)
            .createdAt(Instant.now())
            .build();

        when(paymentRepository.findPendingPayments()).thenReturn(List.of(pendingPayment));
        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(GatewayOperationResult.FAILURE);

        reprocessPendingPayments.reprocess();

        verify(paymentRepository, times(1)).findPendingPayments();
        verify(paymentGateway, times(1)).processPayment(any(Payment.class));
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());

        Payment capturedPayment = paymentCaptor.getValue();

        assertEquals(PaymentStatus.FAILED, capturedPayment.getStatus());
    }

    @Test
    @DisplayName("Should do nothing when no pending payments are found")
    void shouldDoNothingWhenNoPendingPaymentsAreFound() {
        when(paymentRepository.findPendingPayments()).thenReturn(Collections.emptyList());

        reprocessPendingPayments.reprocess();

        verify(paymentRepository, times(1)).findPendingPayments();
        verify(paymentGateway, never()).processPayment(any(Payment.class));
        verify(paymentRepository, never()).save(any(Payment.class));
    }
}