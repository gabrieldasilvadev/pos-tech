package br.com.postech.soat.order.domain.entity;

import br.com.postech.soat.commons.domain.Identifier;
import java.util.UUID;

public class OrderId extends Identifier {

    public OrderId(UUID value) {
        super(value);
    }
}
