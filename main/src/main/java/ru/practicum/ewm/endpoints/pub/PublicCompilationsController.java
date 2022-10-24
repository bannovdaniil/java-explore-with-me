package ru.practicum.ewm.endpoints.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.endpoints.pub.service.CompilationsService;
import ru.practicum.ewm.exception.CompilationNotFoundException;
import ru.practicum.ewm.model.dto.compilations.CompilationOutDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Validated
public class PublicCompilationsController {

    private final CompilationsService compilationsService;

    @GetMapping
    public List<CompilationOutDto> findAllCategories(
            @RequestParam(name = "pinned", required = false) Boolean pinned,
            @PositiveOrZero
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive
            @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size) {
        return compilationsService.findAllCompilations(pinned, from, size);
    }

    @GetMapping("{compId}")
    public CompilationOutDto findCategoriesById(@Positive @PathVariable Long compId) throws CompilationNotFoundException {
        return compilationsService.findCompilationById(compId);
    }
}
