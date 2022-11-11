package ru.practicum.ewm.endpoints.pub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.MainAPI;
import ru.practicum.ewm.dto.events.EventPublicOutDto;
import ru.practicum.ewm.dto.stats.StatInDto;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.EventState;
import ru.practicum.ewm.model.SortType;
import ru.practicum.ewm.repository.EventsRepository;
import ru.practicum.ewm.utils.AdminStatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventsServiceImpl implements EventsService {
    private final EventsRepository eventsRepository;
    private final AdminStatsClient adminStatsClient;

    @Override
    public EventPublicOutDto findEventById(Long eventId, HttpServletRequest request) throws EventNotFoundException {
        if (!eventsRepository.existsByIdAndState(eventId, EventState.PUBLISHED)) {
            throw new EventNotFoundException("Event not found.");
        }
        Event event = eventsRepository.findByIdAndState(eventId, EventState.PUBLISHED).orElseThrow(
                () -> new EventNotFoundException("Event not found.")
        );
        eventsRepository.incrementViews(eventId);

        Thread sendHit = new Thread(
                () -> {
                    try {
                        adminStatsClient.saveHit(new StatInDto(
                                MainAPI.APP_NAME,
                                request.getRequestURI(),
                                request.getRemoteAddr(),
                                LocalDateTime.now()
                        ));
                        log.info(">>Hit send - OK.");
                    } catch (Exception err) {
                        log.info(">>Hit send. Error: " + err.getMessage());
                    }
                });
        sendHit.start();
        return EventMapper.eventToPublicOutDto(event);
    }

    @Override
    public List<EventPublicOutDto> findAllEvents(String text,
                                                 Long[] categories,
                                                 Boolean paid,
                                                 String rangeStart,
                                                 String rangeEnd,
                                                 Boolean onlyAvailable,
                                                 SortType sortType,
                                                 Integer from,
                                                 Integer size, HttpServletRequest request) {

        Sort sort;
        switch (sortType) {
            case EVENT_DATE:
                sort = Sort.sort(Event.class).by(Event::getEventDate).ascending();
                break;
            case VIEWS:
                sort = Sort.sort(Event.class).by(Event::getViews).descending();
                break;
            case RATE:
                sort = Sort.sort(Event.class).by(Event::getRate).descending();
                break;
            default:
                throw new IllegalArgumentException("Указан не существующий тип сортировки.");
        }

        Pageable pageable = PageRequest.of(from / size, size, sort);

        LocalDateTime startDate;
        if (rangeStart != null) {
            startDate = LocalDateTime.parse(rangeStart, Constants.DATE_TIME_SPACE);
        } else {
            startDate = LocalDateTime.now();
        }
        LocalDateTime endDate;
        if (rangeStart != null) {
            endDate = LocalDateTime.parse(rangeEnd, Constants.DATE_TIME_SPACE);
        } else {
            endDate = LocalDateTime.now();
        }

        onlyAvailable = true;
        List<Event> events = eventsRepository.findAllByParam(
                text,
                categories,
                paid,
                startDate,
                endDate,
                onlyAvailable,
                pageable);

        Thread sendHit = new Thread(
                () -> {
                    try {
                        adminStatsClient.saveHit(new StatInDto(
                                MainAPI.APP_NAME,
                                request.getRequestURI(),
                                request.getRemoteAddr(),
                                LocalDateTime.now()
                        ));
                        log.info(">>Hit search send - OK.");
                    } catch (Exception err) {
                        log.info(">>Hit search send. Error: " + err.getMessage());
                    }
                });
        sendHit.start();

        return EventMapper.eventToPublicListOutDto(events);
    }
}
