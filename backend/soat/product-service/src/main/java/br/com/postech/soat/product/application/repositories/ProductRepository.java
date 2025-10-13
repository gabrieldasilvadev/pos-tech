package br.com.postech.soat.product.application.repositories;

import br.com.postech.soat.product.application.dto.FindProductQuery;
import br.com.postech.soat.product.domain.entity.Product;
import br.com.postech.soat.product.domain.entity.ProductId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAll(FindProductQuery request);

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Optional<Product> findById(ProductId id);

    boolean existsById(UUID uuid);

    boolean existsById(ProductId id);

    boolean existsBySku(String sku);
}
