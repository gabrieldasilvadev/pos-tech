package br.com.postech.soat.payment.core.application.commands;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.order.core.domain.model.OrderId;
import java.math.BigDecimal;

public record InitiatePaymentCommand(
    OrderId orderId,
    CustomerId customerId,
    String paymentMethod,
    BigDecimal amount
) implements Command {
}
