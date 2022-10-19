package ru.practicum.ewm.category.service;

import ru.practicum.ewm.admin.exception.NotFoundCategoryId;
import ru.practicum.ewm.dto.admin.categories.CategoryFullDto;

import java.util.List;

public interface CategoryService {
    List<CategoryFullDto> findAllCategories(Integer from, Integer size);

    CategoryFullDto findCategoriesById(Long catId) throws NotFoundCategoryId;
}
