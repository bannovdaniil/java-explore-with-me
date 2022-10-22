package ru.practicum.ewm.endpoints.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.Utils;
import ru.practicum.ewm.dto.events.EventInDto;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.dto.events.EventState;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.EventClosedException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.repository.CategoriesRepository;
import ru.practicum.ewm.repository.EventsRepository;
import ru.practicum.ewm.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventsServiceImpl implements AdminEventsService {
    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;

    @Override
    public List<EventOutDto> findAllEvents(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) throws UserNotFoundException, CategoryNotFoundException {
        checkUsersExitOrThrow(users);
        checkCategoriesExitOrThrow(categories);
        List<EventState> stateList = checkStatesCorrectOrThrow(states);

        LocalDateTime startDate = LocalDateTime.parse(rangeStart, Constants.DATE_TIME_SPACE);
        LocalDateTime endDate = LocalDateTime.parse(rangeEnd, Constants.DATE_TIME_SPACE);

        Sort sort = Sort.sort(Event.class).by(Event::getEventDate).descending();
        Pageable pageable = PageRequest.of(from / size, size, sort);
        List<Event> eventList = eventsRepository.findAllByUsersAndStatesAndCetegories(users, stateList, categories, startDate, endDate, pageable);
        return EventMapper.eventToListOutDto(eventList);
    }

    private List<EventState> checkStatesCorrectOrThrow(String[] states) {
        List<EventState> stateList = new ArrayList<>();
        for (String state : states) {
            try {
                stateList.add(EventState.valueOf(state));
            } catch (IllegalArgumentException err) {
                throw new IllegalArgumentException("Stats: " + state + " not found.");
            }
        }
        return stateList;
    }

    private void checkCategoriesExitOrThrow(Long[] categories) throws CategoryNotFoundException {
        for (Long catId : categories) {
            if (!categoriesRepository.existsById(catId)) {
                throw new CategoryNotFoundException("Category ID: " + catId + " not found.");
            }
        }
    }

    private void checkUsersExitOrThrow(Long[] users) throws UserNotFoundException {
        for (Long userId : users) {
            if (!usersRepository.existsById(userId)) {
                throw new UserNotFoundException("User ID: " + userId + " not found.");
            }
        }
    }

    @Override
    public EventOutDto publishEvent(Long eventId) throws EventClosedException, EventNotFoundException {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );
        if (event.getState() != EventState.PENDING) {
            throw new EventClosedException("Event is not pending.");
        }
        Utils.checkTimeBeforeOrThrow(event.getEventDate(), Constants.ADMIN_TIME_HOUR_BEFORE_START);
        event.setPublishedOn(LocalDateTime.now());
        event.setState(EventState.PUBLISHED);
        return EventMapper.eventToOutDto(eventsRepository.saveAndFlush(event));
    }

    @Override
    public EventOutDto rejectEvent(Long eventId) throws EventClosedException, EventNotFoundException {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );
        if (event.getState() != EventState.PENDING) {
            throw new EventClosedException("Event is not pending.");
        }
        event.setState(EventState.CANCELED);
        return EventMapper.eventToOutDto(eventsRepository.saveAndFlush(event));
    }

    @Override
    @Transactional
    public EventOutDto updateEvent(Long eventId, EventInDto eventInDto) throws EventNotFoundException, CategoryNotFoundException {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );
        if (eventInDto.getEventDate() != null) {
            event.setEventDate(eventInDto.getEventDate());
        }
        Utils.setNotNullParamToEntity(eventInDto, event, categoriesRepository);

        return EventMapper.eventToOutDto(eventsRepository.saveAndFlush(event));
    }
}
