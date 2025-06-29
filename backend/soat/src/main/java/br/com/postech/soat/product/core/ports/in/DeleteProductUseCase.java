package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.core.ports.out.ProductRepository;
import java.util.UUID;
import org.slf4j.Logger;

public interface DeleteProductUseCase {
    void delete(UUID uuid, ProductRepository productRepository, Logger logger);
};
