package ru.practicum.ewm.endpoints.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.dto.categories.CategoryInDto;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.repository.CategoriesRepository;

@Service
@RequiredArgsConstructor
public class AdminCategoriesServiceImpl implements AdminCategoriesService {
    private final CategoriesRepository categoriesRepository;

    @Override
    public CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws CategoryNotFoundException {
        checkCategoryExists(categoryFullDto.getId());
        Category category = CategoryMapper.dtoOutToCategory(categoryFullDto);
        return CategoryMapper.categoryToDtoOut(categoriesRepository.save(category));
    }

    @Override
    public CategoryFullDto addCategory(CategoryInDto categoryInDto) {
        Category category = CategoryMapper.dtoInToCategory(categoryInDto);
        return CategoryMapper.categoryToDtoOut(categoriesRepository.save(category));
    }

    @Override
    public void removeCategory(Long catId) throws CategoryNotFoundException {
        checkCategoryExists(catId);
        categoriesRepository.deleteById(catId);
    }

    private void checkCategoryExists(Long catId) throws CategoryNotFoundException {
        if (!categoriesRepository.existsById(catId)) {
            throw new CategoryNotFoundException("Category ID was not found.");
        }
    }

}
