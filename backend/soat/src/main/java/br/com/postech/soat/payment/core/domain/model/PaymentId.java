package br.com.postech.soat.payment.core.domain.model;

import br.com.postech.soat.commons.domain.Identifier;
import java.util.UUID;

public class PaymentId extends Identifier {

    protected PaymentId(UUID value) {
        super(value);
    }

    public static PaymentId generate() {
        return new PaymentId(UUID.randomUUID());
    }
}
