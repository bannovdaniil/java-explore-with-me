package ru.practicum.ewm.endpoints.pub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilations.CompilationOutDto;
import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.repository.CompilationsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationsServiceImpl implements CompilationsService {
    private final CompilationsRepository compilationsRepository;

    @Override
    public List<CompilationOutDto> findAllCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (pinned != null) {
            return CompilationMapper.compilationToListOutDto(compilationsRepository.findAlLByPinned(pinned, pageable));
        }
        return CompilationMapper.compilationToListOutDto(compilationsRepository.findAll(pageable).toList());
    }

    @Override
    public CompilationOutDto findCompilationById(Long compId) throws CompilationNotFoundException {
        return CompilationMapper.compilationToOutDto(compilationsRepository.findById(compId).orElseThrow(
                () -> new CompilationNotFoundException("Compilation ID was not found.")
        ));

    }
}
