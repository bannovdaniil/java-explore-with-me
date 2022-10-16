package ru.practicum.explore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.explore.StatsConstants;
import ru.practicum.explore.dto.StatInDto;
import ru.practicum.explore.dto.StatOutDto;
import ru.practicum.explore.mapper.StatMapper;
import ru.practicum.explore.model.Stat;
import ru.practicum.explore.repository.StatsRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public void saveHit(@Valid @RequestBody StatInDto statInDto) {
        Stat stats = StatMapper.dtoToStat(statInDto);
        statsRepository.save(stats);
    }

    @Override
    public List<StatOutDto> getHits(String start, String end, List<String> uris, Boolean unique) {
        List<StatOutDto> stats = List.of();
        if (uris.size() == 0) {
            if (unique) {
                stats = statsRepository.countByTimestampUniqueIp(
                        LocalDateTime.parse(start, StatsConstants.DATE_TIME_SPACE),
                        LocalDateTime.parse(end, StatsConstants.DATE_TIME_SPACE));
            } else {
                stats = statsRepository.countByTimestamp(
                        LocalDateTime.parse(start, StatsConstants.DATE_TIME_SPACE),
                        LocalDateTime.parse(end, StatsConstants.DATE_TIME_SPACE));
            }
        } else {
            if (unique) {
                stats = statsRepository.countByTimestampAndListUniqueIp(
                        LocalDateTime.parse(start, StatsConstants.DATE_TIME_SPACE),
                        LocalDateTime.parse(end, StatsConstants.DATE_TIME_SPACE),
                        uris);
            } else {
                stats = statsRepository.countByTimestampAndList(
                        LocalDateTime.parse(start, StatsConstants.DATE_TIME_SPACE),
                        LocalDateTime.parse(end, StatsConstants.DATE_TIME_SPACE),
                        uris);
            }
        }
        return stats;
    }
}
