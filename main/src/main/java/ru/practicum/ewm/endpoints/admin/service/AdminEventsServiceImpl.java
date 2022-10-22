package ru.practicum.ewm.endpoints.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.dto.events.EventState;
import ru.practicum.ewm.endpoints.admin.repository.AdminCategoriesRepository;
import ru.practicum.ewm.endpoints.admin.repository.AdminUsersRepository;
import ru.practicum.ewm.endpoints.user.repository.UsersEventsRepository;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventsServiceImpl implements AdminEventsService {
    private final UsersEventsRepository usersEventsRepository;
    private final AdminUsersRepository adminUsersRepository;
    private final AdminCategoriesRepository adminCategoriesRepository;

    @Override
    public List<EventOutDto> findAllEvents(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) throws UserNotFoundException, CategoryNotFoundException {
        checkUsersExitOrThrow(users);
        checkCategoriesExitOrThrow(categories);
        checkStatesCorrectOrThrow(states);

        LocalDateTime startDate = LocalDateTime.parse(rangeStart, Constants.DATE_TIME_SPACE);
        LocalDateTime endDate = LocalDateTime.parse(rangeEnd, Constants.DATE_TIME_SPACE);

        Sort sort = Sort.sort(Event.class).by(Event::getEventDate).descending();
        Pageable pageable = PageRequest.of(from / size, size, sort);
        List<Event> eventList = usersEventsRepository.findAllByUsersAndStatesAndCetegories(users, states, categories, startDate, endDate, pageable);
        return EventMapper.eventToListOutDto(eventList);
    }

    private void checkStatesCorrectOrThrow(String[] states) {
        for (String state : states) {
            try {
                EventState check = EventState.valueOf(state);
            } catch (IllegalArgumentException err) {
                throw new IllegalArgumentException("Stats: " + state + " not found.");
            }
        }
    }

    private void checkCategoriesExitOrThrow(Long[] categories) throws CategoryNotFoundException {
        for (Long catId : categories) {
            if (!adminCategoriesRepository.existsById(catId)) {
                throw new CategoryNotFoundException("Category ID: " + catId + " not found.");
            }
        }
    }

    private void checkUsersExitOrThrow(Long[] users) throws UserNotFoundException {
        for (Long userId : users) {
            if (!adminUsersRepository.existsById(userId)) {
                throw new UserNotFoundException("User ID: " + userId + " not found.");
            }
        }
    }
}
