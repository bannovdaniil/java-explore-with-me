package ru.practicum.ewm.endpoints.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.events.EventInDto;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.endpoints.admin.service.AdminEventsService;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.EventClosedException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminEventsController {
    private final AdminEventsService adminEventsService;

    @GetMapping
    public List<EventOutDto> findAllEvents(
            @RequestParam(value = "users") Long[] users,
            @RequestParam(value = "states") String[] states,
            @RequestParam(value = "categories") Long[] categories,
            @RequestParam(name = "rangeStart") String rangeStart,
            @RequestParam(name = "rangeEnd") String rangeEnd,
            @PositiveOrZero
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive
            @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size)
            throws UserNotFoundException, CategoryNotFoundException {
        log.info("Admin findAllEvents: {},{},{},{},{},{},{}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return adminEventsService.findAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("{eventId}/publish")
    public EventOutDto publishEvent(@Positive @PathVariable Long eventId)
            throws EventNotFoundException, EventClosedException {
        log.info("Admin publishEvent: {}", eventId);
        return adminEventsService.publishEvent(eventId);
    }

    @PatchMapping("{eventId}/reject")
    public EventOutDto rejectEvent(@Positive @PathVariable Long eventId)
            throws EventNotFoundException, EventClosedException {
        log.info("Admin Patch rejectEvent: {}", eventId);
        return adminEventsService.rejectEvent(eventId);
    }

    @PutMapping("{eventId}")
    public EventOutDto updateEvent(@Positive @PathVariable Long eventId,
                                   @Valid @RequestBody EventInDto eventInDto)
            throws EventNotFoundException, CategoryNotFoundException {
        log.info("Admin Put updateEvent: {},{}", eventId, eventInDto);
        return adminEventsService.updateEvent(eventId, eventInDto);
    }
}
