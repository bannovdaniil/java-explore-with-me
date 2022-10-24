package ru.practicum.ewm.model.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.model.RequestState;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestOutDto {
    private Long id;
    @JsonFormat(pattern = Constants.DATE_TIME_STRING)
    private LocalDateTime created;
    @Positive
    private Long event;
    @Positive
    private Long requester;
    private RequestState status;
}
