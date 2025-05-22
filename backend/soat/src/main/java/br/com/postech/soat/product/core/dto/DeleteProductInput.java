package br.com.postech.soat.product.core.dto;

import java.util.UUID;

public class DeleteProductInput {
    private UUID id;

    public DeleteProductInput (
        UUID id
    ) {
        this.id = id;
    }

    public UUID getId() { return id; }
}
