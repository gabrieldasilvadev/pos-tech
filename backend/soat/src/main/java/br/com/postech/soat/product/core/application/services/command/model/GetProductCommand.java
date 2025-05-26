package br.com.postech.soat.product.core.application.services.command.model;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.model.Product;

public record GetProductCommand(
    Product product
) implements Command {
}