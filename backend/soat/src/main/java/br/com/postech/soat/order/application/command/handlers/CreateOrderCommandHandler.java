package br.com.postech.soat.order.application.command.handlers;

import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.order.application.command.CreateOrderCommand;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.application.repositories.OrderRepository;
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
            order.prepare();
            logger.info("Domain order created: {}", order);
            return orderRepository.save(order);
        } catch (Exception e) {
            logger.error("Error creating order: {}", e.getMessage(), e);
            throw e;
        }
    }
}
