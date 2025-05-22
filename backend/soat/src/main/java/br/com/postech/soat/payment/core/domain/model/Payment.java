package br.com.postech.soat.payment.core.domain.model;

import br.com.postech.soat.commons.domain.AggregateRoot;
import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.order.core.domain.model.OrderId;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
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

    @Builder
    protected Payment(UUID paymentId, OrderId orderId, CustomerId customerId,
                      BigDecimal amount, PaymentStatus status, PaymentMethod method,
                      Instant createdAt, Instant processedAt) {
        super(new PaymentId(paymentId));
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
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

    public void approve() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment cannot be approved from status " + this.status);
        }
        this.status = PaymentStatus.APPROVED;
        this.processedAt = Instant.now();
    }

    public void fail() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment cannot be failed from status " + this.status);
        }
        this.status = PaymentStatus.FAILED;
        this.processedAt = Instant.now();
    }

    public void finish() {
        if (this.status.equals(PaymentStatus.APPROVED)) {
            throw new IllegalStateException("Payment cannot be finished from status " + this.status);
        }
        this.status = PaymentStatus.FINISHED;
        this.processedAt = Instant.now();
    }
}
