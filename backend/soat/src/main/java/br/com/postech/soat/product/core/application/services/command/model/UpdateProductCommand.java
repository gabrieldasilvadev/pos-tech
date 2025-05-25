package br.com.postech.soat.product.core.application.services.command.model;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.model.ProductId;
import java.math.BigDecimal;

public record UpdateProductCommand(
    ProductId productId,
    String name,
    BigDecimal price,
    String description,
    String image,
    Category category
) implements Command {
}