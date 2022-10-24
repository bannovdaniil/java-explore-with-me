package ru.practicum.ewm.endpoints.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.compilations.CompilationInDto;
import ru.practicum.ewm.model.dto.compilations.CompilationOutDto;
import ru.practicum.ewm.repository.CompilationsRepository;
import ru.practicum.ewm.repository.EventsRepository;

@Service
@RequiredArgsConstructor
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    private final EventsRepository eventsRepository;
    private final CompilationsRepository compilationsRepository;

    @Override
    @Transactional
    public CompilationOutDto addCompilation(CompilationInDto compilationInDto) {
        Compilation compilation = CompilationMapper.dtoToCompilation(
                compilationInDto, eventsRepository.findAllById(compilationInDto.getEvents())
        );

        return CompilationMapper.compilationToOutDto(compilationsRepository.saveAndFlush(compilation));
    }

    @Override
    public void removeCompilation(Long compId) throws CompilationNotFoundException {
        if (!compilationsRepository.existsById(compId)) {
            throw new CompilationNotFoundException("Compilation ID not found.");
        }
        compilationsRepository.deleteById(compId);
        compilationsRepository.flush();
    }

    @Override
    @Transactional
    public void removeEventFromCompilation(Long compId, Long eventId) throws CompilationNotFoundException {
        Compilation compilation = compilationsRepository.findById(compId).orElseThrow(
                () -> new CompilationNotFoundException("Compilation ID not found.")
        );
        compilation.getEvents().removeIf((e) -> e.getId().equals(eventId));
        compilationsRepository.flush();
    }

    @Override
    public CompilationOutDto addEventToCompilation(Long compId, Long eventId) throws CompilationNotFoundException, EventNotFoundException {
        Compilation compilation = compilationsRepository.findById(compId).orElseThrow(
                () -> new CompilationNotFoundException("Compilation ID not found.")
        );
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event ID: " + eventId + " not found.")
        );
        compilation.getEvents().add(event);
        compilationsRepository.flush();

        return CompilationMapper.compilationToOutDto(compilation);
    }

    @Override
    public void pinCompilation(Long compId) throws CompilationNotFoundException {
        setPin(compId, true);
    }


    @Override
    public void unPinCompilation(Long compId) throws CompilationNotFoundException {
        setPin(compId, false);
    }

    private void setPin(Long compId, boolean pinned) throws CompilationNotFoundException {
        Compilation compilation = compilationsRepository.findById(compId).orElseThrow(
                () -> new CompilationNotFoundException("Compilation ID not found.")
        );
        compilation.setPinned(pinned);
        compilationsRepository.flush();
    }

}
