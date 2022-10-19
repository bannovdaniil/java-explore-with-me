package ru.practicum.ewm.admin.service;

import ru.practicum.ewm.admin.exception.NotFoundCategoryId;
import ru.practicum.ewm.dto.admin.categories.CategoryFullDto;
import ru.practicum.ewm.dto.admin.categories.CategoryInDto;

public interface AdminCategoriesService {
    CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws NotFoundCategoryId;

    CategoryFullDto addCategory(CategoryInDto categoryInDto);

    void removeCategory(Long catId) throws NotFoundCategoryId;
}
