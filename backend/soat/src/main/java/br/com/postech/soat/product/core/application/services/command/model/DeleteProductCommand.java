package br.com.postech.soat.product.core.application.services.command.model;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.product.core.domain.model.ProductId;

public record DeleteProductCommand(
    ProductId productId
) implements Command {
}