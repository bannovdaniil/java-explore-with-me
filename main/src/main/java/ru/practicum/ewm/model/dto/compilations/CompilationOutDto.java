package ru.practicum.ewm.model.dto.compilations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.model.dto.events.EventOutDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationOutDto {
    private Long id;
    private String title;
    private List<EventOutDto> events;
    private Boolean pinned;
}
