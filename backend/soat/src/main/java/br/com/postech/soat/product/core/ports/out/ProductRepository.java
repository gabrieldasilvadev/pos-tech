package br.com.postech.soat.product.core.ports.out;

import br.com.postech.soat.product.core.domain.model.Product;
import br.com.postech.soat.product.core.domain.model.ProductId;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(UUID id);

    Optional<Product> findById(ProductId id);

    boolean existsById(UUID uuid);

    boolean existsById(ProductId id);
}
