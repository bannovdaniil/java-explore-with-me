package ru.practicum.ewm.endpoints.user.service;

public class RequestNotFoundException extends Exception {

    public RequestNotFoundException(String message) {
        super(message);
    }
}
