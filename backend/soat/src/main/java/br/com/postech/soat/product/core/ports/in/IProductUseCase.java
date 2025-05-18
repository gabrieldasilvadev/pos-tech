package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.adapters.in.dto.CreateProductRequest;
import br.com.postech.soat.product.adapters.in.dto.UpdateProductRequest;
import br.com.postech.soat.product.adapters.out.persistence.entities.ProductEntity;
import br.com.postech.soat.product.core.domain.Category;
import br.com.postech.soat.product.core.domain.Product;
import java.util.List;

public interface IProductUseCase {
    List<ProductEntity> filter(Category category, String sku);
    Product create(CreateProductRequest request);
    Product update(UpdateProductRequest request);
}
