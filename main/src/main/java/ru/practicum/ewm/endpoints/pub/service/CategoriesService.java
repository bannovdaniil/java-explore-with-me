package ru.practicum.ewm.endpoints.pub.service;

import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoriesService {
    List<CategoryFullDto> findAllCategories(Integer from, Integer size);

    CategoryFullDto findCategoriesById(Long catId) throws CategoryNotFoundException;
}
