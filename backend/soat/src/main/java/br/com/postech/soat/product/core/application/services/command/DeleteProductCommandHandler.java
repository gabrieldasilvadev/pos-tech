package br.com.postech.soat.product.core.application.services.command;

import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.product.core.application.services.command.model.DeleteProductCommand;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class DeleteProductCommandHandler implements CommandHandler<DeleteProductCommand, Void> {
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(DeleteProductCommandHandler.class);

    @Override
    public Void handle(DeleteProductCommand command) {
        logger.info("Deleting product with ID: {}", command.productId());

        Product existingProduct = productRepository.findById(command.productId().getValue())
            .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        existingProduct.deactivate();
        productRepository.save(existingProduct);

        logger.info("Product deactivated with ID: {}", existingProduct.getId());

        return null;
    }
}