package br.com.postech.soat.product.adapters.out.persistence;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.product.adapters.out.mapper.ProductMapper;
import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.adapters.out.persistence.entities.repositories.ProductJpaRepository;
import br.com.postech.soat.product.adapters.out.persistence.specification.ProductSpecification;
import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.domain.model.ProductId;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Monitorable
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository jpaRepository;

    @Override
    public List<Product> findAll(Product product) {

        Specification<ProductEntity> spec = ProductSpecification.fromDomain(product);
        List<ProductEntity> result = jpaRepository.findAll(spec);

        return result.stream().map(ProductMapper.INSTANCE::toDomain).toList();
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.INSTANCE.toEntity(product);
        final ProductEntity saved = jpaRepository.save(entity);
        return ProductMapper.INSTANCE.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(ProductMapper.INSTANCE::toDomain);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return findById(id.getValue());
    }

    @Override
    public boolean existsById(UUID uuid) {
        return jpaRepository.existsById(uuid);
    }

    @Override
    public boolean existsById(ProductId id) {
        return existsById(id.getValue());
    }
}
