package ru.practicum.ewm.endpoints.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.endpoints.user.service.RequestNotFoundException;
import ru.practicum.ewm.endpoints.user.service.RequestsService;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.exception.UserRequestHimselfException;
import ru.practicum.ewm.model.dto.requests.RequestOutDto;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Validated
public class UsersRequestsController {
    private final RequestsService requestsService;

    @PostMapping
    public RequestOutDto addRequest(@Positive @PathVariable Long userId,
                                    @Positive @RequestParam(name = "eventId") Long eventId)
            throws UserNotFoundException, EventNotFoundException, UserRequestHimselfException {
        return requestsService.addRequest(userId, eventId);
    }

    @GetMapping
    public List<RequestOutDto> findAllRequests(@Positive @PathVariable Long userId)
            throws UserNotFoundException {
        return requestsService.findAllRequests(userId);
    }

    @PatchMapping("{requestId}/cancel")
    public RequestOutDto cancelRequest(@Positive @PathVariable Long userId,
                                       @Positive @PathVariable Long requestId)
            throws UserNotFoundException, RequestNotFoundException {
        return requestsService.cancelRequest(userId, requestId);
    }
}
