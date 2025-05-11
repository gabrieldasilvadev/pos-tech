package br.com.postech.soat.product.core.ports.out;

import br.com.postech.soat.product.core.domain.Product;

public interface ProductRepository {
    Product save(Product product);
}
