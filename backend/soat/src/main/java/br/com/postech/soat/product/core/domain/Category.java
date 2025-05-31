package br.com.postech.soat.product.core.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;

public enum Category {
    SNACK,
    DRINK,
    DESSERT,
    SIDE_DISH;

    public static Category entryOf(String value) {
        if (value == null) return null;
        return Arrays.stream(Category.values()).map(Category::name)
            .filter(name -> name.equalsIgnoreCase(value))
            .findFirst()
            .map(Category::valueOf)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid category - " + value));
    }
}
