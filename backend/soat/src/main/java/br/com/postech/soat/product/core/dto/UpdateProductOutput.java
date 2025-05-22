package br.com.postech.soat.product.core.dto;

import br.com.postech.soat.product.core.domain.Category;
import java.math.BigDecimal;
import java.util.UUID;

public class UpdateProductOutput {
    private UUID id;
    private String sku;
    private String name;
    private Boolean active;
    private BigDecimal price;
    private String description;
    private String image;
    private Category category;

    public UpdateProductOutput(
        UUID id,
        String sku,
        String name,
        BigDecimal price,
        String description,
        String image,
        Category category,
        Boolean active)
    {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
        this.active = active;
    }

    public UUID getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public Boolean getActive() { return active; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public Category getCategory() { return category; }
}