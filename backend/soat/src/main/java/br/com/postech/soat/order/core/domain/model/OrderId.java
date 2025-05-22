package br.com.postech.soat.order.core.domain.model;

import br.com.postech.soat.commons.domain.Identifier;
import java.util.UUID;

public class OrderId extends Identifier {

    public OrderId(UUID value) {
        super(value);
    }
}
