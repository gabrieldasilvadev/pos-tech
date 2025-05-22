package br.com.postech.soat.product.core.ports.out;

import br.com.postech.soat.product.core.domain.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save (Product product);
    Optional<Product> findById(UUID id);
    boolean existsById(UUID uuid);
}
