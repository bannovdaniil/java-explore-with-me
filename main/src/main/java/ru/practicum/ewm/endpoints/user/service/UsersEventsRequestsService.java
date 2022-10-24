package ru.practicum.ewm.endpoints.user.service;

import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.model.dto.requests.RequestOutDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UsersEventsRequestsService {
    RequestOutDto rejectRequest(Long userId, Long eventId, Long requestId) throws RequestNotFoundException, AccessDeniedException, UserNotFoundException;

    RequestOutDto confirmRequest(Long userId, Long eventId, Long requestId) throws UserNotFoundException, RequestNotFoundException, AccessDeniedException;

    List<RequestOutDto> findAllEventRequests(Long userId, Long eventId) throws UserNotFoundException, EventNotFoundException;
}
