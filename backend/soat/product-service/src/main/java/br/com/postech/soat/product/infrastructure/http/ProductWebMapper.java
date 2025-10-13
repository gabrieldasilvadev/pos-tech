package br.com.postech.soat.product.infrastructure.http;

import br.com.postech.soat.openapi.model.GetProduct200ResponseInnerDto;
import br.com.postech.soat.openapi.model.PostProducts201ResponseDto;
import br.com.postech.soat.openapi.model.ProductDto;
import br.com.postech.soat.product.application.dto.CreateProductDto;
import br.com.postech.soat.product.application.dto.UpdateProductDto;
import br.com.postech.soat.openapi.model.PostProductsRequestDto;
import br.com.postech.soat.openapi.model.PutProductsRequestDto;
import java.util.List;
import br.com.postech.soat.product.domain.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {
    CreateProductDto toCreateRequest(PostProductsRequestDto postProductsRequestDto);

    UpdateProductDto toUpdateRequest(PutProductsRequestDto putProductRequest);

    @Mapping(target = "id", source = "product.id.value")
    PostProducts201ResponseDto toCreateResponse(Product product);

    @Mapping(target = "id", expression = "java(ProductId.of(product.getId().getValue()).getValue())")
    @Mapping(target = "sku", source = "product.sku.value")
    @Mapping(target = "active", source = "product.active")
    @Mapping(target = "name", source = "product.name.value")
    @Mapping(target = "price", source = "product.price.value")
    @Mapping(target = "description", source = "product.description.value")
    @Mapping(target = "image", source = "product.image.value")
    @Mapping(target = "category", source = "product.category.value")
    GetProduct200ResponseInnerDto toResponse(Product product);

    @Mapping(target = "id", expression = "java(ProductId.of(product.getId().getValue()).getValue())")
    @Mapping(target = "sku", source = "product.sku.value")
    @Mapping(target = "name", source = "product.name.value")
    @Mapping(target = "description", source = "product.description.value")
    @Mapping(target = "price", source = "product.price.value")
    @Mapping(target = "active", source = "product.active")
    @Mapping(target = "image", source = "product.image.value")
    @Mapping(target = "category", source = "product.category.value")
    ProductDto toUpdateResponse(Product product);

    List<GetProduct200ResponseInnerDto> toListResponse(List<Product> products);
}

