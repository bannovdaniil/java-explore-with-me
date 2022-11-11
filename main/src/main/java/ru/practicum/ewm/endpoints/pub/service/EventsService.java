package ru.practicum.ewm.endpoints.pub.service;

import ru.practicum.ewm.dto.events.EventPublicOutDto;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.model.SortType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventsService {
    EventPublicOutDto findEventById(Long eventId, HttpServletRequest request) throws EventNotFoundException;

    List<EventPublicOutDto> findAllEvents(String text,
                                          Long[] categories,
                                          Boolean paid,
                                          String rangeStart,
                                          String rangeEnd,
                                          Boolean onlyAvailable,
                                          SortType sortType,
                                          Integer from,
                                          Integer size,
                                          HttpServletRequest request);
}
