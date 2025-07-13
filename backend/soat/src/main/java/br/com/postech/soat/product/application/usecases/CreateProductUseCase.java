package br.com.postech.soat.product.application.usecases;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.product.core.application.dto.CreateProductRequest;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.domain.valueobject.ProductCategory;
import br.com.postech.soat.product.core.domain.valueobject.ProductDescription;
import br.com.postech.soat.product.core.domain.valueobject.ProductImage;
import br.com.postech.soat.product.core.domain.valueobject.ProductName;
import br.com.postech.soat.product.core.domain.valueobject.ProductPrice;
import br.com.postech.soat.product.core.domain.valueobject.ProductSKU;
import br.com.postech.soat.product.core.ports.out.LoggerPort;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
@Monitorable
public class CreateProductUseCase {
    private final ProductRepository productRepository;
    private final LoggerPort logger;

    public CreateProductUseCase(ProductRepository productRepository, LoggerPort logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    public Product execute(CreateProductRequest request){
        ProductSKU sku = new ProductSKU(request.sku());
        ProductName name = new ProductName(request.name());
        ProductPrice price = new ProductPrice(request.price());
        ProductDescription description = new ProductDescription(request.description());
        ProductImage image = new ProductImage(request.image());
        ProductCategory category = new ProductCategory(request.category());

        validateSkuDoesNotExist(sku, productRepository);

        final Product product = Product.create(sku, name, price,
            description, image, category);

        logger.info("Domain product created: " + product.getSku());

        return productRepository.save(product);
    };

    private void validateSkuDoesNotExist(ProductSKU sku, ProductRepository productRepository){
        boolean exists = productRepository.existsBySku(sku.value());
        if (exists) {
            throw new ResourceConflictException(
                "SKU: " + sku.value() + " já existe");
            }
    }
}
