package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.openapi.model.ProductDto;
import br.com.postech.soat.product.adapters.out.LoggerAdapter;
import br.com.postech.soat.product.application.usecases.CreateProductUseCase;
import br.com.postech.soat.product.application.usecases.DeleteProductUseCase;
import br.com.postech.soat.product.application.usecases.FindProductUseCase;
import br.com.postech.soat.product.application.usecases.UpdateProductUseCase;
import br.com.postech.soat.product.core.application.dto.CreateProductRequest;
import br.com.postech.soat.openapi.api.ProductApi;
import br.com.postech.soat.openapi.model.GetProduct200ResponseInnerDto;
import br.com.postech.soat.openapi.model.PostProducts201ResponseDto;
import br.com.postech.soat.openapi.model.PostProductsRequestDto;
import br.com.postech.soat.openapi.model.PutProductsRequestDto;
import br.com.postech.soat.product.adapters.in.http.ProductWebMapper;
import br.com.postech.soat.product.core.application.dto.FindProductRequest;
import br.com.postech.soat.product.core.application.dto.UpdateProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import java.util.List;
import java.util.UUID;
import br.com.postech.soat.product.core.ports.out.LoggerPort;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {
    private final CreateProductUseCase createProductUseCase;
    private final FindProductUseCase findProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ProductWebMapper productWebMapper;

    public ProductController(ProductRepository productRepository, ProductWebMapper productWebMapper) {
        LoggerPort logger = new LoggerAdapter(ProductController.class);
        this.createProductUseCase = new CreateProductUseCase(productRepository, logger);
        this.findProductUseCase = new FindProductUseCase(productRepository, logger);
        this.updateProductUseCase = new UpdateProductUseCase(productRepository, logger);
        this.deleteProductUseCase = new DeleteProductUseCase(productRepository, logger);

        this.productWebMapper = productWebMapper;
    }

    @Override
    public ResponseEntity<List<GetProduct200ResponseInnerDto>> getProduct(String sku, String category) {
        FindProductRequest request = FindProductRequest.from(sku, category);
        final List<Product> result = findProductUseCase.execute(request);
        return ResponseEntity.ok(productWebMapper.toListResponse(result));
    }

    @Override
    public ResponseEntity<PostProducts201ResponseDto> postProducts(PostProductsRequestDto productDto){
        CreateProductRequest productRequest = productWebMapper.toCreateRequest(productDto);
        final Product product = createProductUseCase.execute(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productWebMapper.toCreateResponse(product));
    }

    @Override
    public ResponseEntity<ProductDto> putProducts(UUID uuid, PutProductsRequestDto putProductRequest) {
        UpdateProductRequest productRequest = productWebMapper.toUpdateRequest(putProductRequest);
        final Product product = updateProductUseCase.execute(uuid, productRequest);
        return ResponseEntity.status(HttpStatus.OK)
            .body(productWebMapper.toUpdateResponse(product));
    }

    @Override
    public ResponseEntity<Void> deleteProducts(UUID productId) {
        deleteProductUseCase.execute(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
