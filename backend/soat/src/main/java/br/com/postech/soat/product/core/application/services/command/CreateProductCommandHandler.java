package br.com.postech.soat.product.core.application.services.command;

import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.product.core.application.services.command.model.CreateProductCommand;
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
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand, ProductId> {
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(CreateProductCommandHandler.class);

    @Override
    public ProductId handle(CreateProductCommand command) {
        logger.info("Creating product with SKU: {}", command.sku());
        
        final Product product = Product.create(
            command.sku(),
            command.name(),
            command.price(),
            command.description(),
            command.image(),
            command.category()
        );

        final Product savedProduct = productRepository.save(product);
        logger.info("Product created with ID: {}", savedProduct.getId());
        
        return savedProduct.getId();
    }
}