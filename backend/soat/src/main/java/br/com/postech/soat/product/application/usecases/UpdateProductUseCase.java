package br.com.postech.soat.product.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.product.core.application.dto.UpdateProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.slf4j.Logger;

@Service
@Monitorable
public class UpdateProductUseCase {
    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(UUID uuid, UpdateProductRequest request, Logger logger) {

        logger.info("Updating product with ID: {}", uuid);

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
        logger.info("Product updated with ID: {}", updatedProduct.getId());

        return updatedProduct;
    }
}
