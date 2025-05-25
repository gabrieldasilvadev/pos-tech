package br.com.postech.soat.product.core.application.services.command.model;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.product.core.domain.Category;
import java.math.BigDecimal;

public record CreateProductCommand(
    String sku,
    String name,
    BigDecimal price,
    String description,
    String image,
    Category category
) implements Command {
}