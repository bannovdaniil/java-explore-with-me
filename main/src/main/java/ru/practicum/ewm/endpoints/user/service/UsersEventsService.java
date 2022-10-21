package ru.practicum.ewm.endpoints.user.service;

import ru.practicum.ewm.dto.events.EventInDto;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.EventClosedException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;

import java.util.List;

public interface UsersEventsService {
    EventOutDto addEvent(Long userId, EventInDto eventInDto) throws CategoryNotFoundException, UserNotFoundException;

    EventOutDto updateEvent(Long userId, EventInDto eventInDto) throws CategoryNotFoundException, UserNotFoundException, EventNotFoundException, EventClosedException;

    List<EventOutDto> findAllEvents(Long userId, Integer from, Integer size) throws UserNotFoundException;

    EventOutDto getEvent(Long userId, Long eventId) throws UserNotFoundException, EventNotFoundException;
}
