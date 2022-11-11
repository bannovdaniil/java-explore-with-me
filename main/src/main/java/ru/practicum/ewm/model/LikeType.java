package ru.practicum.ewm.model;

import java.util.Optional;

public enum LikeType {
    LIKE,
    DISLIKE;

    public static Optional<LikeType> from(String stringType) {
        for (LikeType type : values()) {
            if (type.name().equalsIgnoreCase(stringType)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
