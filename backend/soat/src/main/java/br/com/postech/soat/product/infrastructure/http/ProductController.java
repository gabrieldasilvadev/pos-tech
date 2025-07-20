package br.com.postech.soat.product.infrastructure.http;

import br.com.postech.soat.openapi.model.ProductDto;
import br.com.postech.soat.product.infrastructure.LoggerAdapter;
import br.com.postech.soat.product.application.dto.UpdateProductDto;
import br.com.postech.soat.product.application.usecases.CreateProductUseCase;
import br.com.postech.soat.product.application.usecases.DeleteProductUseCase;
import br.com.postech.soat.product.application.usecases.FindProductUseCase;
import br.com.postech.soat.product.application.usecases.UpdateProductUseCase;
import br.com.postech.soat.product.application.dto.CreateProductDto;
import br.com.postech.soat.openapi.api.ProductApi;
import br.com.postech.soat.openapi.model.GetProduct200ResponseInnerDto;
import br.com.postech.soat.openapi.model.PostProducts201ResponseDto;
import br.com.postech.soat.openapi.model.PostProductsRequestDto;
import br.com.postech.soat.openapi.model.PutProductsRequestDto;
import br.com.postech.soat.product.application.dto.FindProductQuery;
import br.com.postech.soat.product.domain.entity.Product;
import java.util.List;
import java.util.UUID;
import br.com.postech.soat.product.application.adapters.LoggerPort;
import br.com.postech.soat.product.application.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements ProductApi {
    private final CreateProductUseCase createProductUseCase;
    private final FindProductUseCase findProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ProductWebMapper productWebMapper;

    public ProductController(ProductRepository productRepository, ProductWebMapper productWebMapper) {
        LoggerPort logger = new LoggerAdapter();

        this.createProductUseCase = new CreateProductUseCase(productRepository, logger);
        this.findProductUseCase = new FindProductUseCase(productRepository, logger);
        this.updateProductUseCase = new UpdateProductUseCase(productRepository, logger);
        this.deleteProductUseCase = new DeleteProductUseCase(productRepository, logger);

        this.productWebMapper = productWebMapper;
    }

    @Override
    public ResponseEntity<List<GetProduct200ResponseInnerDto>> getProduct(String sku, String category) {
        FindProductQuery request = FindProductQuery.from(sku, category);
        final List<Product> result = findProductUseCase.execute(request);
        return ResponseEntity.ok(productWebMapper.toListResponse(result));
    }

    @Override
    public ResponseEntity<PostProducts201ResponseDto> postProducts(PostProductsRequestDto productDto){
        CreateProductDto productRequest = productWebMapper.toCreateRequest(productDto);
        final Product product = createProductUseCase.execute(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productWebMapper.toCreateResponse(product));
    }

    @Override
    public ResponseEntity<ProductDto> putProducts(UUID uuid, PutProductsRequestDto putProductRequest) {
        UpdateProductDto productRequest = productWebMapper.toUpdateRequest(putProductRequest);
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
