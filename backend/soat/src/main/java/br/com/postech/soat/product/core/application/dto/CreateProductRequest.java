package br.com.postech.soat.product.core.application.dto;

import br.com.postech.soat.product.core.domain.Category;
import java.math.BigDecimal;

public record CreateProductRequest (
    String sku,
    String name,
    String description,
    BigDecimal price,
    String image,
    String category
    ){
}