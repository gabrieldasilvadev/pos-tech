package br.com.postech.soat.product.adapters.in.mapper;

import br.com.postech.soat.openapi.model.PostProductsRequestDto;
import br.com.postech.soat.openapi.model.PutProductsRequestDto;
import br.com.postech.soat.product.core.application.services.command.model.CreateProductCommand;
import br.com.postech.soat.product.core.application.services.command.model.DeleteProductCommand;
import br.com.postech.soat.product.core.application.services.command.model.UpdateProductCommand;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCommandMapper {
    ProductCommandMapper INSTANCE = Mappers.getMapper(ProductCommandMapper.class);

    @Mapping(target = "sku", source = "postProductsRequestDto.sku")
    @Mapping(target = "name", source = "postProductsRequestDto.name")
    @Mapping(target = "price", expression = "java(java.math.BigDecimal.valueOf(postProductsRequestDto.getPrice()))")
    @Mapping(target = "description", source = "postProductsRequestDto.description")
    @Mapping(target = "image", source = "postProductsRequestDto.image")
    @Mapping(target = "category", source = "postProductsRequestDto.category.value")
    CreateProductCommand toCommand(PostProductsRequestDto postProductsRequestDto);

    @Mapping(target = "productId", expression = "java(ProductId.of(uuid))")
    @Mapping(target = "name", source = "putProductsRequestDto.name")
    @Mapping(target = "price", expression = "java(java.math.BigDecimal.valueOf(putProductsRequestDto.getPrice()))")
    @Mapping(target = "description", source = "putProductsRequestDto.description")
    @Mapping(target = "image", source = "putProductsRequestDto.image")
    @Mapping(target = "category", source = "putProductsRequestDto.category.value")
    UpdateProductCommand toCommand(UUID uuid, PutProductsRequestDto putProductsRequestDto);

    @Mapping(target = "productId", expression = "java(ProductId.of(uuid))")
    DeleteProductCommand toCommand(UUID uuid);
}