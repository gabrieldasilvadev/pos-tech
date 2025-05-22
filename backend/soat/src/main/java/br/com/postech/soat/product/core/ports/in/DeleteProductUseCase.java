package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.core.dto.DeleteProductInput;

public interface DeleteProductUseCase {
    void delete (DeleteProductInput id);
}
