package ru.practicum.ewm.endpoints.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilations.CompilationInDto;
import ru.practicum.ewm.dto.compilations.CompilationOutDto;
import ru.practicum.ewm.endpoints.admin.service.AdminCompilationsService;
import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.exception.EventNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
public class AdminCompilationsController {
    private final AdminCompilationsService adminCompilationsService;

    @PostMapping
    public CompilationOutDto addCompilation(@Valid @RequestBody CompilationInDto compilationInDto) {
        return adminCompilationsService.addCompilation(compilationInDto);
    }

    @DeleteMapping("{compId}")
    public void removeCompilation(@Positive @PathVariable Long compId) throws CompilationNotFoundException {
        adminCompilationsService.removeCompilation(compId);
    }

    @DeleteMapping("{compId}/events/{eventId}")
    public void removeEventFromCompilation(
            @Positive @PathVariable Long compId,
            @Positive @PathVariable Long eventId
    ) throws CompilationNotFoundException {
        adminCompilationsService.removeEventFromCompilation(compId, eventId);
    }

    @PatchMapping("{compId}/events/{eventId}")
    public CompilationOutDto addEventToCompilation(
            @Positive @PathVariable Long compId,
            @Positive @PathVariable Long eventId
    ) throws CompilationNotFoundException, EventNotFoundException {
        return adminCompilationsService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("{compId}/pin")
    public void unPinCompilation(
            @Positive @PathVariable Long compId
    ) throws CompilationNotFoundException {
        adminCompilationsService.unPinCompilation(compId);
    }

    @PatchMapping("{compId}/pin")
    public void pinCompilation(
            @Positive @PathVariable Long compId
    ) throws CompilationNotFoundException {
        adminCompilationsService.pinCompilation(compId);
    }

}
