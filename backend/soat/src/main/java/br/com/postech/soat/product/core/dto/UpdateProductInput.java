package br.com.postech.soat.product.core.dto;

import br.com.postech.soat.product.core.domain.Category;

import java.math.BigDecimal;
import java.util.Objects;

public class UpdateProductInput {

    private String name;
    private BigDecimal price;
    private String description;
    private String image;
    private Category category;

    public UpdateProductInput() {
    }

    public UpdateProductInput(String name, BigDecimal price, String description, String image, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    // Getters e setters

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateProductInput)) return false;
        UpdateProductInput that = (UpdateProductInput) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(price, that.price) &&
            Objects.equals(description, that.description) &&
            Objects.equals(image, that.image) &&
            Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, description, image, category);
    }

    @Override
    public String toString() {
        return "UpdateProductInput{" +
            "name='" + name + '\'' +
            ", price=" + price +
            ", description='" + description + '\'' +
            ", image='" + image + '\'' +
            ", category=" + category +
            '}';
    }
}