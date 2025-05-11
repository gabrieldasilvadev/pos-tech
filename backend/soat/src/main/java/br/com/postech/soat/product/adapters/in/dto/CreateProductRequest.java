package br.com.postech.soat.product.adapters.in.dto;

import br.com.postech.soat.product.core.domain.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "SKU é obrigatório")
    private String sku;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que 0")
    private BigDecimal price;

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @NotBlank(message = "Imagem é obrigatória")
    private String image;

    @NotNull(message = "Categoria é obrigatória")
    private Category category;
}
