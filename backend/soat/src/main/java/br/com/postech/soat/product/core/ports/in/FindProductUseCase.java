package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.core.application.dto.FindProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import java.util.List;

public interface FindProductUseCase {
    List<Product> findProduct(FindProductRequest request, ProductRepository productRepository);
}
