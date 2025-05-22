package br.com.postech.soat.product.core.usecase;

import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.dto.DeleteProductInput;
import br.com.postech.soat.product.core.exception.ProductNotFoundException;
import br.com.postech.soat.product.core.ports.in.DeleteProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;

public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository repository;

    public DeleteProductUseCaseImpl (ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(DeleteProductInput input) {
        Product productToSoftDelete = repository.findById(input.getId())
            .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        productToSoftDelete.setActive(false);

        repository.save(productToSoftDelete);
    }
}
