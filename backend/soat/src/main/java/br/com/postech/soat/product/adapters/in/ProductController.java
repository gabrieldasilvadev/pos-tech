package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.commons.application.mediator.Mediator;
import br.com.postech.soat.openapi.api.ProductApi;
import br.com.postech.soat.openapi.model.PostProducts201ResponseDto;
import br.com.postech.soat.openapi.model.PostProductsRequestDto;
import br.com.postech.soat.openapi.model.ProductDto;
import br.com.postech.soat.openapi.model.ProductList200ResponseDto;
import br.com.postech.soat.openapi.model.PutProductsRequestDto;
import br.com.postech.soat.product.adapters.in.mapper.ProductCommandMapper;
import br.com.postech.soat.product.adapters.in.mapper.ProductQueryMapper;
import br.com.postech.soat.product.core.application.services.query.model.ProductQuery;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.domain.model.ProductId;
import java.util.List;
import java.util.UUID;
import br.com.postech.soat.product.core.exception.InvalidCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {
    private final Mediator mediator;

    @Override
    public ResponseEntity<List<ProductList200ResponseDto>> getProduct(String sku, String category
    ) {
        Category categoryEnum = null;
        if (category != null) {
            try {
                categoryEnum = Category.valueOf(category.toUpperCase());
            } catch (Exception e) {
                throw new InvalidCategoryException("Invalid Category: " + category);
            }
        }

        List<Product> result = mediator.send(ProductCommandMapper.INSTANCE.toCommand(categoryEnum, sku));

        return ResponseEntity.ok(result.stream().map(product -> ProductList200ResponseDto.builder()
            .id(product.getId().getValue())
            .name(product.getName())
            .sku(product.getSku())
            .description(product.getDescription())
            .price(product.getPrice())
            .active(product.getActive())
            .image(product.getImage())
            .category(String.valueOf(product.getCategory()))
            .build()).toList());
    }

    @Override
    public ResponseEntity<PostProducts201ResponseDto> postProducts(PostProductsRequestDto postProductRequest) {
        final ProductId productId = mediator.send(ProductCommandMapper.INSTANCE.toCommand(postProductRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(PostProducts201ResponseDto.builder()
            .id(productId.getValue().toString())
            .message("Produto criado com sucesso")
            .build());
    }

    @Override
    public ResponseEntity<ProductDto> putProducts(UUID uuid, PutProductsRequestDto putProductRequest) {
        final ProductId productId = mediator.send(ProductCommandMapper.INSTANCE.toCommand(uuid, putProductRequest));
        final Product product = mediator.send(new ProductQuery(productId));
        return ResponseEntity.status(HttpStatus.OK).body(ProductQueryMapper.INSTANCE.toResponse(product));
    }

    @Override
    public ResponseEntity<Void> deleteProducts(UUID productId) {
        mediator.sendUnit(ProductCommandMapper.INSTANCE.toCommand(productId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
