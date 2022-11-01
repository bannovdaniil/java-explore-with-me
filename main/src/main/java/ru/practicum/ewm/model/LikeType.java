package ru.practicum.ewm.model;

import java.util.Optional;

public enum LikeType {
    LIKE,
    DISLIKE;

    public static Optional<LikeType> from(String stringState) {
        for (LikeType state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
