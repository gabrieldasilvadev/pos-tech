package br.com.postech.soat.product.core.application.services;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.commons.infrastructure.exception.ResourceConflictException;
import br.com.postech.soat.customer.core.domain.model.Customer;
import br.com.postech.soat.product.core.application.dto.CreateProductRequest;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.domain.valueobject.ProductCategory;
import br.com.postech.soat.product.core.domain.valueobject.ProductDescription;
import br.com.postech.soat.product.core.domain.valueobject.ProductImage;
import br.com.postech.soat.product.core.domain.valueobject.ProductName;
import br.com.postech.soat.product.core.domain.valueobject.ProductPrice;
import br.com.postech.soat.product.core.domain.valueobject.SKU;
import br.com.postech.soat.product.core.ports.in.CreateProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
@Monitorable
public class CreateProductService implements CreateProductUseCase {

    @Override
    public Product create(CreateProductRequest request, ProductRepository productRepository, Logger logger){
        SKU sku = new SKU(request.sku());
        ProductName name = new ProductName(request.name());
        ProductPrice price = new ProductPrice(request.price());
        ProductDescription description = new ProductDescription(request.description());
        ProductImage image = new ProductImage(request.image());
        ProductCategory category = new ProductCategory(request.category());

        validateSkuDoesNotExist(sku, productRepository);

        final Product product = Product.create(sku, name, price,
            description, image, category);

        logger.info("Domain product created: {}", product);

        return productRepository.save(product);
    };

    private void validateSkuDoesNotExist(SKU sku, ProductRepository productRepository){
        boolean exists = productRepository.existsBySku(sku.value());
        if (exists) {
            throw new ResourceConflictException(
                "SKU: " + sku.value() + " já existe");
            }
    }
}
