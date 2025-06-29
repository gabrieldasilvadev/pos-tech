package br.com.postech.soat.product.adapters.in.http;

import br.com.postech.soat.openapi.model.GetProduct200ResponseInnerDto;
import br.com.postech.soat.openapi.model.PostProducts201ResponseDto;
import br.com.postech.soat.product.core.application.dto.CreateProductRequest;
import br.com.postech.soat.product.core.application.dto.UpdateProductRequest;
import br.com.postech.soat.openapi.model.PostProductsRequestDto;
import br.com.postech.soat.openapi.model.PutProductsRequestDto;
import java.util.List;
import java.util.UUID;
import br.com.postech.soat.product.core.domain.model.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {
    ProductWebMapper INSTANCE = Mappers.getMapper(ProductWebMapper.class);

    CreateProductRequest toCreateRequest(PostProductsRequestDto postProductsRequestDto);

    UpdateProductRequest toUpdateRequest(PutProductsRequestDto putProductRequest);

    @Mapping(target = "id", source = "product.id.value")
    PostProducts201ResponseDto toCreateResponse(Product product);

    @Mapping(target = "id", expression = "java(ProductId.of(product.getId().getValue()).getValue())")
    @Mapping(target = "sku", source = "product.sku.value")
    @Mapping(target = "name", source = "product.name.value")
    @Mapping(target = "description", source = "product.description.value")
    @Mapping(target = "price", source = "product.price.value")
    @Mapping(target = "active", source = "product.active")
    @Mapping(target = "image", source = "product.image.value")
    @Mapping(target = "category", source = "product.category.value")
    GetProduct200ResponseInnerDto toResponse(Product product);

    List<GetProduct200ResponseInnerDto> toListResponse(List<Product> products);

    UUID toDelete(UUID uuid);
}

