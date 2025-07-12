package br.com.postech.soat.payment.domain.valueobject;

import br.com.postech.soat.commons.domain.Identifier;
import java.util.UUID;

public class PaymentId extends Identifier {

    public PaymentId(UUID value) {
        super(value);
    }

    public static PaymentId generate() {
        return new PaymentId(UUID.randomUUID());
    }

    public static PaymentId of(UUID value) {
        return new PaymentId(value);
    }

    public static PaymentId of(String value) {
        return new PaymentId(UUID.fromString(value));
    }
}
