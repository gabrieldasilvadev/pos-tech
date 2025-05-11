package br.com.postech.soat.product.core.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID id;
    private String sku;
    private Boolean active;
    private String name;
    private BigDecimal price;
    private String description;
    private String image;
    private Category category;

    public Product() {}

    public Product(
        UUID id,
        String sku,
        Boolean active,
        String name,
        BigDecimal price,
        String description,
        String image,
        Category category
    ) {
        this.id = id;
        this.sku = sku;
        this.active = active;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public Product(
        UUID id,
        String sku,
        String name,
        BigDecimal price,
        String description,
        String image,
        Category category
    ) {
        this(id, sku, true, name, price, description, image, category);
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
