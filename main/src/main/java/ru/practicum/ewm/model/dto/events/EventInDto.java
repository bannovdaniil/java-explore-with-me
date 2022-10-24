package ru.practicum.ewm.model.dto.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.model.dto.locations.LocationDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventInDto {
    private Long eventId;
    @Size(min = 20, max = 2000)
    private String annotation;
    @Positive
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    private LocationDto location;
    @Size(min = 3, max = 120)
    private String title;
    @NotNull
    @JsonFormat(pattern = Constants.DATE_TIME_STRING)
    private LocalDateTime eventDate;
    private Boolean paid;
    @Positive
    private Integer participantLimit;
    private Boolean requestModeration;

    public EventInDto(String annotation, Long category, String description, LocationDto location, String title, String eventDate, Boolean paid, Integer participantLimit, Boolean requestModeration) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.location = location;
        this.title = title;
        this.eventDate = LocalDateTime.parse(eventDate, Constants.DATE_TIME_SPACE);
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = LocalDateTime.parse(eventDate, Constants.DATE_TIME_SPACE);
    }
}
