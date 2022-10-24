package ru.practicum.ewm.endpoints.admin.service;

import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.model.dto.categories.CategoryFullDto;
import ru.practicum.ewm.model.dto.categories.CategoryInDto;

import java.nio.file.AccessDeniedException;

public interface AdminCategoriesService {
    CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws CategoryNotFoundException;

    CategoryFullDto addCategory(CategoryInDto categoryInDto);

    void removeCategory(Long catId) throws CategoryNotFoundException, AccessDeniedException;
}
