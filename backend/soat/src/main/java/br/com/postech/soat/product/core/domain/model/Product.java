package br.com.postech.soat.product.core.domain.model;

import br.com.postech.soat.commons.domain.AggregateRoot;
import br.com.postech.soat.product.core.domain.Category;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product extends AggregateRoot<ProductId> {
    private String sku;
    private Boolean active;
    private String name;
    private BigDecimal price;
    private String description;
    private String image;
    private Category category;

    protected Product(ProductId productId) {
        super(productId);
    }

    @Builder
    protected Product(UUID productId, String sku, Boolean active, String name,
                      BigDecimal price, String description, String image, Category category) {
        super(new ProductId(productId));
        this.sku = sku;
        this.active = active;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public static Product create(String sku, String name, BigDecimal price, 
                                String description, String image, Category category) {
        Product product = new Product(ProductId.generate());
        product.sku = sku;
        product.active = true;
        product.name = name;
        product.price = price;
        product.description = description;
        product.image = image;
        product.category = category;
        return product;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void update(String name, BigDecimal price, String description, String image, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }
}