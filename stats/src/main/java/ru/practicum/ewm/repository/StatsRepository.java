package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.dto.StatOutDto;
import ru.practicum.ewm.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stat, Long> {
    @Query("SELECT new ru.practicum.ewm.dto.StatOutDto(s.app, s.uri, count(s.id)) " +
            " FROM Stat as s " +
            " WHERE s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri "
    )
    List<StatOutDto> countByTimestamp(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.ewm.dto.StatOutDto(s.app, s.uri, count(s.id)) " +
            " FROM Stat as s " +
            " WHERE s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri, s.ip "
    )
    List<StatOutDto> countByTimestampUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.ewm.dto.StatOutDto(s.app, s.uri, count(s.id)) " +
            " FROM Stat as s " +
            " WHERE s.uri IN :uris AND s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri "
    )
    List<StatOutDto> countByTimestampAndList(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.ewm.dto.StatOutDto(s.app, s.uri, count(s.id)) " +
            " FROM Stat as s " +
            " WHERE s.uri IN :uris AND s.timestamp BETWEEN :start AND :end " +
            " GROUP BY s.app, s.uri, s.ip "
    )
    List<StatOutDto> countByTimestampAndListUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
