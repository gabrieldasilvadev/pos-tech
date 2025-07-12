package br.com.postech.soat.payment.application.command;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.core.domain.model.OrderId;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import java.math.BigDecimal;

public record InitiatePaymentCommand(
    OrderId orderId,
    CustomerId customerId,
    PaymentMethod paymentMethod,
    BigDecimal amount
) implements Command {
}
