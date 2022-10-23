package ru.practicum.ewm.dto.compilations;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class CompilationInDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Autowired
    private JacksonTester<CompilationInDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new CompilationInDto(
                "title 1",
                new ArrayList<>(),
                true
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.title");
        assertThat(result).hasJsonPath("$.events");
        assertThat(result).hasJsonPath("$.pinned");

        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo(dto.getTitle());
        assertThat(result).extractingJsonPathBooleanValue("$.pinned").isEqualTo(dto.getPinned());
    }

    @DisplayName("Check Blank")
    @ParameterizedTest
    @CsvSource({
            "null, 1, NotBlank.message",
            "'', 1, NotBlank.message",
            "text, 0, OK",
    })
    void textNotBlank(String text, int expectSize, String expectedMessage) {
        if ("null".equals(text)) {
            text = null;
        }
        var dto = new CompilationInDto(text, List.of(), true);
        Set<ConstraintViolation<CompilationInDto>> violations = validator.validate(dto);
        Assertions.assertEquals(expectSize, violations.size());

        if (!violations.isEmpty()) {
            AssertionsForClassTypes.assertThat(violations.iterator().next().getMessageTemplate())
                    .contains(expectedMessage);
        }
    }
}