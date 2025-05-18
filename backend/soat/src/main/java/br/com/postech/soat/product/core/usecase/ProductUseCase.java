package br.com.postech.soat.product.core.usecase;

import br.com.postech.soat.product.adapters.in.dto.CreateProductRequest;
import br.com.postech.soat.product.adapters.in.dto.UpdateProductRequest;
import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.adapters.out.specification.ProductSpecification;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.exception.ProductNotFoundException;
import br.com.postech.soat.product.core.ports.in.IProductUseCase;
import br.com.postech.soat.product.core.ports.out.IProductRepository;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductUseCase implements IProductUseCase {
    private final IProductRepository repository;

    public ProductUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductEntity> filter(Category category, String sku) {
        Specification<ProductEntity> spec = Specification.where(ProductSpecification.hasCategory(category))
            .and(ProductSpecification.hasSku(sku));
        return repository.findAll(spec);
    }

    @Override
    public Product create (CreateProductRequest request) {
        Product product = new Product(
            UUID.randomUUID(),
            request.getSku(),
            request.getName(),
            request.getPrice(),
            request.getDescription(),
            request.getImage(),
            request.getCategory()
        );

        return repository.save(product);
    }

    @Override
    public Product update (UpdateProductRequest request) {
        if (request.getId() == null) {
            throw new IllegalArgumentException("Id do produto é obrigatório");
        }

        Product existingProduct = repository.findById(request.getId())
            .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        Optional.ofNullable(request.getName()).ifPresent(existingProduct::setName);
        Optional.ofNullable(request.getPrice()).ifPresent(existingProduct::setPrice);
        Optional.ofNullable(request.getDescription()).ifPresent(existingProduct::setDescription);
        Optional.ofNullable(request.getImage()).ifPresent(existingProduct::setImage);
        Optional.ofNullable(request.getCategory()).ifPresent(existingProduct::setCategory);
        Optional.ofNullable(request.getActive()).ifPresent(existingProduct::setActive);

        return repository.save(existingProduct);
    }
}
