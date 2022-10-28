package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.stats.StatInDto;
import ru.practicum.ewm.dto.stats.StatOutDto;
import ru.practicum.ewm.model.Stat;
import ru.practicum.ewm.service.StatsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public void saveHit(@Valid @RequestBody StatInDto statInDto) {
        log.info("Stats saveHit: {}", statInDto);
        statsService.saveHit(statInDto);
    }

    @GetMapping("/stats")
    public List<StatOutDto> getHits(@NotNull @RequestParam(name = "start") String start,
                                    @NotNull @RequestParam(name = "end") String end,
                                    @Valid
                                    @RequestParam(name = "uris", defaultValue = "", required = false) List<String> uris,
                                    @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Stats getHits: {},{},{},{}", start, end, uris, unique);
        return statsService.getHits(start, end, uris, unique);
    }

    @GetMapping("/log")
    public List<Stat> getAllHits(@PositiveOrZero
                                 @RequestParam(name = "from", defaultValue = "0") Integer from,
                                 @Positive
                                 @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size) {
        log.info("Stats getAllHits: {},{}", from, size);
        return statsService.getAllHits(from, size);
    }

}
