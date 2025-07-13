package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.openapi.model.GetProduct200ResponseInnerDto;
import br.com.postech.soat.openapi.model.ProductCategoryDto;
import br.com.postech.soat.product.adapters.in.http.ProductWebMapper;
import br.com.postech.soat.product.application.usecases.CreateProductUseCase;
import br.com.postech.soat.product.application.usecases.DeleteProductUseCase;
import br.com.postech.soat.product.application.usecases.FindProductUseCase;
import br.com.postech.soat.product.application.usecases.UpdateProductUseCase;
import br.com.postech.soat.product.core.application.dto.FindProductRequest;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
    private CreateProductUseCase createProductUseCase;
    @Mock
    private FindProductUseCase findProductUseCase;
    @Mock
    private UpdateProductUseCase updateProductUseCase;
    @Mock
    private DeleteProductUseCase deleteProductUseCase;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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

        lenient().when(productWebMapper.toListResponse(List.of(product))).thenReturn(List.of(responseDto));
        lenient().when(findProductUseCase.execute(any(FindProductRequest.class)))
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

        when(findProductUseCase.execute(any(FindProductRequest.class)))
            .thenReturn(Collections.emptyList());
        when(productWebMapper.toListResponse(Collections.emptyList()))
            .thenReturn(Collections.emptyList());

        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(sku, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}
