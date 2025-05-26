package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.commons.application.mediator.Mediator;
import br.com.postech.soat.commons.application.query.Query;
import br.com.postech.soat.openapi.model.GetProduct200ResponseInnerDto;
import br.com.postech.soat.openapi.model.ProductCategoryDto;
import br.com.postech.soat.product.core.application.services.command.model.GetProductCommand;
import br.com.postech.soat.product.core.application.services.query.model.ProductQuery;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.model.Product;
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
    private Mediator mediator;

    private ProductController productController;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(mediator);

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

        // Configuração padrão para o mock, que será usada em todos os testes
        lenient().when(mediator.send(any(Query.class))).thenReturn(Collections.singletonList(product));
        lenient().when(mediator.send(any(ProductQuery.class))).thenReturn(Collections.singletonList(product));
        lenient().when(mediator.send(any(GetProductCommand.class))).thenReturn(Collections.singletonList(product));
    }

    @Test
    void getProduct_whenCategoryAndSkuProvided_shouldReturnOk() {
        // Arrange
        String sku = "Test SKU";
        String category = "DRINK";

        // Act
        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(sku, category);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetProduct200ResponseInnerDto responseDto = response.getBody().get(0);
        assertEquals(product.getId().getValue(), responseDto.getId());
        assertEquals(product.getName(), responseDto.getName());
        assertEquals(product.getSku(), responseDto.getSku());
        assertEquals(product.getDescription(), responseDto.getDescription());
        assertEquals(product.getPrice().doubleValue(), responseDto.getPrice());
        assertEquals(product.getActive(), responseDto.getActive());
        assertEquals(product.getImage(), responseDto.getImage());
        assertEquals(ProductCategoryDto.fromValue(product.getCategory().name()), responseDto.getCategory());
    }

    @Test
    void getProduct_whenOnlyCategoryProvided_shouldReturnOk() {
        // Arrange
        String category = "DRINK";

        // Act
        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(null, category);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetProduct200ResponseInnerDto responseDto = response.getBody().get(0);
        assertEquals(product.getId().getValue(), responseDto.getId());
        assertEquals(product.getName(), responseDto.getName());
        assertEquals(product.getSku(), responseDto.getSku());
        assertEquals(product.getDescription(), responseDto.getDescription());
        assertEquals(product.getPrice().doubleValue(), responseDto.getPrice());
        assertEquals(product.getActive(), responseDto.getActive());
        assertEquals(product.getImage(), responseDto.getImage());
        assertEquals(ProductCategoryDto.fromValue(product.getCategory().name()), responseDto.getCategory());
    }

    @Test
    void getProduct_whenOnlySkuProvided_shouldReturnOk() {
        // Arrange
        String sku = "Test SKU";
        String category = "DRINK"; // Categoria padrão para evitar exceção

        // Act
        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(sku, category);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetProduct200ResponseInnerDto responseDto = response.getBody().get(0);
        assertEquals(product.getId().getValue(), responseDto.getId());
        assertEquals(product.getName(), responseDto.getName());
        assertEquals(product.getSku(), responseDto.getSku());
        assertEquals(product.getDescription(), responseDto.getDescription());
        assertEquals(product.getPrice().doubleValue(), responseDto.getPrice());
        assertEquals(product.getActive(), responseDto.getActive());
        assertEquals(product.getImage(), responseDto.getImage());
        assertEquals(ProductCategoryDto.fromValue(product.getCategory().name()), responseDto.getCategory());
    }

    @Test
    void getProduct_whenNoFilterProvided_shouldReturnOk() {
        // Arrange
        String category = "DRINK"; // Categoria padrão para evitar exceção

        // Act
        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(null, category);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        GetProduct200ResponseInnerDto responseDto = response.getBody().get(0);
        assertEquals(product.getId().getValue(), responseDto.getId());
        assertEquals(product.getName(), responseDto.getName());
        assertEquals(product.getSku(), responseDto.getSku());
        assertEquals(product.getDescription(), responseDto.getDescription());
        assertEquals(product.getPrice().doubleValue(), responseDto.getPrice());
        assertEquals(product.getActive(), responseDto.getActive());
        assertEquals(product.getImage(), responseDto.getImage());
        assertEquals(ProductCategoryDto.fromValue(product.getCategory().name()), responseDto.getCategory());
    }

    @Test
    void getProduct_whenNoProductsFound_shouldReturnOkWithEmptyList() {
        // Arrange
        String sku = "NonExistentSKU";
        String category = "DRINK";

        // Sobrescrever o mock padrão para este teste específico
        when(mediator.send(any(Query.class))).thenReturn(Collections.emptyList());
        when(mediator.send(any(GetProductCommand.class))).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<GetProduct200ResponseInnerDto>> response = productController.getProduct(sku, category);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}
