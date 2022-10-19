package ru.practicum.ewm;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateTimeFormatter DATE_TIME_SPACE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}