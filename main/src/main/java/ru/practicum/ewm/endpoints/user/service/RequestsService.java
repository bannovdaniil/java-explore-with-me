package ru.practicum.ewm.endpoints.user.service;

import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.exception.UserRequestHimselfException;
import ru.practicum.ewm.model.dto.requests.RequestOutDto;

import java.util.List;

public interface RequestsService {
    RequestOutDto addRequest(Long userId, Long eventId) throws UserNotFoundException, EventNotFoundException, UserRequestHimselfException;

    List<RequestOutDto> findAllRequests(Long userId) throws UserNotFoundException;

    RequestOutDto cancelRequest(Long userId, Long requestId) throws UserNotFoundException, RequestNotFoundException;
}
