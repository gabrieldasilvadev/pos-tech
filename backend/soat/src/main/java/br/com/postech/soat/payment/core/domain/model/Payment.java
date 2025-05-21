package br.com.postech.soat.payment.core.domain.model;

import br.com.postech.soat.commons.domain.AggregateRoot;
import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.order.core.domain.model.OrderId;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;

@Getter
public class Payment extends AggregateRoot<PaymentId> {
    private OrderId orderId;
    private CustomerId customerId;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private Instant createdAt;
    private Instant processedAt;

    protected Payment(PaymentId paymentId) {
        super(paymentId);
    }

    public static Payment initiate(OrderId orderId, CustomerId customerId, String paymentMethod, BigDecimal amount) {
        Payment payment = new Payment(PaymentId.generate());
        payment.orderId = orderId;
        payment.customerId = customerId;
        payment.amount = amount;
        payment.method = PaymentMethod.entryOf(paymentMethod);
        payment.status = PaymentStatus.PENDING;
        payment.createdAt = Instant.now();
        return payment;
    }
}
