package br.com.postech.soat.product.core.application.services;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.in.DeleteProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class DeleteProductService implements DeleteProductUseCase {

    @Override
    public void delete(UUID uuid, ProductRepository productRepository, Logger logger) {
        logger.info("Deleting product with ID: {}", uuid);

        Product existingProduct = productRepository.findById(uuid)
            .orElseThrow(() -> new NotFoundException("Product not found with ID: " + uuid));

        existingProduct.deactivate();
        productRepository.save(existingProduct);

        logger.info("Product deactivated with ID: {}", uuid);
    }
}
