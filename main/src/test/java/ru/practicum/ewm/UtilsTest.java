package ru.practicum.ewm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.practicum.ewm.utils.Utils;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

class UtilsTest {
    @DisplayName("Check Before Time or Throw")
    @ParameterizedTest
    @CsvSource({
            "62, 1, OK",
            "30, 1, ERROR",
            "0, 1, ERROR",
            "-30, 1, ERROR"
    })
    void checkTimeBeforeOrThrow(long timeToEventInMinutes, int timeLimitInHours, String expectedMessage) {
        LocalDateTime eventDate = LocalDateTime.now().plusMinutes(timeToEventInMinutes);
        if ("ERROR".equals(expectedMessage)) {

            final InvalidParameterException exception = Assertions.assertThrows(
                    InvalidParameterException.class,
                    () -> Utils.checkTimeBeforeOrThrow(eventDate, timeLimitInHours)
            );
            Assertions.assertEquals("Event Time must be, late then 1 hours.", exception.getMessage());
        } else {
            Utils.checkTimeBeforeOrThrow(eventDate, timeLimitInHours);
        }
    }
}