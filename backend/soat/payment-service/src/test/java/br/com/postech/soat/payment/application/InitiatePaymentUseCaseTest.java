package br.com.postech.soat.payment.application;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import br.com.postech.soat.payment.application.command.InitiatePaymentCommand;
import br.com.postech.soat.payment.application.dto.PaymentInitiationResult;
import br.com.postech.soat.payment.application.usecases.InitiatePaymentUseCase;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.application.gateway.GatewayOperationResult;
import br.com.postech.soat.payment.application.gateway.PaymentGateway;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import java.math.BigDecimal;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Initiate Payment Command Handler Tests")
class InitiatePaymentUseCaseTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private InitiatePaymentUseCase handler;

    @Captor
    private ArgumentCaptor<Payment> paymentCaptor;

    @Test
    @DisplayName("Should mark payment as failed when gateway returns failure")
    void shouldMarkPaymentAsFailedWhenGatewayReturnsFailure() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal amount = new BigDecimal("100.00");

        InitiatePaymentCommand command = new InitiatePaymentCommand(
            orderId,
            customerId,
            PaymentMethod.PIX,
            amount
        );

        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(GatewayOperationResult.failure());

        PaymentInitiationResult result = handler.execute(command);

        assertNotNull(result);
        assertNotNull(result.paymentId());
        assertNull(result.paymentUrl());

        verify(paymentRepository, times(1)).save(paymentCaptor.capture());

        Payment capturedPayment = paymentCaptor.getValue();
        assertEquals(orderId, capturedPayment.getOrderId());
        assertEquals(customerId, capturedPayment.getCustomerId());
        assertEquals(PaymentMethod.PIX, capturedPayment.getMethod());
        assertEquals(amount, capturedPayment.getAmount());
        assertEquals(PaymentStatus.FAILED, capturedPayment.getStatus());
    }

    @Test
    @DisplayName("Should save payment as APPROVED when gateway returns success")
    void shouldSavePaymentAsApprovedWhenGatewayReturnsSuccess() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal amount = new BigDecimal("100.00");
        String expectedPaymentUrl = "https://www.mercadopago.com/mlb/payments/ticket?foo=bar";

        InitiatePaymentCommand command = new InitiatePaymentCommand(
            orderId,
            customerId,
            PaymentMethod.PIX,
            amount
        );

        when(paymentGateway.processPayment(any(Payment.class)))
            .thenReturn(GatewayOperationResult.success(expectedPaymentUrl));

        PaymentInitiationResult result = handler.execute(command);

        assertNotNull(result);
        assertNotNull(result.paymentId());
        assertEquals(expectedPaymentUrl, result.paymentUrl());

        verify(paymentRepository, times(1)).save(paymentCaptor.capture());

        Payment capturedPayment = paymentCaptor.getValue();
        assertEquals(orderId, capturedPayment.getOrderId());
        assertEquals(customerId, capturedPayment.getCustomerId());
        assertEquals(PaymentMethod.PIX, capturedPayment.getMethod());
        assertEquals(amount, capturedPayment.getAmount());
        assertEquals(PaymentStatus.APPROVED, capturedPayment.getStatus());
    }

    @Test
    @DisplayName("Should return payment ID and URL when payment is successful")
    void shouldReturnPaymentIdAndUrlWhenPaymentIsSuccessful() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal amount = new BigDecimal("250.50");
        String expectedPaymentUrl = "https://www.mercadopago.com/mlb/payments/ticket?test=123";

        InitiatePaymentCommand command = new InitiatePaymentCommand(
            orderId,
            customerId,
            PaymentMethod.PIX,
            amount
        );

        when(paymentGateway.processPayment(any(Payment.class)))
            .thenReturn(GatewayOperationResult.success(expectedPaymentUrl));

        PaymentInitiationResult result = handler.execute(command);

        assertNotNull(result);
        assertNotNull(result.paymentId());
        assertEquals(expectedPaymentUrl, result.paymentUrl());
    }

    @Test
    @DisplayName("Should return payment ID with null URL when payment fails")
    void shouldReturnPaymentIdWithNullUrlWhenPaymentFails() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal amount = new BigDecimal("99.99");

        InitiatePaymentCommand command = new InitiatePaymentCommand(
            orderId,
            customerId,
            PaymentMethod.PIX,
            amount
        );

        when(paymentGateway.processPayment(any(Payment.class)))
            .thenReturn(GatewayOperationResult.failure());

        PaymentInitiationResult result = handler.execute(command);

        assertNotNull(result);
        assertNotNull(result.paymentId());
        assertNull(result.paymentUrl());
    }
}
