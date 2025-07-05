package br.com.postech.soat.order.core.application.commands;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.core.domain.model.Discount;
import br.com.postech.soat.order.core.domain.model.Observation;
import br.com.postech.soat.order.core.domain.model.OrderItem;
import java.util.List;

public record CreateOrderCommand(
    CustomerId customerId,
    List<Discount> discounts,
    List<OrderItem> orderItems,
    List<Observation> observations
) implements Command {
}
