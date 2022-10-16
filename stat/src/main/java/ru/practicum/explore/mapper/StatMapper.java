package ru.practicum.explore.mapper;

import ru.practicum.explore.StatsConstants;
import ru.practicum.explore.dto.StatInDto;
import ru.practicum.explore.dto.StatOutDto;
import ru.practicum.explore.model.Stat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatMapper {
    public static Stat dtoToStat(StatInDto statInDto) {
        Stat stat = new Stat();

        stat.setApp(statInDto.getApp());
        stat.setUri(statInDto.getUri());
        stat.setIp(statInDto.getIp());
        stat.setTimestamp(LocalDateTime.parse(statInDto.getTimestamp(), StatsConstants.DATE_TIME_SPACE));

        return stat;
    }

    public static List<StatOutDto> statsListToDto(List<Stat> stats) {
        List<StatOutDto> lists = new ArrayList<>();
        for (Stat stat : stats) {
            lists.add(statToDto(stat));
        }
        return lists;
    }

    private static StatOutDto statToDto(Stat stat) {
        StatOutDto statOutDto = new StatOutDto();
        statOutDto.setApp(stat.getApp());
        statOutDto.setUri(stat.getUri());
        return statOutDto;
    }
}
