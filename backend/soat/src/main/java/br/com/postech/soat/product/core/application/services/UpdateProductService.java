package br.com.postech.soat.product.core.application.services;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.product.core.application.dto.UpdateProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.domain.valueobject.ProductCategory;
import br.com.postech.soat.product.core.ports.in.UpdateProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
@Monitorable
public class UpdateProductService implements UpdateProductUseCase {

    @Override
    public Product update(UUID uuid, UpdateProductRequest request, ProductRepository productRepository, Logger logger) {

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
