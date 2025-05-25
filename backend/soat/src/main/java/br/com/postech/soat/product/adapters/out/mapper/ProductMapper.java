package br.com.postech.soat.product.adapters.out.mapper;

import br.com.postech.soat.openapi.model.PostProducts201ResponseDto;
import br.com.postech.soat.openapi.model.PostProductsRequestDto;
import br.com.postech.soat.openapi.model.ProductDto;
import br.com.postech.soat.openapi.model.PutProductsRequestDto;
import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.dto.CreateProductInput;
import br.com.postech.soat.product.core.dto.CreateProductOutput;
import br.com.postech.soat.product.core.dto.DeleteProductInput;
import br.com.postech.soat.product.core.dto.UpdateProductInput;
import br.com.postech.soat.product.core.dto.UpdateProductOutput;
import org.mapstruct.Mapper;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    CreateProductInput toCoreDto(PostProductsRequestDto request);

    PostProducts201ResponseDto toResponse(CreateProductOutput product);

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);

    UpdateProductInput toUpdateInput(PutProductsRequestDto request);

    ProductDto toResponse(UpdateProductOutput product);

    DeleteProductInput toDeleteInput (UUID id);
}