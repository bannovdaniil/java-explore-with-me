package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.StatInDto;
import ru.practicum.ewm.dto.StatOutDto;
import ru.practicum.ewm.service.StatsService;

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
    public List<StatOutDto> getHits(@NotNull @RequestParam(name = "start") String start,
                                    @NotNull @RequestParam(name = "end") String end,
                                    @Valid
                                    @RequestParam(name = "uris", defaultValue = "", required = false) List<String> uris,
                                    @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return statsService.getHits(start, end, uris, unique);
    }
}
