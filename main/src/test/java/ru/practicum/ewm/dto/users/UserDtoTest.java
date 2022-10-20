package ru.practicum.ewm.dto.users;

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
class UserDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new UserDto(
                1L,
                "User name",
                "email@mail.com"
        );

        var result = json.write(dto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.email");

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(dto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo(dto.getEmail());
    }

    @DisplayName("Email validation")
    @ParameterizedTest
    @CsvSource({
            "email, 1, Email.message",
            "@email, 1, Email.message",
            "email@, 1, Email.message",
            "email@@dd, 1, Email.message",
            "'ema il@dd', 1, Email.message",
            "'name@email.com', 0, OK"
    })
    void emailValidation(String email, int expectSize, String expectedMessage) {
        if ("null".equals(email)) {
            email = null;
        }
        var dto = new UserDto();
        dto.setEmail(email);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto);
        Assertions.assertEquals(expectSize, violations.size());

        if (!violations.isEmpty()) {
            AssertionsForClassTypes.assertThat(violations.iterator().next().getMessageTemplate())
                    .contains(expectedMessage);
        }
    }
}