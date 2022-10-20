package ru.practicum.ewm.dto.categories;

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
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class CategoryFullDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Autowired
    private JacksonTester<CategoryFullDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new CategoryFullDto(
                3L,
                "Name categories"
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
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
        var dto = new CategoryFullDto(1L, text);
        Set<ConstraintViolation<CategoryFullDto>> violations = validator.validate(dto);
        Assertions.assertEquals(expectSize, violations.size());

        if (!violations.isEmpty()) {
            AssertionsForClassTypes.assertThat(violations.iterator().next().getMessageTemplate())
                    .contains(expectedMessage);
        }
    }

    @DisplayName("Check Positive")
    @ParameterizedTest
    @CsvSource({
            "-1, 1, Positive.message",
            "0, 1, Positive.message",
            "1, 0, OK",
    })
    void textNotBlank(long id, int expectSize, String expectedMessage) {
        var dto = new CategoryFullDto(id, "text");
        Set<ConstraintViolation<CategoryFullDto>> violations = validator.validate(dto);
        Assertions.assertEquals(expectSize, violations.size());

        if (!violations.isEmpty()) {
            AssertionsForClassTypes.assertThat(violations.iterator().next().getMessageTemplate())
                    .contains(expectedMessage);
        }
    }

}