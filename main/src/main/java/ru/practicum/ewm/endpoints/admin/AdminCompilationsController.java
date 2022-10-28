package ru.practicum.ewm.endpoints.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.endpoints.admin.service.AdminCompilationsService;
import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.model.dto.compilations.CompilationInDto;
import ru.practicum.ewm.model.dto.compilations.CompilationOutDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminCompilationsController {
    private final AdminCompilationsService adminCompilationsService;

    @PostMapping
    public CompilationOutDto addCompilation(@Valid @RequestBody CompilationInDto compilationInDto) {
        log.info("Admin addCompilation: {}", compilationInDto);
        return adminCompilationsService.addCompilation(compilationInDto);
    }

    @DeleteMapping("{compId}")
    public void removeCompilation(@Positive @PathVariable Long compId) throws CompilationNotFoundException {
        log.info("Admin removeCompilation: {}", compId);
        adminCompilationsService.removeCompilation(compId);
    }

    @DeleteMapping("{compId}/events/{eventId}")
    public void removeEventFromCompilation(
            @Positive @PathVariable Long compId,
            @Positive @PathVariable Long eventId
    ) throws CompilationNotFoundException {
        log.info("Admin removeEventFromCompilation: {},{}", compId, eventId);
        adminCompilationsService.removeEventFromCompilation(compId, eventId);
    }

    @PatchMapping("{compId}/events/{eventId}")
    public CompilationOutDto addEventToCompilation(
            @Positive @PathVariable Long compId,
            @Positive @PathVariable Long eventId
    ) throws CompilationNotFoundException, EventNotFoundException {
        log.info("Admin addEventToCompilation: {},{}", compId, eventId);
        return adminCompilationsService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("{compId}/pin")
    public void unPinCompilation(
            @Positive @PathVariable Long compId
    ) throws CompilationNotFoundException {
        log.info("Admin unPinCompilation: {}", compId);
        adminCompilationsService.unPinCompilation(compId);
    }

    @PatchMapping("{compId}/pin")
    public void pinCompilation(
            @Positive @PathVariable Long compId
    ) throws CompilationNotFoundException {
        log.info("Admin pinCompilation: {}", compId);
        adminCompilationsService.pinCompilation(compId);
    }

}
