package ru.practicum.ewm.endpoints.admin.service;

import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.model.dto.compilations.CompilationInDto;
import ru.practicum.ewm.model.dto.compilations.CompilationOutDto;

public interface AdminCompilationsService {

    CompilationOutDto addCompilation(CompilationInDto compilationInDto);

    void removeCompilation(Long compId) throws CompilationNotFoundException;

    void removeEventFromCompilation(Long compId, Long eventId) throws CompilationNotFoundException;

    CompilationOutDto addEventToCompilation(Long compId, Long eventId) throws CompilationNotFoundException, EventNotFoundException;

    void pinCompilation(Long compId) throws CompilationNotFoundException;

    void unPinCompilation(Long compId) throws CompilationNotFoundException;
}
