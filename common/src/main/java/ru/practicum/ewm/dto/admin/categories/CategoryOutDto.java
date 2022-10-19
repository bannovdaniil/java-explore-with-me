package ru.practicum.ewm.dto.admin.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOutDto {
    @Positive
    private Long id;
    @NotBlank
    private String name;
}
