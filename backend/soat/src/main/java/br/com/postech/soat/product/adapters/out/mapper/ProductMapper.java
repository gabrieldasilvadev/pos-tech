package br.com.postech.soat.product.adapters.out.mapper;

import br.com.postech.soat.openapi.model.PostProducts201Response;
import br.com.postech.soat.openapi.model.PostProductsRequest;
import br.com.postech.soat.openapi.model.PutProductsRequest;
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
    CreateProductInput toCoreDto(PostProductsRequest request);

    PostProducts201Response toResponse(CreateProductOutput product);

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);

    UpdateProductInput toUpdateInput(PutProductsRequest request);

    br.com.postech.soat.openapi.model.Product toResponse(UpdateProductOutput product);

    DeleteProductInput toDeleteInput (UUID id);
}