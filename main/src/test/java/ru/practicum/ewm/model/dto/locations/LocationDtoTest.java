package ru.practicum.ewm.model.dto.locations;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.ewm.dto.locations.LocationDto;

import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class LocationDtoTest {
    @Autowired
    private JacksonTester<LocationDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new LocationDto(
                1.11f,
                2.22f
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.lat");
        assertThat(result).hasJsonPath("$.lon");

        assertThat(result).extractingJsonPathNumberValue("$.lat")
                .satisfies((number) -> assertThat(number.floatValue()).isCloseTo(dto.getLat(), within(0.01f)));
        assertThat(result).extractingJsonPathNumberValue("$.lon")
                .satisfies((number) -> AssertionsForClassTypes.assertThat(number.floatValue()).isCloseTo(dto.getLon(), within(0.01f)));
    }
}