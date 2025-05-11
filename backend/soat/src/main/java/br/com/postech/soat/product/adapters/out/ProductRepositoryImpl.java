package br.com.postech.soat.product.adapters.out;

import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryImpl (ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save (Product product) {
        ProductEntity entity = new ProductEntity();

        entity.setId(product.getId());
        entity.setSku(product.getSku());
        entity.setActive(product.getActive());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setDescription(product.getDescription());
        entity.setImage(product.getImage());
        entity.setCategory(product.getCategory());

        ProductEntity saved = jpaRepository.save(entity);

        return new Product(
            saved.getId(),
            saved.getSku(),
            saved.getActive(),
            saved.getName(),
            saved.getPrice(),
            saved.getDescription(),
            saved.getImage(),
            saved.getCategory()
        );
    }
}
