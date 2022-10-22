package ru.practicum.ewm.dto.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.dto.locations.LocationDto;
import ru.practicum.ewm.dto.users.UserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventOutDto {
    private String annotation;
    private CategoryFullDto category;
    private UserDto initiator;
    private LocationDto location;
    private String title;
    private Integer confirmedRequests;
    @JsonFormat(pattern = Constants.DATE_TIME_STRING)
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(pattern = Constants.DATE_TIME_STRING)
    private LocalDateTime eventDate;
    private Long id;
    private Boolean paid;
    private Integer participantLimit;
    @JsonFormat(pattern = Constants.DATE_TIME_STRING)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventState state;
    private Long views;
}
