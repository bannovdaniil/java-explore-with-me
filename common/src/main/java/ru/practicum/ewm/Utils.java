package ru.practicum.ewm;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

public class Utils {
    public static void checkTimeBeforeOrThrow(LocalDateTime eventDate, int hours) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(hours))) {
            throw new InvalidParameterException("Event Time must be, late then " + hours + " hours.");
        }
    }
}
