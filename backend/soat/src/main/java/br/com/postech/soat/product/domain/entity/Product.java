package br.com.postech.soat.product.domain.entity;

import br.com.postech.soat.commons.domain.AggregateRoot;
import br.com.postech.soat.product.domain.enumtypes.Category;
import java.math.BigDecimal;
import java.util.UUID;
import br.com.postech.soat.product.domain.valueobject.ProductCategory;
import br.com.postech.soat.product.domain.valueobject.ProductDescription;
import br.com.postech.soat.product.domain.valueobject.ProductImage;
import br.com.postech.soat.product.domain.valueobject.ProductName;
import br.com.postech.soat.product.domain.valueobject.ProductPrice;
import br.com.postech.soat.product.domain.valueobject.ProductSKU;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product extends AggregateRoot<ProductId> {
    private ProductSKU sku;
    private Boolean active;
    private ProductName name;
    private ProductPrice price;
    private ProductDescription description;
    private ProductImage image;
    private ProductCategory category;

    protected Product(ProductId productId) {
        super(productId);
    }

    @Builder
    protected Product(UUID productId, String sku, Boolean active, String name,
                      BigDecimal price, String description, String image, Category category) {
        super(new ProductId(productId));
        this.sku = new ProductSKU(sku);
        this.active = active;
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
        this.description = new ProductDescription(description);
        this.image = new ProductImage(image);
        this.category = new ProductCategory(category.toString());
    }

    public static Product create(ProductSKU sku, ProductName name, ProductPrice price,
                                 ProductDescription description, ProductImage image, ProductCategory category) {
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

    public void update(String name, BigDecimal price, String description, String image, String category) {
        if (name != null) this.name = new ProductName(name);
        if (price != null) this.price = new ProductPrice(price);
        if (description != null) this.description = new ProductDescription(description);
        if (image != null) this.image = new ProductImage(image);
        if (category != null) this.category = new ProductCategory(category);
    }
}