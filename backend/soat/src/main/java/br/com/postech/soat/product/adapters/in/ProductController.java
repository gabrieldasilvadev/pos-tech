package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.openapi.api.ProductApi;
import br.com.postech.soat.openapi.model.PostProducts201ResponseDto;
import br.com.postech.soat.openapi.model.PostProductsRequestDto;
import br.com.postech.soat.openapi.model.ProductDto;
import br.com.postech.soat.openapi.model.PutProductsRequestDto;
import br.com.postech.soat.product.adapters.out.mapper.ProductMapper;
import br.com.postech.soat.product.core.dto.CreateProductInput;
import br.com.postech.soat.product.core.dto.CreateProductOutput;
import br.com.postech.soat.product.core.dto.DeleteProductInput;
import br.com.postech.soat.product.core.dto.UpdateProductInput;
import br.com.postech.soat.product.core.dto.UpdateProductOutput;
import br.com.postech.soat.product.core.ports.in.CreateProductUseCase;
import br.com.postech.soat.product.core.ports.in.DeleteProductUseCase;
import br.com.postech.soat.product.core.ports.in.UpdateProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {
    private final CreateProductUseCase createUseCase;
    private final UpdateProductUseCase updateUseCase;
    private final DeleteProductUseCase deleteUseCase;
    private final ProductMapper productMapper;

    @Override
    public ResponseEntity<PostProducts201ResponseDto> postProducts(PostProductsRequestDto postProductRequest) {
        CreateProductInput coreDto = productMapper.toCoreDto(postProductRequest);
        CreateProductOutput created = createUseCase.create(coreDto);
        PostProducts201ResponseDto response = productMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ProductDto> putProducts(UUID uuid, PutProductsRequestDto putProductRequest) {
        UpdateProductInput input = productMapper.toUpdateInput(putProductRequest);
        UpdateProductOutput output = updateUseCase.update(uuid, input);
        ProductDto response = productMapper.toResponse(output);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<Void> deleteProducts(UUID productId) {
        DeleteProductInput input = productMapper.toDeleteInput(productId);
        deleteUseCase.delete(input);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
