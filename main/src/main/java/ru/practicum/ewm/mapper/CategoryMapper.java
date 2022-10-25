package ru.practicum.ewm.mapper;

import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.dto.categories.CategoryFullDto;
import ru.practicum.ewm.model.dto.categories.CategoryInDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static Category dtoOutToCategory(CategoryFullDto categoryFullDto) {
        return new Category(
                categoryFullDto.getId(),
                categoryFullDto.getName()
        );
    }

    public static CategoryFullDto categoryToDtoOut(Category category) {
        return new CategoryFullDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category dtoInToCategory(CategoryInDto categoryInDto) {
        Category category = new Category();
        category.setName(categoryInDto.getName());
        return category;
    }

    public static List<CategoryFullDto> categoryToListDtoOut(List<Category> listCategories) {
        List<CategoryFullDto> categoryFullDtoList = new ArrayList<>();
        for (Category category : listCategories) {
            categoryFullDtoList.add(categoryToDtoOut(category));
        }
        return categoryFullDtoList;
    }
}
