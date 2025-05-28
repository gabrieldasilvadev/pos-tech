package br.com.postech.soat.product.core.domain;

import java.util.Arrays;

public enum Category {
    SNACK,
    DRINK,
    DESSERT,
    SIDE_DISH;

    public static Category entryOf(String value) {
        return Arrays.stream(Category.values()).map(Category::name)
            .filter(name -> name.equalsIgnoreCase(value))
            .findFirst()
            .map(Category::valueOf)
            .orElseThrow(() -> new IllegalArgumentException("Invalid category: " + value));
    }
}
