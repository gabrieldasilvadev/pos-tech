package br.com.postech.soat.product.core.ports.in;

import br.com.postech.soat.product.core.dto.UpdateProductInput;
import br.com.postech.soat.product.core.dto.UpdateProductOutput;
import java.util.UUID;

public interface UpdateProductUseCase {
    UpdateProductOutput update(UUID uuid, UpdateProductInput request);
}
