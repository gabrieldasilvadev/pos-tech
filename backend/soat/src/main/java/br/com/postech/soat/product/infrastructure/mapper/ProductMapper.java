package br.com.postech.soat.product.infrastructure.mapper;

import br.com.postech.soat.product.infrastructure.persistence.entities.ProductEntity;
import br.com.postech.soat.product.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    default Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return Product.builder()
            .productId(entity.getId())
            .sku(entity.getSku())
            .active(entity.getActive())
            .name(entity.getName())
            .price(entity.getPrice())
            .description(entity.getDescription())
            .image(entity.getImage())
            .category(entity.getCategory())
            .build();
    }

    default ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId().getValue());
        entity.setSku(domain.getSku().value());
        entity.setActive(domain.getActive());
        entity.setName(domain.getName().value());
        entity.setPrice(domain.getPrice().value());
        entity.setDescription(domain.getDescription().value());
        entity.setImage(domain.getImage().value());
        entity.setCategory(domain.getCategory().category());
        return entity;
    }
}
