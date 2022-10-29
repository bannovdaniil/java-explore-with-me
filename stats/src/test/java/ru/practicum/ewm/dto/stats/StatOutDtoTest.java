package ru.practicum.ewm.dto.stats;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class StatOutDtoTest {
    @Autowired
    private JacksonTester<StatOutDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new StatOutDto(
                "App name",
                "https://some.uri.com/events/1",
                5L
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.app");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).hasJsonPath("$.hits");

        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo(dto.getApp());
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo(dto.getUri());
        assertThat(result).extractingJsonPathNumberValue("$.hits").isEqualTo(dto.getHits().intValue());
    }

}