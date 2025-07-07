package br.com.postech.soat.order.application.command;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.order.domain.vo.Discount;
import br.com.postech.soat.order.domain.vo.Observation;
import br.com.postech.soat.order.domain.entity.OrderItem;
import java.util.List;

public record CreateOrderCommand(
    CustomerId customerId,
    List<Discount> discounts,
    List<OrderItem> orderItems,
    List<Observation> observations
) implements Command {
}
