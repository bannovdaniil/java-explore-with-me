package ru.practicum.ewm.endpoints.admin.service;

import ru.practicum.ewm.dto.events.EventInDto;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.EventClosedException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;

import java.util.List;

public interface AdminEventsService {
    List<EventOutDto> findAllEvents(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) throws UserNotFoundException, CategoryNotFoundException;

    EventOutDto publishEvent(Long eventId) throws EventClosedException, EventNotFoundException;

    EventOutDto rejectEvent(Long eventId) throws EventClosedException, EventNotFoundException;

    EventOutDto updateEvent(Long eventId, EventInDto eventInDto) throws EventNotFoundException, CategoryNotFoundException;
}
