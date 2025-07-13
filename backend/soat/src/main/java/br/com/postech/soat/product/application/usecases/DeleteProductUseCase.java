package br.com.postech.soat.product.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.LoggerPort;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import java.util.UUID;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@Monitorable
public class DeleteProductUseCase {
    private final ProductRepository productRepository;
    private final LoggerPort logger;

    public DeleteProductUseCase(ProductRepository productRepository, LoggerPort logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    public void execute(UUID uuid) {
        logger.info("Deleting product with ID: " + uuid);

        Product existingProduct = productRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException("Product not found with ID: " + uuid));

        existingProduct.deactivate();
        productRepository.save(existingProduct);

        logger.info("Product deactivated with ID: " + uuid);
    }
}
