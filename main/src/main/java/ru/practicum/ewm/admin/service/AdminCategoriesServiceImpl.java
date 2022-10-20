package ru.practicum.ewm.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.admin.mapper.CategoryMapper;
import ru.practicum.ewm.admin.model.Category;
import ru.practicum.ewm.admin.repository.AdminCategoriesRepository;
import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.dto.categories.CategoryInDto;
import ru.practicum.ewm.exception.CategoryNotFoundException;

@Service
@RequiredArgsConstructor
public class AdminCategoriesServiceImpl implements AdminCategoriesService {
    private final AdminCategoriesRepository adminCategoriesRepository;

    @Override
    public CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws CategoryNotFoundException {
        checkCategoryExists(categoryFullDto.getId());
        Category category = CategoryMapper.dtoOutToCategory(categoryFullDto);
        return CategoryMapper.categoryToDtoOut(adminCategoriesRepository.save(category));
    }

    @Override
    public CategoryFullDto addCategory(CategoryInDto categoryInDto) {
        Category category = CategoryMapper.dtoInToCategory(categoryInDto);
        return CategoryMapper.categoryToDtoOut(adminCategoriesRepository.save(category));
    }

    @Override
    public void removeCategory(Long catId) throws CategoryNotFoundException {
        checkCategoryExists(catId);
        adminCategoriesRepository.deleteById(catId);
    }

    private void checkCategoryExists(Long catId) throws CategoryNotFoundException {
        if (!adminCategoriesRepository.existsById(catId)) {
            throw new CategoryNotFoundException("Category ID was not found.");
        }
    }

}
