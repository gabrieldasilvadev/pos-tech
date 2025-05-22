package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.openapi.api.ProductApi;
import br.com.postech.soat.openapi.model.PostProducts201Response;
import br.com.postech.soat.openapi.model.PostProductsRequest;
import br.com.postech.soat.openapi.model.Product;
import br.com.postech.soat.openapi.model.PutProductsRequest;
import br.com.postech.soat.product.adapters.out.mapper.ProductMapper;
import br.com.postech.soat.product.core.dto.CreateProductInput;
import br.com.postech.soat.product.core.dto.CreateProductOutput;
import br.com.postech.soat.product.core.dto.DeleteProductInput;
import br.com.postech.soat.product.core.dto.UpdateProductInput;
import br.com.postech.soat.product.core.dto.UpdateProductOutput;
import br.com.postech.soat.product.core.ports.in.CreateProductUseCase;
import br.com.postech.soat.product.core.ports.in.DeleteProductUseCase;
import br.com.postech.soat.product.core.ports.in.UpdateProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class ProductController implements ProductApi {
    private final CreateProductUseCase createUseCase;
    private final UpdateProductUseCase updateUseCase;
    private final DeleteProductUseCase deleteUseCase;
    private final ProductMapper productMapper;

    public ProductController (
        CreateProductUseCase createUseCase,
        UpdateProductUseCase updateUseCase,
        DeleteProductUseCase deleteUseCase,
        ProductMapper productMapper
    ) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.productMapper = productMapper;
    }

    @Override
    public ResponseEntity<PostProducts201Response> postProducts(PostProductsRequest postProductRequest) {
        CreateProductInput coreDto = productMapper.toCoreDto(postProductRequest);
        CreateProductOutput created = createUseCase.create(coreDto);
        PostProducts201Response response = productMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Product> putProducts(UUID uuid, PutProductsRequest putProductRequest) {
        UpdateProductInput input = productMapper.toUpdateInput(putProductRequest);
        UpdateProductOutput output = updateUseCase.update(uuid, input);
        Product response = productMapper.toResponse(output);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteProducts(UUID productId) {
        DeleteProductInput input = productMapper.toDeleteInput(productId);
        deleteUseCase.delete(input);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
