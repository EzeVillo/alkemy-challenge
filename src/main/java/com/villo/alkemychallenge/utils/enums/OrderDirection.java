package com.villo.alkemychallenge.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderDirection {
    ASC("ASC"),
    DESC("DESC");

    public static OrderDirection fromValue(String value) {
        for (OrderDirection direction : OrderDirection.values()) {
            if (direction.value.equalsIgnoreCase(value)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    private final String value;
}
