package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.core.domain.Product;

public interface CreateProductUseCase {
    Product create (Product product);
}
