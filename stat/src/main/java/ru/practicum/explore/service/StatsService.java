package ru.practicum.explore.service;

import ru.practicum.explore.dto.StatInDto;
import ru.practicum.explore.dto.StatOutDto;

import java.util.List;

public interface StatsService {
    void saveHit(StatInDto statInDto);

    List<StatOutDto> getHits(String start, String end, List<String> uris, Boolean unique);
}
