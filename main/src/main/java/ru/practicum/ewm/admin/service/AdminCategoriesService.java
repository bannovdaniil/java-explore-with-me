package ru.practicum.ewm.admin.service;

import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.dto.categories.CategoryInDto;
import ru.practicum.ewm.exception.NotFoundCategoryId;

public interface AdminCategoriesService {
    CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws NotFoundCategoryId;

    CategoryFullDto addCategory(CategoryInDto categoryInDto);

    void removeCategory(Long catId) throws NotFoundCategoryId;
}
