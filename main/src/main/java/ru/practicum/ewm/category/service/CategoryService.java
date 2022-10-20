package ru.practicum.ewm.category.service;

import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.exception.NotFoundCategoryId;

import java.util.List;

public interface CategoryService {
    List<CategoryFullDto> findAllCategories(Integer from, Integer size);

    CategoryFullDto findCategoriesById(Long catId) throws NotFoundCategoryId;
}
