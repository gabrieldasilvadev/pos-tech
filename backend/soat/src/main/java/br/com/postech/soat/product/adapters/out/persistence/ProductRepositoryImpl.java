package br.com.postech.soat.product.adapters.out.persistence;

import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.adapters.out.persistence.entities.mappers.ProductMapper;
import br.com.postech.soat.product.adapters.out.persistence.entities.repositories.IProductJpaRepository;
import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.ports.out.IProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductRepositoryImpl implements IProductRepository {
    private final IProductJpaRepository jpaRepository;

    public ProductRepositoryImpl (IProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<ProductEntity> findAll(Specification<ProductEntity> spec) {
        return jpaRepository.findAll(spec);
    }

    @Override
    public Product save (Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        ProductEntity saved = jpaRepository.save(entity);

        return ProductMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(ProductMapper::toDomain);
    }
}
