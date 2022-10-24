package ru.practicum.ewm.endpoints.pub.service;

import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.model.dto.categories.CategoryFullDto;

import java.util.List;

public interface CategoriesService {
    List<CategoryFullDto> findAllCategories(Integer from, Integer size);

    CategoryFullDto findCategoryById(Long catId) throws CategoryNotFoundException;
}
