package ru.practicum.ewm.endpoints.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.events.EventPublicOutDto;
import ru.practicum.ewm.endpoints.pub.service.EventsService;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.model.SortType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PublicEventsController {

    private final EventsService eventsService;

    @GetMapping
    public List<EventPublicOutDto> findAllEvents(
            @RequestParam(name = "text", defaultValue = "") String text,
            @RequestParam(name = "categories", required = false) Long[] categories,
            @RequestParam(name = "paid", required = false) Boolean paid,
            @RequestParam(name = "rangeStart", required = false) String rangeStart,
            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,
            @RequestParam(name = "sort", defaultValue = "EVENT_DATE", required = false) String sort,
            @PositiveOrZero
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive
            @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size,
            HttpServletRequest request) {
        log.info("Public findAllEvents: {},{},{},{},{},{},{},{},{},{}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
        SortType sortType = SortType.from(sort)
                .orElseThrow(() -> new IllegalArgumentException("Unknown type: " + sort));
        return eventsService.findAllEvents(
                text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sortType,
                from,
                size,
                request
        );
    }

    @GetMapping("{eventId}")
    public EventPublicOutDto findEventById(@Positive @PathVariable Long eventId, HttpServletRequest request) throws EventNotFoundException {
        log.info("Public findEventById: {},{}", eventId, request);
        return eventsService.findEventById(eventId, request);
    }
}
