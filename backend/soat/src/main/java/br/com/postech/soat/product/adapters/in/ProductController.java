package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.product.adapters.in.dto.CreateProductRequest;
import br.com.postech.soat.product.adapters.in.dto.FilterProductResponse;
import br.com.postech.soat.product.adapters.in.dto.UpdateProductRequest;
import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.ports.in.IProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductUseCase productUseCase;

    public ProductController (IProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping
    @Operation(
        summary = "Busca produtos",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Busca de produtos",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FilterProductResponse.class)
                )
            ),
        }
    )
    public ResponseEntity<List<FilterProductResponse>> filter (@RequestParam(required = false) Category category, @RequestParam(required = false) String sku) {
        List<ProductEntity> result = productUseCase.filter(category, sku);

        List<FilterProductResponse> responseList = result.stream()
            .map(entity -> new FilterProductResponse(
                entity.getId(),
                entity.getSku(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getActive(),
                entity.getImage(),
                entity.getCategory()
            ))
            .toList();

        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<Product> create (@RequestBody @Valid CreateProductRequest request) {
        Product created = productUseCase.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update (@PathVariable UUID id, @RequestBody @Valid UpdateProductRequest request) {
        request.setId(id);
        Product updated = productUseCase.update(request);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
