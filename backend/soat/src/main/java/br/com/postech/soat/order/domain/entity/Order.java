package br.com.postech.soat.order.domain.entity;

import br.com.postech.soat.commons.domain.AggregateRoot;
import br.com.postech.soat.customer.core.domain.model.CustomerId;
import br.com.postech.soat.order.domain.valueobject.Discount;
import br.com.postech.soat.order.domain.valueobject.Observation;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Order extends AggregateRoot<OrderId> {
    private CustomerId customerId;
    private List<OrderItem> orderItems;
    private List<Discount> discounts;
    private OrderStatus status;
    private List<Observation> observations;
    private BigDecimal originalPrice;
    private BigDecimal totalPrice;
    private BigDecimal discountAmount;

    protected Order(OrderId orderId) {
        super(orderId);
    }

    public static Order receive(CustomerId customerId,
                                List<OrderItem> orderItems,
                                List<Discount> discounts,
                                List<Observation> observations) {
        Order order = new Order(new OrderId(UUID.randomUUID()));
        order.status = OrderStatus.RECEIVED;
        order.customerId = customerId;
        order.orderItems = orderItems;
        order.observations = observations;
        order.discounts = discounts;

        order.calculateOriginalPrice();
        order.calculateDiscount();
        order.calculateTotalPriceWithDiscount();
        return order;
    }

    public void prepare() {
        this.status = OrderStatus.IN_PREPARATION;
    }

    public void calculateDiscount() {
        this.discountAmount =
            discounts.stream()
                .map(Discount::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void calculateOriginalPrice() {

        this.originalPrice = orderItems.stream()
            .map(OrderItem::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void calculateTotalPriceWithDiscount() {
        this.totalPrice = this.originalPrice.subtract(this.discountAmount);
    }
}
