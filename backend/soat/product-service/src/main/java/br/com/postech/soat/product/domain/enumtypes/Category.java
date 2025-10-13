package br.com.postech.soat.product.domain.enumtypes;

import java.util.Arrays;

public enum Category {
    SNACK,
    DRINK,
    DESSERT,
    SIDE_DISH;

    public static Category entryOf(String category) {
        if (category == null) return null;
        return Arrays.stream(Category.values()).map(Category::name)
            .filter(name -> name.equalsIgnoreCase(category))
            .findFirst()
            .map(Category::valueOf)
            .orElseThrow(() -> new IllegalArgumentException("Invalid category: " + category));
    }
}
