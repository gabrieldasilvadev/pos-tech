package br.com.postech.soat.product.core.usecase;

import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.ports.in.CreateProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;

public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository repository;

    public CreateProductUseCaseImpl (ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product create (Product product) {
        return repository.save(product);
    }
}
