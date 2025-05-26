package br.com.postech.soat.product.core.application.services.command;

import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.product.core.application.services.command.model.GetProductCommand;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class GetProductCommandHandler implements CommandHandler<GetProductCommand, List<Product>> {
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(GetProductCommandHandler.class);

    @Override
    public List<Product> handle(GetProductCommand command) {
        logger.info("Retrieving products with filters: {}", command);

        Product product = command.product();
        List<Product> products = productRepository.findAll(product);

        logger.info("Found {} products", products.size());
        return products;
    }
}