package br.com.postech.soat.product.infrastructure.persistence.specification;

import br.com.postech.soat.product.infrastructure.persistence.entities.ProductEntity;
import br.com.postech.soat.product.application.dto.FindProductQuery;
import br.com.postech.soat.product.domain.enumtypes.Category;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;

public class ProductSpecification {

    public static Specification<ProductEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    public static Specification<ProductEntity> hasCategory(Optional<Category> category) {
        return (root, query, cb) ->
            category.isPresent()
                ? cb.equal(root.get("category"), category.get())
                : cb.conjunction();
    }

    public static Specification<ProductEntity> hasSku(Optional<String> sku) {
        return (root, query, cb) ->
            sku.isPresent() ? cb.equal(root.get("sku"), sku.get()) : cb.conjunction();
    }

    public static Specification<ProductEntity> fromDomain(FindProductQuery product) {
        return (root, query, cb) -> {
            if (product == null) {
                return cb.conjunction();
            }
            return cb.and(
                isActive().toPredicate(root, query, cb),
                hasCategory(product.category()).toPredicate(root, query, cb),
                hasSku(product.SKU()).toPredicate(root, query, cb)
            );
        };
    }

}