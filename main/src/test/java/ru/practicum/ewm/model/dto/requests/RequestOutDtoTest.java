package ru.practicum.ewm.model.dto.requests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.ewm.model.RequestState;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class RequestOutDtoTest {
    @Autowired
    private JacksonTester<RequestOutDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new RequestOutDto(
                2L,
                LocalDateTime.now(),
                null,
                null,
                RequestState.PENDING
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.created");
        assertThat(result).hasJsonPath("$.event");
        assertThat(result).hasJsonPath("$.requester");
        assertThat(result).hasJsonPath("$.status");
    }
}