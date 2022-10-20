package ru.practicum.ewm.dto.admin.categories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.ewm.dto.categories.CategoryInDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class CategoryInDtoTest {
    @Autowired
    private JacksonTester<CategoryInDto> json;

    @Test
    void testSerialize() throws Exception {
        var dto = new CategoryInDto(
                "Name category"
        );

        var result = json.write(dto);
        assertThat(result).hasJsonPath("$.name");

        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(dto.getName());
    }

}