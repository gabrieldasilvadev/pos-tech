package br.com.postech.soat.payment.core.application.services.query.model;

import br.com.postech.soat.commons.application.query.Query;
import br.com.postech.soat.payment.core.domain.model.PaymentId;

public record PaymentQuery(
    PaymentId paymentId
) implements Query {
}
