package ru.practicum.ewm.endpoints.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.exception.UserRequestHimselfException;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.model.dto.RequestState;
import ru.practicum.ewm.model.dto.requests.RequestOutDto;
import ru.practicum.ewm.repository.EventsRepository;
import ru.practicum.ewm.repository.RequestsRepository;
import ru.practicum.ewm.repository.UsersRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestsServiceImpl implements RequestsService {
    private final RequestsRepository requestsRepository;
    private final UsersRepository usersRepository;
    private final EventsRepository eventsRepository;

    @Override
    public RequestOutDto addRequest(Long userId, Long eventId)
            throws UserNotFoundException, EventNotFoundException, UserRequestHimselfException {

        User user = usersRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User ID not found.")
        );
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event ID not found.")
        );
        if (event.getInitiator().getId().equals(userId)) {
            throw new UserRequestHimselfException("User can't request himself.");
        }
        Request request = new Request(
                null,
                user,
                LocalDateTime.now(),
                RequestState.PENDING,
                event
        );

        return RequestMapper.requestToDto(requestsRepository.saveAndFlush(request));
    }
}
