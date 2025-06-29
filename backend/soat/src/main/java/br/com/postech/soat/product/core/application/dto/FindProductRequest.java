package br.com.postech.soat.product.core.application.dto;

import br.com.postech.soat.product.core.domain.Category;
import java.util.Optional;

public record FindProductRequest (
    Optional<String> SKU,
    Optional<Category> category
) {
    public static FindProductRequest from(String sku, String category) {
        Optional<String> optionalSku = Optional.ofNullable(sku);
        Optional<Category> optionalCategory = Optional.ofNullable(category)
            .map(Category::entryOf);
        return new FindProductRequest(optionalSku, optionalCategory);
    }
}
