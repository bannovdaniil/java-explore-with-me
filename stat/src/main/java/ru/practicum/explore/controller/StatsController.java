package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.StatInDto;
import ru.practicum.explore.dto.StatOutDto;
import ru.practicum.explore.service.StatsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public void saveHit(@Valid @RequestBody StatInDto statInDto) {
        statsService.saveHit(statInDto);
    }

    @GetMapping("/stats")
    public List<StatOutDto> getHits(@NotNull @RequestParam(name = "start", required = true) String start,
                                    @NotNull @RequestParam(name = "end", required = true) String end,
                                    @Valid @RequestParam(name = "uris", defaultValue = "") List<String> uris,
                                    @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return statsService.getHits(start, end, uris, unique);
    }
}
