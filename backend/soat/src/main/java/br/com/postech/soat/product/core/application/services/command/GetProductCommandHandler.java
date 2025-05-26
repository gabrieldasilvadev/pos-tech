package br.com.postech.soat.product.core.application.services.command;

import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.adapters.out.specification.ProductSpecification;
import br.com.postech.soat.product.core.application.services.command.model.GetProductCommand;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Monitorable
public class GetProductCommandHandler implements CommandHandler<GetProductCommand, List<Product>> {
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(GetProductCommandHandler.class);

    @Override
    public List<Product> handle(GetProductCommand command) {
        logger.info("Starting filter products");
        Specification<ProductEntity> spec = Specification.where(ProductSpecification.isActive())
            .and(ProductSpecification.hasCategory(command.category()))
            .and(ProductSpecification.hasSku(command.sku()))
            .and(ProductSpecification.isActive());

        logger.info("Finish filter products");
        return productRepository.findAll(spec);
    }
}