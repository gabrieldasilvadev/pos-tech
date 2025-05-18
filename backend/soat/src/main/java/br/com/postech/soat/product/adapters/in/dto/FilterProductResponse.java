package br.com.postech.soat.product.adapters.in.dto;

import br.com.postech.soat.product.core.domain.Category;
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
public class FilterProductResponse {
    private UUID id;

    private String sku;

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean active;

    private String image;

    private Category category;
}