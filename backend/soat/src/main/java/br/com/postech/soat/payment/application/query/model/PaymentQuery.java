package br.com.postech.soat.payment.application.query.model;

import br.com.postech.soat.commons.application.query.Query;
import br.com.postech.soat.payment.domain.entity.PaymentId;

public record PaymentQuery(
    PaymentId paymentId
) implements Query {
}
