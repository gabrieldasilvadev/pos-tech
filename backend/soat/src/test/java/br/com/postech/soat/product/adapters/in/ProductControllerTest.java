package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.openapi.model.GetProduct200ResponseInnerDto;
import br.com.postech.soat.openapi.model.ProductCategoryDto;
import br.com.postech.soat.product.infrastructure.http.ProductController;
import br.com.postech.soat.product.infrastructure.http.ProductWebMapper;
import br.com.postech.soat.product.infrastructure.LoggerAdapter;
import br.com.postech.soat.product.application.repositories.ProductRepository;
import br.com.postech.soat.product.application.usecases.FindProductUseCase;
import br.com.postech.soat.product.application.dto.FindProductQuery;
import br.com.postech.soat.product.domain.enumtypes.Category;
import br.com.postech.soat.product.domain.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductWebMapper productWebMapper;
    @Mock
    private FindProductUseCase findProductUseCase;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private LoggerAdapter loggerAdapter;

    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productController = new ProductController(productRepository, productWebMapper);

        this.findProductUseCase = new FindProductUseCase(productRepository, loggerAdapter);

        UUID productIdValue = UUID.randomUUID();

        product = Product.builder()
            .productId(productIdValue)
            .sku("Test SKU")
            .name("Test Product")
            .description("This is a test product.")
            .image("http://example.com/image.jpg")
            .price(BigDecimal.valueOf(19.99))
            .category(Category.DRINK)
            .active(true)
            .build();

        GetProduct200ResponseInnerDto responseDto = new GetProduct200ResponseInnerDto()
            .id(product.getId().getValue())
            .name(product.getName().value())
            .sku(product.getSku().value())
            .description(product.getDescription().value())
            .price(product.getPrice().value().doubleValue())
            .active(product.getActive())
            .image(product.getImage().value())
            .category(ProductCategoryDto.fromValue(product.getCategory().value()));

        lenient().when(productRepository.findAll(any(FindProductQuery.class))).thenReturn(List.of(product));
        lenient().when(productWebMapper.toListResponse(List.of(product))).thenReturn(List.of(responseDto));
        lenient().when(findProductUseCase.execute(any(FindProductQuery.class)))
            .thenReturn(List.of(product));
    }

    @Test
    void getProduct_whenCategoryAndSkuProvided_shouldReturnOk() {
        String sku = "Test SKU";
        String category = "DRINK";

        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(sku, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetProduct200ResponseInnerDto responseDto = response.getBody().get(0);
        assertEquals(product.getId().getValue(), responseDto.getId());
        assertEquals(product.getName().value(), responseDto.getName());
        assertEquals(product.getSku().value(), responseDto.getSku());
        assertEquals(product.getDescription().value(), responseDto.getDescription());
        assertEquals(product.getPrice().value(), BigDecimal.valueOf(responseDto.getPrice()));
        assertEquals(product.getActive(), responseDto.getActive());
        assertEquals(product.getImage().value(), responseDto.getImage());
        assertEquals(ProductCategoryDto.fromValue(product.getCategory().value()), responseDto.getCategory());
    }

    @Test
    void getProduct_whenOnlyCategoryProvided_shouldReturnOk() {
        String category = "DRINK";

        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(null, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetProduct200ResponseInnerDto responseDto = response.getBody().get(0);
        assertEquals(product.getId().getValue(), responseDto.getId());
        assertEquals(product.getName().value(), responseDto.getName());
        assertEquals(product.getSku().value(), responseDto.getSku());
        assertEquals(product.getDescription().value(), responseDto.getDescription());
        assertEquals(product.getPrice().value(), BigDecimal.valueOf(responseDto.getPrice()));
        assertEquals(product.getActive(), responseDto.getActive());
        assertEquals(product.getImage().value(), responseDto.getImage());
        assertEquals(ProductCategoryDto.fromValue(product.getCategory().value()), responseDto.getCategory());
    }

    @Test
    void getProduct_whenOnlySkuProvided_shouldReturnOk() {
        String sku = "Test SKU";
        String category = "DRINK";

        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(sku, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetProduct200ResponseInnerDto responseDto = response.getBody().get(0);
        assertEquals(product.getId().getValue(), responseDto.getId());
        assertEquals(product.getName().value(), responseDto.getName());
        assertEquals(product.getSku().value(), responseDto.getSku());
        assertEquals(product.getDescription().value(), responseDto.getDescription());
        assertEquals(product.getPrice().value(), BigDecimal.valueOf(responseDto.getPrice()));
        assertEquals(product.getActive(), responseDto.getActive());
        assertEquals(product.getImage().value(), responseDto.getImage());
        assertEquals(ProductCategoryDto.fromValue(product.getCategory().value()), responseDto.getCategory());
    }

    @Test
    void getProduct_whenNoFilterProvided_shouldReturnOk() {
        String category = "DRINK";

        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(null, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetProduct200ResponseInnerDto responseDto = response.getBody().get(0);
        assertEquals(product.getId().getValue(), responseDto.getId());
        assertEquals(product.getName().value(), responseDto.getName());
        assertEquals(product.getSku().value(), responseDto.getSku());
        assertEquals(product.getDescription().value(), responseDto.getDescription());
        assertEquals(product.getPrice().value(), BigDecimal.valueOf(responseDto.getPrice()));
        assertEquals(product.getActive(), responseDto.getActive());
        assertEquals(product.getImage().value(), responseDto.getImage());
        assertEquals(ProductCategoryDto.fromValue(product.getCategory().value()), responseDto.getCategory());
    }

    @Test
    void getProduct_whenNoProductsFound_shouldReturnOkWithEmptyList() {
        String sku = "NonExistentSKU";
        String category = "DRINK";

        when(productRepository.findAll(any(FindProductQuery.class))).thenReturn(Collections.emptyList());
        when(findProductUseCase.execute(any(FindProductQuery.class)))
            .thenReturn(Collections.emptyList());
        when(productWebMapper.toListResponse(Collections.emptyList()))
            .thenReturn(Collections.emptyList());

        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(sku, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}
