package br.com.postech.soat.product.core.application.services.command;

import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.product.core.application.services.command.model.UpdateProductCommand;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.domain.model.ProductId;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class UpdateProductCommandHandler implements CommandHandler<UpdateProductCommand, ProductId> {
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateProductCommandHandler.class);

    @Override
    public ProductId handle(UpdateProductCommand command) {
        logger.info("Updating product with ID: {}", command.productId());

        Product existingProduct = productRepository.findById(command.productId().getValue())
            .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        existingProduct.update(
            command.name(),
            command.price(),
            command.description(),
            command.image(),
            command.category()
        );

        final Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated with ID: {}", updatedProduct.getId());

        return updatedProduct.getId();
    }
}
