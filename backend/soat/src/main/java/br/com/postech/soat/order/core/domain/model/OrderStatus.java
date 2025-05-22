package br.com.postech.soat.order.core.domain.model;

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
}
