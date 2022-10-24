package ru.practicum.ewm.endpoints.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.endpoints.user.service.UsersEventsService;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.EventClosedException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.model.dto.events.EventInDto;
import ru.practicum.ewm.model.dto.events.EventOutDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
public class UsersEventsController {
    private final UsersEventsService usersEventsService;

    @PostMapping
    public EventOutDto addEvent(@Positive @PathVariable Long userId, @Valid @RequestBody EventInDto eventInDto)
            throws CategoryNotFoundException, UserNotFoundException {
        return usersEventsService.addEvent(userId, eventInDto);
    }

    @PatchMapping
    public EventOutDto updateEvent(@Positive @PathVariable Long userId, @Valid @RequestBody EventInDto eventInDto)
            throws
            CategoryNotFoundException,
            UserNotFoundException,
            EventNotFoundException,
            EventClosedException {
        return usersEventsService.updateEvent(userId, eventInDto);
    }

    @GetMapping
    public List<EventOutDto> findAllEvents(@Positive @PathVariable Long userId,
                                           @PositiveOrZero
                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive
                                           @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size)
            throws UserNotFoundException {
        return usersEventsService.findAllEvents(userId, from, size);
    }

    @GetMapping("{eventId}")
    public EventOutDto getEvent(@Positive @PathVariable Long userId,
                                @Positive @PathVariable Long eventId)
            throws UserNotFoundException, EventNotFoundException {
        return usersEventsService.getEvent(userId, eventId);
    }

    @PatchMapping("{eventId}")
    public EventOutDto cancelEvent(@Positive @PathVariable Long userId,
                                   @Positive @PathVariable Long eventId)
            throws UserNotFoundException, EventNotFoundException, EventClosedException {
        return usersEventsService.cancelEvent(userId, eventId);
    }
}
