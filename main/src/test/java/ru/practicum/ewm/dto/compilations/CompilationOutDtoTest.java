package ru.practicum.ewm.dto.compilations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class CompilationOutDtoTest {
    @Autowired
    private JacksonTester<CompilationOutDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new CompilationOutDto(
                1L,
                "title 1",
                new ArrayList<>(),
                true
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.title");
        assertThat(result).hasJsonPath("$.events");
        assertThat(result).hasJsonPath("$.pinned");

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(dto.getTitle());
        assertThat(result).extractingJsonPathBooleanValue("$.pinned").isEqualTo(dto.getPinned());
    }

}