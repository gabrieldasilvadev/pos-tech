package br.com.postech.soat.order.infrastructure.http;

import br.com.postech.soat.openapi.api.OrderApi;
import br.com.postech.soat.openapi.model.PostOrders201ResponseDto;
import br.com.postech.soat.openapi.model.PostOrdersRequestDto;
import br.com.postech.soat.order.application.command.CreateOrderCommand;
import br.com.postech.soat.order.application.usecases.CreateOrderUseCase;
import br.com.postech.soat.order.domain.entity.Order;
import br.com.postech.soat.order.infrastructure.http.mapper.CreateOrderCommandMapper;
import br.com.postech.soat.order.infrastructure.http.mapper.OrderResponseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final CreateOrderUseCase createOrderUseCase;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Override
    public ResponseEntity<PostOrders201ResponseDto> postOrders(PostOrdersRequestDto postOrdersRequest) {
        logger.info("Initiating order creation process: {}", postOrdersRequest);
        final CreateOrderCommand command = CreateOrderCommandMapper.INSTANCE.mapFrom(postOrdersRequest);
        final Order orderCrated = createOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponseMapper.INSTANCE.toResponse(orderCrated));
    }
}
