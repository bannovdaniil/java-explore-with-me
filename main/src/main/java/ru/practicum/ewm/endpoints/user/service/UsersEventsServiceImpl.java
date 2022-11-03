package ru.practicum.ewm.endpoints.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.events.EventInDto;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.repository.*;
import ru.practicum.ewm.utils.Utils;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersEventsServiceImpl implements UsersEventsService {
    private final EventsRepository eventsRepository;
    private final CategoriesRepository categoriesRepository;
    private final UsersRepository usersRepository;
    private final LikeRepository likeRepository;
    private final RequestsRepository requestsRepository;

    @Override
    @Transactional
    public EventOutDto addEvent(Long userId, EventInDto eventInDto) throws CategoryNotFoundException, UserNotFoundException {
        if (!categoriesRepository.existsById(eventInDto.getCategory())) {
            throw new CategoryNotFoundException("Category ID not found.");
        }
        if (!usersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        if (eventInDto.getLocation() == null) {
            throw new InvalidParameterException("Location is null.");
        }
        if (eventInDto.getPaid() == null) {
            throw new InvalidParameterException("Paid is null.");
        }
        Utils.checkTimeBeforeOrThrow(eventInDto.getEventDate(), Constants.USER_TIME_HOUR_BEFORE_START);

        Event event = EventMapper.dtoInToEvent(eventInDto, categoriesRepository.getReferenceById(eventInDto.getCategory()));
        event.setInitiator(usersRepository.getReferenceById(userId));
        event.setState(EventState.PENDING);
        event.setConfirmedRequests(0);
        event.setViews(0L);
        return EventMapper.eventToOutDto(eventsRepository.saveAndFlush(event));
    }

    @Override
    @Transactional
    public EventOutDto updateEvent(Long userId, EventInDto eventInDto)
            throws CategoryNotFoundException, UserNotFoundException, EventNotFoundException, EventClosedException {
        if (!usersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        Event event = eventsRepository.findById(eventInDto.getEventId()).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );

        if (event.getState() == EventState.PUBLISHED) {
            throw new EventClosedException("Event is published.");
        } else if (event.getState() == EventState.CANCELED) {
            event.setState(EventState.PENDING);
        }

        if (eventInDto.getEventDate() != null) {
            event.setEventDate(eventInDto.getEventDate());
        }
        Utils.checkTimeBeforeOrThrow(event.getEventDate(), Constants.USER_TIME_HOUR_BEFORE_START);
        Utils.setNotNullParamToEntity(eventInDto, event, categoriesRepository);

        return EventMapper.eventToOutDto(eventsRepository.saveAndFlush(event));
    }

    @Override
    public List<EventOutDto> findAllEvents(Long userId, Integer from, Integer size) throws UserNotFoundException {
        if (!usersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        Sort sort = Sort.sort(Event.class).by(Event::getEventDate).descending();
        Pageable pageable = PageRequest.of(from / size, size, sort);
        return EventMapper.eventToListOutDto(eventsRepository.findAllByInitiatorId(userId, pageable));
    }

    @Override
    public EventOutDto getEvent(Long userId, Long eventId) throws UserNotFoundException, EventNotFoundException {
        if (!usersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );

        return EventMapper.eventToOutDto(event);
    }

    @Override
    public EventOutDto cancelEvent(Long userId, Long eventId) throws UserNotFoundException, EventNotFoundException, EventClosedException {
        if (!usersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
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
    public void addLike(Long userId, Long eventId, LikeType likeType)
            throws UserNotFoundException, EventNotFoundException, DoubleLikeException, LikeNotFoundException, AccessDeniedException {
        Event event = getEvent(eventId);
        checkUser(userId, event);
        if (!requestsRepository.existsByRequesterIdAndEventIdAndStatus(userId, eventId, RequestState.CONFIRMED)) {
            throw new AccessDeniedException("Запрещено оценивать событие в которых не участвуешь.");
        }

        Optional<Like> like = likeRepository.findByEventIdAndUserId(userId, eventId);
        if (like.isPresent()) {
            if (like.get().getType() != likeType) {
                LikeType deleteType = LikeType.LIKE;
                if (likeType == LikeType.LIKE) {
                    deleteType = LikeType.DISLIKE;
                }
                removeLike(userId, eventId, deleteType);
            } else {
                throw new DoubleLikeException("Можно поставить только один раз.");
            }
        }
        likeRepository.saveAndFlush(new Like(null, userId, eventId, likeType));
        if (likeType == LikeType.LIKE) {
            eventsRepository.incrementRate(eventId);
        } else {
            eventsRepository.decrementRate(eventId);
        }

        User initiator = event.getInitiator();
        initiator.setRate(getRate(initiator.getId()));
        usersRepository.save(initiator);
    }

    @Override
    @Transactional
    public void removeLike(Long userId, Long eventId, LikeType likeType)
            throws UserNotFoundException, EventNotFoundException, LikeNotFoundException, AccessDeniedException {
        Event event = getEvent(eventId);
        checkUser(userId, event);

        Like like = likeRepository.findByUserIdAndEventIdAndType(userId, eventId, likeType)
                .orElseThrow(
                        () -> new LikeNotFoundException(likeType + " not found.")
                );
        likeRepository.delete(like);

        if (likeType == LikeType.LIKE) {
            eventsRepository.decrementRate(eventId);
        } else {
            eventsRepository.incrementRate(eventId);
        }

        User initiator = event.getInitiator();
        initiator.setRate(getRate(initiator.getId()));
        usersRepository.save(initiator);
    }

    private Float getRate(Long userId) {
        int count = eventsRepository.countByInitiatorId(userId);
        long rate = eventsRepository.sumRateByInitiatorId(userId);

        return count == 0 ? 0.0F : (1.0F * rate / count);
    }

    private void checkUser(Long userId, Event event) throws UserNotFoundException, AccessDeniedException {
        if (!usersRepository.existsById(userId)) {
            throw new UserNotFoundException("User ID not found.");
        }
        if (userId.equals(event.getInitiator().getId())) {
            throw new AccessDeniedException("Запрещено оценивать собственное событие.");
        }
    }

    private Event getEvent(Long eventId) throws EventNotFoundException, AccessDeniedException {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );
        if (event.getState() != EventState.PUBLISHED) {
            throw new AccessDeniedException("Можно оценивать только опубликованные события.");
        }
        return event;
    }

}