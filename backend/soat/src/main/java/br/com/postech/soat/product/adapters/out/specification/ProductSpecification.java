package br.com.postech.soat.product.adapters.out.specification;

import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.core.domain.Category;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<ProductEntity> hasCategory(Category category) {
        return (root, query, cb) ->
            category == null ? cb.conjunction() : cb.equal(root.get("category"), category);
    }

    public static Specification<ProductEntity> hasSku(String sku) {
        return (root, query, cb) ->
            sku == null ? cb.conjunction() : cb.equal(root.get("sku"), sku);
    }

}
