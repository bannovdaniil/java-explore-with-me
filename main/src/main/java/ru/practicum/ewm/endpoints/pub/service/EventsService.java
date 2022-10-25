package ru.practicum.ewm.endpoints.pub.service;

import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.model.dto.events.EventOutDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventsService {
    EventOutDto findEventById(Long eventId, HttpServletRequest request) throws EventNotFoundException;

    List<EventOutDto> findAllEvents(String text,
                                    Long[] categories,
                                    Boolean paid,
                                    String rangeStart,
                                    String rangeEnd,
                                    Boolean onlyAvailable,
                                    String sort,
                                    Integer from,
                                    Integer size,
                                    HttpServletRequest request) throws EventNotFoundException;
}
