package br.com.postech.soat.order.core.domain.model;

import br.com.postech.soat.commons.domain.AggregateRoot;
import br.com.postech.soat.customer.core.domain.model.CustomerId;
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

        order.calculatePrice();
        order.calculateDiscount();
        order.calculateTotalPriceWithDiscount();
        return order;
    }

    public void prepare() {
        this.status = OrderStatus.IN_PREPARATION;
    }

    public BigDecimal calculateDiscount() {
        BigDecimal discount = discounts.stream()
            .map(Discount::value)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.discountAmount = discount;
        return discount;
    }

    public BigDecimal calculatePrice() {
        BigDecimal originalPrice = orderItems.stream()
            .map(OrderItem::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.originalPrice = originalPrice;
        return originalPrice;
    }

    public BigDecimal calculateTotalPriceWithDiscount() {
        BigDecimal totalWithDiscount = calculatePrice().subtract(calculateDiscount());
        this.totalPrice = totalWithDiscount;
        return totalWithDiscount;
    }
}
