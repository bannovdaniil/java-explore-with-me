package ru.practicum.ewm.endpoints.pub.service;

import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.model.dto.compilations.CompilationOutDto;

import java.util.List;

public interface CompilationsService {
    List<CompilationOutDto> findAllCompilations(Boolean pinned, Integer from, Integer size);

    CompilationOutDto findCompilationById(Long compId) throws CompilationNotFoundException;
}
