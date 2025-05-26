package br.com.postech.soat.product.adapters.out.persistence.specification;

import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<ProductEntity> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    public static Specification<ProductEntity> hasCategory(Category category) {
        return (root, query, cb) ->
            category == null ? cb.conjunction() : cb.equal(root.get("category"), category);
    }

    public static Specification<ProductEntity> hasSku(String sku) {
        return (root, query, cb) ->
            sku == null ? cb.conjunction() : cb.equal(root.get("sku"), sku);
    }

    public static Specification<ProductEntity> fromDomain(Product product) {
        return (root, query, cb) -> {
            if (product == null) {
                return cb.conjunction();
            }
            return cb.and(
                isActive().toPredicate(root, query, cb),
                hasCategory(product.getCategory()).toPredicate(root, query, cb),
                hasSku(product.getSku()).toPredicate(root, query, cb)
            );
        };
    }

}