package ru.practicum.ewm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.Constants;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stats", schema = "public", indexes = {@Index(name = "APP_URI_INDEX", columnList = "APP, URI, IP")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(pattern = Constants.DATE_TIME_STRING)
    private LocalDateTime timestamp;

}
