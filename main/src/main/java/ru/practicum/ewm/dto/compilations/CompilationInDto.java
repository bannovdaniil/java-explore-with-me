package ru.practicum.ewm.dto.compilations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationInDto {
    private String title;
    private List<Long> events;
    private Boolean pinned;
}
