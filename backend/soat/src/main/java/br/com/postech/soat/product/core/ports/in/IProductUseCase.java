package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.adapters.in.dto.CreateProductRequest;
import br.com.postech.soat.product.adapters.in.dto.UpdateProductRequest;
import br.com.postech.soat.product.core.domain.Product;

public interface IProductUseCase {
    Product create(CreateProductRequest request);
    Product update(UpdateProductRequest request);
}
