package ru.practicum.ewm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.controller.StatsController;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;

@RestControllerAdvice(assignableTypes = {
        StatsController.class})
public class ErrorHandler {
    private ErrorResponse errorResponse;

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            InvalidParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(final Exception e) {
        return new ErrorResponse("Ошибка валидации данных: " + e.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBookingBadRequest(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllError(final Throwable e) {
        return new ErrorResponse("Произошла непредвиденная ошибка." + e.getMessage());
    }

    private static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
