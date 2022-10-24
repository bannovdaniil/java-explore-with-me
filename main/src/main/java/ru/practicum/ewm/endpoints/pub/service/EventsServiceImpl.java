package ru.practicum.ewm.endpoints.pub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.EventState;
import ru.practicum.ewm.model.dto.events.EventOutDto;
import ru.practicum.ewm.repository.EventsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {
    private final EventsRepository eventsRepository;

    @Override
    public EventOutDto findEventById(Long eventId) throws EventNotFoundException {
        if (!eventsRepository.existsByIdAndState(eventId, EventState.PUBLISHED)) {
            throw new EventNotFoundException("Event not found.");
        }
        //TODO Save to stats Service!
        eventsRepository.incrementViews(eventId);
        Event event = eventsRepository.findByIdAndState(eventId, EventState.PUBLISHED).orElseThrow(
                () -> new EventNotFoundException("Event not found.")
        );

        return EventMapper.eventToOutDto(event);
    }

    @Override
    public List<EventOutDto> findAllEvents(String text,
                                           Long[] categories,
                                           Boolean paid,
                                           String rangeStart,
                                           String rangeEnd,
                                           Boolean onlyAvailable,
                                           String sortType,
                                           Integer from,
                                           Integer size) throws EventNotFoundException {
        Sort sort = "EVENT_DATE".equals(sortType) ?
                Sort.sort(Event.class).by(Event::getEventDate).ascending() :
                Sort.sort(Event.class).by(Event::getViews).descending();
        Pageable pageable = PageRequest.of(from / size, size, sort);

        onlyAvailable = true;

        List<Event> events = eventsRepository.findAllByParam(
                text,
                categories,
                paid,
                LocalDateTime.parse(rangeStart, Constants.DATE_TIME_SPACE),
                LocalDateTime.parse(rangeEnd, Constants.DATE_TIME_SPACE),
                onlyAvailable,
                pageable);
        if (events.size() == 0) {
            throw new EventNotFoundException("Not found any events for your parameters.");
        }

        return EventMapper.eventToListOutDto(events);
    }
}
