package ru.practicum.ewm.endpoints.admin.service;

import ru.practicum.ewm.dto.compilations.CompilationInDto;
import ru.practicum.ewm.dto.compilations.CompilationOutDto;
import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.exception.EventNotFoundException;

public interface AdminCompilationsService {

    CompilationOutDto addCompilation(CompilationInDto compilationInDto);

    void removeCompilation(Long compId) throws CompilationNotFoundException;

    void removeEventFromCompilation(Long compId, Long eventId) throws CompilationNotFoundException;

    CompilationOutDto addEventToCompilation(Long compId, Long eventId) throws CompilationNotFoundException, EventNotFoundException;

    void pinCompilation(Long compId) throws CompilationNotFoundException;

    void unPinCompilation(Long compId) throws CompilationNotFoundException;
}
