package br.com.postech.soat.product.core.dto;

import br.com.postech.soat.product.core.domain.model.ProductId;
import java.util.UUID;

public class CreateProductOutput {

    private ProductId id;
    private String message;

    public CreateProductOutput(
        ProductId id,
        String message
    ) {
        this.id = id;
        this.message = message;
    }

    public ProductId getId() { return id; }
    public UUID getIdValue() { return id.getValue(); }
    public String getMessage() { return message; }
}
