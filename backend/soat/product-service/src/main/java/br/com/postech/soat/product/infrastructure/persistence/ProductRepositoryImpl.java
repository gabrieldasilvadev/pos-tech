package br.com.postech.soat.product.infrastructure.persistence;

import br.com.postech.soat.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.product.infrastructure.mapper.ProductMapper;
import br.com.postech.soat.product.infrastructure.persistence.entities.ProductEntity;
import br.com.postech.soat.product.infrastructure.persistence.entities.repositories.ProductJpaRepository;
import br.com.postech.soat.product.infrastructure.persistence.specification.ProductSpecification;
import br.com.postech.soat.product.application.dto.FindProductQuery;
import br.com.postech.soat.product.domain.entity.Product;
import br.com.postech.soat.product.domain.entity.ProductId;
import br.com.postech.soat.product.application.repositories.ProductRepository;
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
    public List<Product> findAll(FindProductQuery request) {

        Specification<ProductEntity> spec = ProductSpecification.fromDomain(request);
        List<ProductEntity> result = jpaRepository.findAll(spec);

        return result.stream().map(ProductMapper::toDomain).toList();
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        final ProductEntity saved = jpaRepository.save(entity);
        return ProductMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(ProductMapper::toDomain);
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

    @Override
    public boolean existsBySku(String sku) {
        return jpaRepository.existsBySku(sku);
    }
}
