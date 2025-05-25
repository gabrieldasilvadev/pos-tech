package br.com.postech.soat.product.core.usecase;

import br.com.postech.soat.product.core.domain.Product;
import br.com.postech.soat.product.core.dto.CreateProductInput;
import br.com.postech.soat.product.core.dto.CreateProductOutput;
import br.com.postech.soat.product.core.ports.in.CreateProductUseCase;
import br.com.postech.soat.product.core.ports.out.ProductRepository;
import java.util.UUID;

public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository repository;

    public CreateProductUseCaseImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public CreateProductOutput create(CreateProductInput input) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (repository.existsById(uuid));

        Product product = new Product(
            uuid,
            input.getSku(),
            input.getName(),
            input.getPrice(),
            input.getDescription(),
            input.getImage(),
            input.getCategory()
        );

        final Product saved = repository.save(product);

        return new CreateProductOutput(
            saved.getId(),
            "Produto criado com sucesso!"
        );
    }
}
