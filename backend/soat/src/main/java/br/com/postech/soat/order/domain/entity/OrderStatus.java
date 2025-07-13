package br.com.postech.soat.order.domain.entity;

import java.util.List;

public enum OrderStatus {
    RECEIVED,
    AWAITING_PAYMENT,
    PAID,
    IN_PREPARATION,
    DONE,
    DELIVERED;

    public static OrderStatus entryOf(String status) {
        return OrderStatus.valueOf(status.toUpperCase());
    }

    public static List<OrderStatus> activeOrderStatusList() {
        return List.of(
            OrderStatus.DONE,
            OrderStatus.IN_PREPARATION,
            OrderStatus.RECEIVED
        );
    }
}
