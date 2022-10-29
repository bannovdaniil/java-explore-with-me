package ru.practicum.ewm.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.stats.StatInDto;
import ru.practicum.ewm.model.Stat;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StatMapperTest {
    private StatInDto statInDto;
    private Stat expected;

    @BeforeEach
    void setUp() {
        statInDto = new StatInDto(
                "App name",
                "https://some.uri.com/events/1",
                "10.0.65.1",
                "2022-10-19 11:10:00"
        );
        expected = new Stat(
                null,
                "App name",
                "https://some.uri.com/events/1",
                "10.0.65.1",
                LocalDateTime.parse("2022-10-19 11:10:00", Constants.DATE_TIME_SPACE)
        );
    }

    @Test
    void dtoToStat() {
        Stat result = StatMapper.dtoToStat(statInDto);

        assertThat(expected.getId()).isEqualTo(result.getId());
        assertThat(expected.getApp()).isEqualTo(result.getApp());
        assertThat(expected.getUri()).isEqualTo(result.getUri());
        assertThat(expected.getIp()).isEqualTo(result.getIp());
        assertThat(expected.getTimestamp()).isEqualTo(result.getTimestamp());

    }
}