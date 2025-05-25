package br.com.postech.soat.product.core.dto;

import br.com.postech.soat.product.core.domain.model.ProductId;
import java.util.UUID;

public class DeleteProductInput {
    private ProductId id;

    public DeleteProductInput(
        ProductId id
    ) {
        this.id = id;
    }

    public DeleteProductInput(
        UUID id
    ) {
        this.id = ProductId.of(id);
    }

    public ProductId getId() { return id; }
    public UUID getIdValue() { return id.getValue(); }
}
