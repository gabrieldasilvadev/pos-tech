package br.com.postech.soat.payment.domain.entity;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {

    @Test
    @DisplayName("Should throw exception when initiating payment with negative amount")
    void shouldThrowExceptionWhenInitiatingPaymentWithNegativeAmount() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal negativeAmount = new BigDecimal("-100.00");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            Payment.initiate(orderId, customerId, PaymentMethod.PIX, negativeAmount)
        );
        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when initiating payment with null orderId")
    void shouldThrowExceptionWhenInitiatingPaymentWithNullOrderId() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal amount = new BigDecimal("100.00");

        NullPointerException exception = assertThrows(NullPointerException.class, () ->
            Payment.initiate(null, customerId, PaymentMethod.PIX, amount)
        );
        assertEquals("OrderId cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when initiating payment with null customerId")
    void shouldThrowExceptionWhenInitiatingPaymentWithNullCustomerId() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        BigDecimal amount = new BigDecimal("100.00");

        NullPointerException exception = assertThrows(NullPointerException.class, () ->
            Payment.initiate(orderId, null, PaymentMethod.PIX, amount)
        );
        assertEquals("CustomerId cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when initiating payment with null payment method")
    void shouldThrowExceptionWhenInitiatingPaymentWithNullPaymentMethod() {
        OrderId orderId = new OrderId(UUID.randomUUID());
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        BigDecimal amount = new BigDecimal("100.00");

        NullPointerException exception = assertThrows(NullPointerException.class, () ->
            Payment.initiate(orderId, customerId, null, amount)
        );
        assertEquals("Payment method cannot be null", exception.getMessage());
    }
}
