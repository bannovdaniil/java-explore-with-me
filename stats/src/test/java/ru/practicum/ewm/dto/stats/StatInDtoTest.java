package ru.practicum.ewm.dto.stats;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.ewm.Constants;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class StatInDtoTest {
    @Autowired
    private JacksonTester<StatInDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new StatInDto(
                "App name",
                "https://some.uri.com/events/1",
                "10.0.65.1",
                "2022-10-19 11:10:00"
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.app");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).hasJsonPath("$.ip");
        assertThat(result).hasJsonPath("$.timestamp");

        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo(dto.getApp());
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo(dto.getUri());
        assertThat(result).extractingJsonPathStringValue("$.ip").isEqualTo(dto.getIp());
        assertThat(result).extractingJsonPathStringValue("$.timestamp")
                .startsWith(dto.getTimestamp().format(Constants.DATE_TIME_SPACE));
    }

}