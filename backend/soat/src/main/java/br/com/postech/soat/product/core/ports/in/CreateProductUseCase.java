package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.core.application.dto.CreateProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import org.slf4j.Logger;

public interface CreateProductUseCase {
    Product create(CreateProductRequest request, ProductRepository productRepository, Logger logger);
}



