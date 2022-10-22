package ru.practicum.ewm.endpoints.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.endpoints.admin.service.AdminEventsService;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
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
        return adminEventsService.findAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }
//
//    @PutMapping("{eventId}")
//    public EventOutDto updateEvent(@Positive @PathVariable Long userId, @Valid @RequestBody EventInDto eventInDto)
//            throws
//            CategoryNotFoundException,
//            UserNotFoundException,
//            EventNotFoundException,
//            EventClosedException {
//        return adminEventsService.updateEvent(userId, eventInDto);
//    }
//
//
//    @GetMapping("{eventId}")
//    public EventOutDto getEvent(@Positive @PathVariable Long userId,
//                                @Positive @PathVariable Long eventId)
//            throws UserNotFoundException, EventNotFoundException {
//        return adminEventsService.getEvent(userId, eventId);
//    }
//
//    @PatchMapping("{eventId}")
//    public EventOutDto cancelEvent(@Positive @PathVariable Long userId,
//                                   @Positive @PathVariable Long eventId)
//            throws UserNotFoundException, EventNotFoundException, EventClosedException {
//        return adminEventsService.cancelEvent(userId, eventId);
//    }
}
