package br.com.postech.soat.payment.core.application.services.command;

import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.order.core.domain.model.OrderId;
import br.com.postech.soat.payment.core.application.services.command.model.InitiatePaymentCommand;
import br.com.postech.soat.payment.core.domain.model.Payment;
import br.com.postech.soat.payment.core.domain.model.PaymentId;
import br.com.postech.soat.payment.core.domain.model.PaymentMethod;
import br.com.postech.soat.payment.core.domain.model.PaymentStatus;
import br.com.postech.soat.payment.core.ports.out.GatewayOperationResult;
import br.com.postech.soat.payment.core.ports.out.PaymentGateway;
import br.com.postech.soat.payment.core.ports.out.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Initiate Payment Command Handler Tests")
class InitiatePaymentCommandHandlerTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private InitiatePaymentCommandHandler handler;

    @Captor
    private ArgumentCaptor<Payment> paymentCaptor;

    @Test
    @DisplayName("Should initiate payment successfully when gateway returns success")
    void shouldInitiatePaymentSuccessfullyWhenGatewayReturnsSuccess() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal amount = new BigDecimal("100.00");

        InitiatePaymentCommand command = new InitiatePaymentCommand(
            orderId,
            customerId,
            PaymentMethod.PIX,
            amount
        );

        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(GatewayOperationResult.SUCCESS);

        PaymentId result = handler.handle(command);

        assertNotNull(result);
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());

        Payment capturedPayment = paymentCaptor.getValue();
        assertEquals(orderId, capturedPayment.getOrderId());
        assertEquals(customerId, capturedPayment.getCustomerId());
        assertEquals(PaymentMethod.PIX, capturedPayment.getMethod());
        assertEquals(amount, capturedPayment.getAmount());
        assertEquals(PaymentStatus.APPROVED, capturedPayment.getStatus());
    }

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

        when(paymentGateway.processPayment(any(Payment.class))).thenReturn(GatewayOperationResult.FAILURE);

        PaymentId result = handler.handle(command);

        assertNotNull(result);
        verify(paymentRepository, times(1)).save(paymentCaptor.capture());

        Payment capturedPayment = paymentCaptor.getValue();
        assertEquals(orderId, capturedPayment.getOrderId());
        assertEquals(customerId, capturedPayment.getCustomerId());
        assertEquals(PaymentMethod.PIX, capturedPayment.getMethod());
        assertEquals(amount, capturedPayment.getAmount());
        assertEquals(PaymentStatus.FAILED, capturedPayment.getStatus());
    }
}
