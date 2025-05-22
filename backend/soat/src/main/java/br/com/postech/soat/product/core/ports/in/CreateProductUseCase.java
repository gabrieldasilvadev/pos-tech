package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.core.dto.CreateProductInput;
import br.com.postech.soat.product.core.dto.CreateProductOutput;

public interface CreateProductUseCase {
    CreateProductOutput create(CreateProductInput request);
}
