package br.com.postech.soat.product.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.product.application.dto.UpdateProductDto;
import br.com.postech.soat.product.domain.entity.Product;
import br.com.postech.soat.product.application.adapters.LoggerPort;
import br.com.postech.soat.product.application.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@Monitorable
public class UpdateProductUseCase {
    private final ProductRepository productRepository;
    private final LoggerPort logger;

    public UpdateProductUseCase(ProductRepository productRepository, LoggerPort logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    public Product execute(UUID uuid, UpdateProductDto request) {

        logger.info("Updating product with ID: " + uuid);

        Product existingProduct = productRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException("Product not found with ID: " + uuid));

        existingProduct.update(
            request.name(),
            request.price(),
            request.description(),
            request.image(),
            request.category()
        );

        final Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated with ID: " + updatedProduct.getId());

        return updatedProduct;
    }
}
