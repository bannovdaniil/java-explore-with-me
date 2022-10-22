package ru.practicum.ewm.endpoints.admin.service;

import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;

import java.util.List;

public interface AdminEventsService {
    List<EventOutDto> findAllEvents(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) throws UserNotFoundException, CategoryNotFoundException;
}
