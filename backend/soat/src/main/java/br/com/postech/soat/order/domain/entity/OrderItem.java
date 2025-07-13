package br.com.postech.soat.order.domain.entity;

import br.com.postech.soat.commons.domain.AggregateRoot;
import br.com.postech.soat.order.domain.valueobject.Discount;
import br.com.postech.soat.order.domain.valueobject.OrderItemId;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class OrderItem extends AggregateRoot<OrderItemId> {
    private UUID productId;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private String category;
    private Discount discount;

    protected OrderItem(OrderItemId orderItemId) {
        super(orderItemId);
    }

    public OrderItem(
        UUID productId,
        String name,
        Integer quantity,
        BigDecimal price,
        String category,
        Discount discount) {
        super(new OrderItemId(UUID.randomUUID()));
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.discount = discount;
    }
}
