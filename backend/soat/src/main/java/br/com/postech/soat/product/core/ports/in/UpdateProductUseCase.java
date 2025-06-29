package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.core.application.dto.UpdateProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import java.util.UUID;
import org.slf4j.Logger;

public interface UpdateProductUseCase {
    Product update(UUID uuid, UpdateProductRequest request, ProductRepository productRepository, Logger logger);
}
