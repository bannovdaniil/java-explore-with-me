package ru.practicum.ewm.endpoints.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.events.EventInDto;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.endpoints.user.service.UsersEventsService;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.model.LikeType;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UsersEventsController {
    private final UsersEventsService usersEventsService;

    @PostMapping
    public EventOutDto addEvent(@Positive @PathVariable Long userId, @Valid @RequestBody EventInDto eventInDto)
            throws CategoryNotFoundException, UserNotFoundException {
        log.info("User addEvent: {},{}", userId, eventInDto);
        return usersEventsService.addEvent(userId, eventInDto);
    }

    @PatchMapping
    public EventOutDto updateEvent(@Positive @PathVariable Long userId, @Valid @RequestBody EventInDto eventInDto)
            throws
            CategoryNotFoundException,
            UserNotFoundException,
            EventNotFoundException,
            EventClosedException {
        log.info("User updateEvent: {},{}", userId, eventInDto);
        return usersEventsService.updateEvent(userId, eventInDto);
    }

    @GetMapping
    public List<EventOutDto> findAllEvents(@Positive @PathVariable Long userId,
                                           @PositiveOrZero
                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive
                                           @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size)
            throws UserNotFoundException {
        log.info("User findAllEvents: {},{},{}", userId, from, size);
        return usersEventsService.findAllEvents(userId, from, size);
    }

    @GetMapping("{eventId}")
    public EventOutDto getEvent(@Positive @PathVariable Long userId,
                                @Positive @PathVariable Long eventId)
            throws UserNotFoundException, EventNotFoundException {
        log.info("User getEvent: {},{}", userId, eventId);
        return usersEventsService.getEvent(userId, eventId);
    }

    @PatchMapping("{eventId}")
    public EventOutDto cancelEvent(@Positive @PathVariable Long userId,
                                   @Positive @PathVariable Long eventId)
            throws UserNotFoundException, EventNotFoundException, EventClosedException {
        log.info("User cancelEvent: {},{}", userId, eventId);
        return usersEventsService.cancelEvent(userId, eventId);
    }

    @PutMapping("/{eventId}/like")
    public void addLike(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId,
            @RequestParam(name = "type") String type
    ) throws UserNotFoundException,
            EventNotFoundException,
            DoubleLikeException,
            LikeNotFoundException,
            AccessDeniedException {
        LikeType likeType = LikeType.from(type)
                .orElseThrow(() -> new IllegalArgumentException("Unknown type: " + type));
        usersEventsService.addLike(userId, eventId, likeType);
    }

    @DeleteMapping("/{eventId}/like")
    public void removeLike(
            @Positive @PathVariable Long userId,
            @Positive @PathVariable Long eventId,
            @RequestParam(name = "type") String type
    ) throws UserNotFoundException, EventNotFoundException, LikeNotFoundException {
        LikeType likeType = LikeType.from(type)
                .orElseThrow(() -> new IllegalArgumentException("Unknown type: " + type));
        usersEventsService.removeLike(userId, eventId, likeType);
    }

}
