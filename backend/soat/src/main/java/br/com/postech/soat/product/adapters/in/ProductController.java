package br.com.postech.soat.product.adapters.in;

import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.ports.in.CreateProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final CreateProductUseCase createProductUseCase;

    public ProductController (CreateProductUseCase createProductUseCase) {
        this.createProductUseCase = createProductUseCase;
    }

    @PostMapping
    public ResponseEntity<Product> create (@RequestBody Product product) {
        Product created = createProductUseCase.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
