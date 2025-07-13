package br.com.postech.soat.product.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.product.core.application.dto.FindProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.ports.out.LoggerPort;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Monitorable
public class FindProductUseCase{
    private final ProductRepository productRepository;
    private final LoggerPort logger;

    public FindProductUseCase(ProductRepository productRepository, LoggerPort logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    public List<Product> execute(FindProductRequest request) {
        logger.info("Starting to find products");
        return productRepository.findAll(request);
    }
}
