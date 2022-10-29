package ru.practicum.ewm.dto.stats;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.Constants;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatInDto {
    @NotNull
    private String app;
    @NotNull
    private String uri;
    @NotNull
    private String ip;
    @NotNull
    @JsonFormat(pattern = Constants.DATE_TIME_STRING)
    private LocalDateTime timestamp;

    public StatInDto(String app, String uri, String ip, String timestamp) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = LocalDateTime.parse(timestamp, Constants.DATE_TIME_SPACE);
    }
}
