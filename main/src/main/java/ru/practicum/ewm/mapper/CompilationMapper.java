package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.compilations.CompilationInDto;
import ru.practicum.ewm.dto.compilations.CompilationOutDto;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;

import java.util.List;

public class CompilationMapper {
    public static Compilation dtoToCompilation(CompilationInDto compilationInDto, List<Event> events) {
        return new Compilation(
                null,
                compilationInDto.getTitle(),
                compilationInDto.getPinned(),
                events
        );
    }

    public static CompilationOutDto compilationToOutDto(Compilation compilation) {
        return new CompilationOutDto(
                compilation.getId(),
                compilation.getTitle(),
                EventMapper.eventToListOutDto(compilation.getEvents()),
                compilation.getPinned()
        );
    }
}
