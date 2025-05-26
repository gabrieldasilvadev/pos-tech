package br.com.postech.soat.product.core.application.services.command.model;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.product.core.domain.Category;

public record GetProductCommand(
    Category category,
    String sku
) implements Command {
}