package br.com.postech.soat.product.core.ports.out;

import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.core.domain.Product;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductRepository {
    List<ProductEntity> findAll(Specification<ProductEntity> spec);
    Product save (Product product);
    Optional<Product> findById(UUID id);
}
