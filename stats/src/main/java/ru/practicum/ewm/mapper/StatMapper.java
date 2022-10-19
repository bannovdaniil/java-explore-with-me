package ru.practicum.ewm.mapper;

import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.stats.StatInDto;
import ru.practicum.ewm.model.Stat;

import java.time.LocalDateTime;

public class StatMapper {
    public static Stat dtoToStat(StatInDto statInDto) {
        Stat stat = new Stat();

        stat.setApp(statInDto.getApp());
        stat.setUri(statInDto.getUri());
        stat.setIp(statInDto.getIp());
        stat.setTimestamp(LocalDateTime.parse(statInDto.getTimestamp(), Constants.DATE_TIME_SPACE));

        return stat;
    }
}
