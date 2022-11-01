package ru.practicum.ewm.endpoints.user.service;

import ru.practicum.ewm.dto.events.EventInDto;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.model.LikeType;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UsersEventsService {
    EventOutDto addEvent(Long userId, EventInDto eventInDto) throws CategoryNotFoundException, UserNotFoundException;

    EventOutDto updateEvent(Long userId, EventInDto eventInDto) throws CategoryNotFoundException, UserNotFoundException, EventNotFoundException, EventClosedException;

    List<EventOutDto> findAllEvents(Long userId, Integer from, Integer size) throws UserNotFoundException;

    EventOutDto getEvent(Long userId, Long eventId) throws UserNotFoundException, EventNotFoundException;

    EventOutDto cancelEvent(Long userId, Long eventId) throws UserNotFoundException, EventNotFoundException, EventClosedException;

    void addLike(Long userId, Long eventId, LikeType likeType) throws UserNotFoundException, EventNotFoundException, DoubleLikeException, LikeNotFoundException, AccessDeniedException;

    void removeLike(Long userId, Long eventId, LikeType likeType) throws UserNotFoundException, EventNotFoundException, LikeNotFoundException;
}
