package ru.practicum.ewm.model;

import java.util.Optional;

public enum SortType {
    EVENT_DATE,
    VIEWS,
    RATE;

    public static Optional<SortType> from(String stringType) {
        for (SortType type : values()) {
            if (type.name().equalsIgnoreCase(stringType)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
