package ru.practicum.ewm.dto.events;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class EventInDtoTest {
    @Autowired
    private JacksonTester<EventInDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new EventInDto(
                "annotation 1234566778891234567898765432",
                1L,
                "description12345678909876544332122145678765432",
                null,
                "title",
                "2022-10-21 20:48:11",
                true,
                10,
                false
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.annotation");
        assertThat(result).hasJsonPath("$.category");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.location");
        assertThat(result).hasJsonPath("$.title");
        assertThat(result).hasJsonPath("$.eventDate");
        assertThat(result).hasJsonPath("$.paid");
        assertThat(result).hasJsonPath("$.participantLimit");
        assertThat(result).hasJsonPath("$.requestModeration");

        assertThat(result).extractingJsonPathStringValue("$.annotation").isEqualTo(dto.getAnnotation());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(dto.getTitle());
    }

}