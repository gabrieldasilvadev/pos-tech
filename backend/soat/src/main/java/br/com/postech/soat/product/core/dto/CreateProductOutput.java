package br.com.postech.soat.product.core.dto;

import java.util.UUID;

public class CreateProductOutput {

    private UUID id;
    private String message;

    public CreateProductOutput(
        UUID id,
        String message
    ) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() { return id; }
    public String getMessage() { return message; }
}
