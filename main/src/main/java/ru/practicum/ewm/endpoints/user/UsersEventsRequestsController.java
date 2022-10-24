package ru.practicum.ewm.endpoints.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.endpoints.user.service.RequestNotFoundException;
import ru.practicum.ewm.endpoints.user.service.UsersEventsRequestsService;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserNotFoundException;
import ru.practicum.ewm.model.dto.requests.RequestOutDto;

import javax.validation.constraints.Positive;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events/{eventId}/requests")
@RequiredArgsConstructor
@Validated
public class UsersEventsRequestsController {
    private final UsersEventsRequestsService usersEventsRequestsService;

    @GetMapping
    public List<RequestOutDto> findAllEventRequests(@Positive @PathVariable Long userId,
                                                    @Positive @PathVariable Long eventId)
            throws UserNotFoundException, EventNotFoundException {
        return usersEventsRequestsService.findAllEventRequests(userId, eventId);
    }

    @PatchMapping("{requestId}/confirm")
    public RequestOutDto confirmRequest(@Positive @PathVariable Long userId,
                                        @Positive @PathVariable Long eventId,
                                        @Positive @PathVariable Long requestId)
            throws RequestNotFoundException, AccessDeniedException, UserNotFoundException {
        return usersEventsRequestsService.confirmRequest(userId, eventId, requestId);
    }

    @PatchMapping("{requestId}/reject")
    public RequestOutDto rejectRequest(@Positive @PathVariable Long userId,
                                       @Positive @PathVariable Long eventId,
                                       @Positive @PathVariable Long requestId)
            throws RequestNotFoundException, AccessDeniedException, UserNotFoundException {
        return usersEventsRequestsService.rejectRequest(userId, eventId, requestId);
    }
}
