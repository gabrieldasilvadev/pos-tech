package br.com.postech.soat.order.domain.entity;

import br.com.postech.soat.commons.domain.Identifier;
import java.util.UUID;

public class OrderItemId extends Identifier {

    public OrderItemId(UUID value) {
        super(value);
    }
}
