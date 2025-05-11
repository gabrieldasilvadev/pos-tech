package br.com.postech.soat.product.adapters.out.persistence.entities.mappers;

import br.com.postech.soat.product.adapters.in.dto.UpdateProductRequest;
import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.core.domain.Product;

public class ProductMapper {
    public static ProductEntity toEntity (Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setSku(product.getSku());
        entity.setActive(product.getActive());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setDescription(product.getDescription());
        entity.setImage(product.getImage());
        entity.setCategory(product.getCategory());
        return entity;
    }

    public static Product toDomain (ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Product(
            entity.getId(),
            entity.getSku(),
            entity.getActive(),
            entity.getName(),
            entity.getPrice(),
            entity.getDescription(),
            entity.getImage(),
            entity.getCategory()
        );
    }
}
