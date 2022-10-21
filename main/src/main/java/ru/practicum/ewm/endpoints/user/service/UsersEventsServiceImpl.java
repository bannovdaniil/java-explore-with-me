package ru.practicum.ewm.endpoints.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.events.EventInDto;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.dto.events.EventState;
import ru.practicum.ewm.endpoints.admin.repository.AdminCategoriesRepository;
import ru.practicum.ewm.endpoints.admin.repository.AdminUsersRepository;
import ru.practicum.ewm.endpoints.user.repository.UsersEventsRepository;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.EventClosedException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Event;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersEventsServiceImpl implements UsersEventsService {
    private final UsersEventsRepository usersEventsRepository;
    private final AdminCategoriesRepository adminCategoriesRepository;
    private final AdminUsersRepository adminUsersRepository;

    @Override
    @Transactional
    public EventOutDto addEvent(Long userId, EventInDto eventInDto) throws CategoryNotFoundException, UserNotFoundException {
        if (!adminCategoriesRepository.existsById(eventInDto.getCategory())) {
            throw new CategoryNotFoundException("Category ID not found.");
        }
        if (!adminUsersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        if (eventInDto.getLocation() == null) {
            throw new InvalidParameterException("Location is null.");
        }
        if (eventInDto.getPaid() == null) {
            throw new InvalidParameterException("Paid is null.");
        }
        Event event = EventMapper.dtoInToEvent(eventInDto, adminCategoriesRepository.getReferenceById(eventInDto.getCategory()));
        event.setInitiator(adminUsersRepository.getReferenceById(userId));
        event.setState(EventState.PENDING);
        event.setConfirmedRequests(0);
        event.setViews(0L);
        return EventMapper.eventToOutDto(usersEventsRepository.saveAndFlush(event));
    }

    @Override
    @Transactional
    public EventOutDto updateEvent(Long userId, EventInDto eventInDto)
            throws CategoryNotFoundException, UserNotFoundException, EventNotFoundException, EventClosedException {
        if (!adminUsersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        Event event = usersEventsRepository.findById(eventInDto.getEventId()).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );

        if (event.getState() == EventState.PUBLISHED) {
            throw new EventClosedException("Event is published.");
        } else if (event.getState() == EventState.CANCELED) {
            event.setState(EventState.PENDING);
        }

        setNotNullParamToEntity(eventInDto, event);

        return EventMapper.eventToOutDto(usersEventsRepository.saveAndFlush(event));
    }

    private void setNotNullParamToEntity(EventInDto eventInDto, Event event)
            throws CategoryNotFoundException {
        if (eventInDto.getCategory() != null) {
            if (!adminCategoriesRepository.existsById(eventInDto.getCategory())) {
                throw new CategoryNotFoundException("New Category ID not found.");
            }
            event.setCategory(adminCategoriesRepository.getReferenceById(eventInDto.getCategory()));
        }
        if (eventInDto.getAnnotation() != null) {
            event.setAnnotation(eventInDto.getAnnotation());
        }
        if (eventInDto.getDescription() != null) {
            event.setDescription(eventInDto.getDescription());
        }
        if (eventInDto.getTitle() != null) {
            event.setTitle(eventInDto.getTitle());
        }
        if (eventInDto.getEventDate() != null) {
            event.setEventDate(eventInDto.getEventDate());
        }
        if (eventInDto.getPaid() != null) {
            event.setPaid(eventInDto.getPaid());
        }
        if (eventInDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventInDto.getParticipantLimit());
        }
        if (eventInDto.getRequestModeration() != null) {
            event.setRequestModeration(eventInDto.getRequestModeration());
        }
    }

    @Override
    public List<EventOutDto> findAllEvents(Long userId, Integer from, Integer size) throws UserNotFoundException {
        if (!adminUsersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        Sort sort = Sort.sort(Event.class).by(Event::getEventDate).descending();
        Pageable pageable = PageRequest.of(from / size, size, sort);
        return EventMapper.eventToListOutDto(usersEventsRepository.findAllByInitiatorId(userId, pageable));
    }

    @Override
    public EventOutDto getEvent(Long userId, Long eventId) throws UserNotFoundException, EventNotFoundException {
        if (!adminUsersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        Event event = usersEventsRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );

        return EventMapper.eventToOutDto(event);
    }

}
