package br.com.postech.soat.product.adapters.in.dto;

import br.com.postech.soat.product.core.domain.Category;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    private UUID id;

    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private String description;

    private String image;

    private Category category;

    private Boolean active;
}
