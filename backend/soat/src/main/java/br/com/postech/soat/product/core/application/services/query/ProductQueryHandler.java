package br.com.postech.soat.product.core.application.services.query;

import br.com.postech.soat.commons.application.query.QueryHandler;
import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.NotFoundException;
import br.com.postech.soat.product.core.application.services.query.model.ProductQuery;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Monitorable
public class ProductQueryHandler implements QueryHandler<ProductQuery, Product> {
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductQueryHandler.class);

    @Override
    public Product handle(ProductQuery query) {
        logger.info("Querying product with ID: {}", query.productId());

        return productRepository.findById(query.productId().getValue())
            .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }
}