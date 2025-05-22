package br.com.postech.soat.product.core.dto;

import br.com.postech.soat.product.core.domain.Category;
import java.math.BigDecimal;

public class CreateProductInput {
    private String sku;
    private String name;
    private BigDecimal price;
    private String description;
    private String image;
    private Category category;
    private Boolean active = true;

    public CreateProductInput(
        String sku,
        String name,
        BigDecimal price,
        String description,
        String image,
        Category category
    ) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
        this.active = true;
    }

    public String getSku() { return sku; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public Category getCategory() { return category; }
    public Boolean getActive() { return active; }
}
