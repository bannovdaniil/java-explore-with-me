package ru.practicum.ewm;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.endpoints.admin.AdminCategoriesController;
import ru.practicum.ewm.endpoints.admin.AdminCompilationsController;
import ru.practicum.ewm.endpoints.admin.AdminEventsController;
import ru.practicum.ewm.endpoints.admin.AdminUsersController;
import ru.practicum.ewm.endpoints.pub.PublicCategoriesController;
import ru.practicum.ewm.endpoints.pub.PublicCompilationsController;
import ru.practicum.ewm.endpoints.pub.PublicEventsController;
import ru.practicum.ewm.endpoints.user.UsersEventsController;
import ru.practicum.ewm.endpoints.user.UsersEventsRequestsController;
import ru.practicum.ewm.endpoints.user.UsersRequestsController;
import ru.practicum.ewm.endpoints.user.service.RequestNotFoundException;
import ru.practicum.ewm.exception.*;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;

@RestControllerAdvice(
        assignableTypes = {
                AdminUsersController.class,
                AdminCategoriesController.class,
                AdminCompilationsController.class,
                AdminEventsController.class,
                UsersEventsController.class,
                UsersRequestsController.class,
                UsersEventsRequestsController.class,
                PublicCategoriesController.class,
                PublicCompilationsController.class,
                PublicEventsController.class
        })
public class ErrorHandler {
    private ErrorResponse errorResponse;

    @ExceptionHandler({
            CategoryNotFoundException.class,
            EventNotFoundException.class,
            UserNotFoundException.class,
            RequestNotFoundException.class,
            CompilationNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            InvalidParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(final Exception e) {
        return new ErrorResponse("Ошибка валидации данных: " + e.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            UserRequestHimselfException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleArgumentBadRequest(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            EventClosedException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDoubleData(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            AccessDeniedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllError(final Throwable e) {
        return new ErrorResponse("Произошла непредвиденная ошибка. " + e.getMessage());
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
