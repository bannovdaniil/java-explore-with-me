package ru.practicum.ewm.model.dto.events;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.ewm.dto.events.EventOutDto;
import ru.practicum.ewm.model.EventState;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class EventOutDtoTest {
    @Autowired
    private JacksonTester<EventOutDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new EventOutDto(
                "annotation 1234566778891234567898765432",
                null,
                null,
                null,
                "title",
                0,
                LocalDateTime.now(),
                "description12345678909876544332122145678765432",
                LocalDateTime.now(),
                1L,
                true,
                10,
                LocalDateTime.now(),
                false,
                EventState.PENDING,
                0L
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.annotation");
        assertThat(result).hasJsonPath("$.category");
        assertThat(result).hasJsonPath("$.initiator");
        assertThat(result).hasJsonPath("$.location");
        assertThat(result).hasJsonPath("$.title");
        assertThat(result).hasJsonPath("$.confirmedRequests");
        assertThat(result).hasJsonPath("$.createdOn");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.eventDate");
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.paid");
        assertThat(result).hasJsonPath("$.participantLimit");
        assertThat(result).hasJsonPath("$.publishedOn");
        assertThat(result).hasJsonPath("$.requestModeration");
        assertThat(result).hasJsonPath("$.state");
        assertThat(result).hasJsonPath("$.views");

        assertThat(result).extractingJsonPathStringValue("$.annotation").isEqualTo(dto.getAnnotation());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(dto.getTitle());
    }

    @Test
    void testBuilderSerialize() throws Exception {
        var dto = EventOutDto.builder()
                .annotation("annotation 1234566778891234567898765432")
                .title("title")
                .description("description12345678909876544332122145678765432")
                .build();

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.annotation");
        assertThat(result).hasJsonPath("$.title");
        assertThat(result).hasJsonPath("$.description");

        assertThat(result).extractingJsonPathStringValue("$.annotation").isEqualTo(dto.getAnnotation());
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo(dto.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(dto.getTitle());
    }

}