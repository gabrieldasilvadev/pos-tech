package br.com.postech.soat.order.core.application;

import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.order.core.application.commands.CreateOrderCommand;
import br.com.postech.soat.order.core.domain.model.Order;
import br.com.postech.soat.order.core.ports.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, Order> {
    private final OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(CreateOrderCommandHandler.class);

    @Override
    public Order handle(CreateOrderCommand command) {
        try {
            final Order order = Order.receive(
                command.customerId(),
                command.orderItems(),
                command.discounts(),
                command.observations()
            );

            logger.info("Domain order created: {}", order);
            return orderRepository.sendOrder(order);
        } catch (Exception e) {
            logger.error("Error creating order: {}", e.getMessage(), e);
            throw e;
        }
    }
}
