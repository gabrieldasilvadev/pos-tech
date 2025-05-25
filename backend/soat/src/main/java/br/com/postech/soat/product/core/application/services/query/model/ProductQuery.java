package br.com.postech.soat.product.core.application.services.query.model;

import br.com.postech.soat.commons.application.query.Query;
import br.com.postech.soat.product.core.domain.model.ProductId;

public record ProductQuery(
    ProductId productId
) implements Query {
}