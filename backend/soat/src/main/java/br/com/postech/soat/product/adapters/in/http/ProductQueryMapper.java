package br.com.postech.soat.product.adapters.in.http;

import br.com.postech.soat.openapi.model.ProductCategoryDto;
import br.com.postech.soat.openapi.model.ProductDto;
import br.com.postech.soat.product.core.application.services.query.model.ProductQuery;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.domain.model.ProductId;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
    imports = {ProductCategoryDto.class, ProductId.class}
)
public interface ProductQueryMapper {
    ProductQueryMapper INSTANCE = Mappers.getMapper(ProductQueryMapper.class);

    @Mapping(target = "productId", expression = "java(ProductId.of(uuid))")
    ProductQuery toQuery(UUID uuid);

    @Mapping(target = "id", source = "product.id.value")
    @Mapping(target = "sku", source = "product.sku.value")
    @Mapping(target = "active", source = "product.active")
    @Mapping(target = "name", source = "product.name.value")
    @Mapping(target = "price", source = "product.price.value")
    @Mapping(target = "description", source = "product.description.value")
    @Mapping(target = "image", source = "product.image.value")
    @Mapping(target = "category", expression = "java(ProductCategoryDto.fromValue(product.getCategory().category().toString()))")
    ProductDto toResponse(Product product);
}