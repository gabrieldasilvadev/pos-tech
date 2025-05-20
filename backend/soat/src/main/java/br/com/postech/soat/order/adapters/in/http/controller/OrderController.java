package br.com.postech.soat.order.adapters.in.http.controller;

import br.com.postech.soat.commons.application.mediator.Mediator;
import br.com.postech.soat.openapi.api.OrderApi;
import br.com.postech.soat.openapi.model.CategoryDto;
import br.com.postech.soat.openapi.model.DiscountDto;
import br.com.postech.soat.openapi.model.OrderItemDto;
import br.com.postech.soat.openapi.model.OrderStatusDto;
import br.com.postech.soat.openapi.model.PostOrders201ResponseDiscountsInnerDto;
import br.com.postech.soat.openapi.model.PostOrders201ResponseDto;
import br.com.postech.soat.openapi.model.PostOrdersRequestDto;
import br.com.postech.soat.order.adapters.in.http.controller.mapper.CreateOrderCommandMapper;
import br.com.postech.soat.order.adapters.in.http.controller.mapper.OrderResponseMapper;
import br.com.postech.soat.order.core.application.commands.CreateOrderCommand;
import br.com.postech.soat.order.core.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrderApi {
    private final Mediator mediator;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public ResponseEntity<PostOrders201ResponseDto> postOrders(PostOrdersRequestDto postOrdersRequest) {
        logger.info("Initiating order creation process: {}", postOrdersRequest);
        final CreateOrderCommand command = CreateOrderCommandMapper.INSTANCE.mapFrom(postOrdersRequest);
        final Order orderCrated = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponseMapper.INSTANCE.toResponse(orderCrated));
    }
}
