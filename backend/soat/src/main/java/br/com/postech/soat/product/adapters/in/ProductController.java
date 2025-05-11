package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.product.adapters.in.dto.CreateProductRequest;
import br.com.postech.soat.product.adapters.in.dto.UpdateProductRequest;
import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.ports.in.IProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductUseCase productUseCase;

    public ProductController (IProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
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
