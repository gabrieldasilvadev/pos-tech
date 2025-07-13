package br.com.postech.soat.product.application.dto;

import br.com.postech.soat.product.domain.enumtypes.Category;
import java.util.Optional;

public record FindProductQuery(
    Optional<String> SKU,
    Optional<Category> category
) {
    public static FindProductQuery from(String sku, String category) {
        Optional<String> optionalSku = Optional.ofNullable(sku);
        Optional<Category> optionalCategory = Optional.ofNullable(category)
            .map(Category::entryOf);
        return new FindProductQuery(optionalSku, optionalCategory);
    }
}
