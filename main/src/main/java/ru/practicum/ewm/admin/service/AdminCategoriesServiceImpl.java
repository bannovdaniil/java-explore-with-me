package ru.practicum.ewm.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.admin.exception.NotFoundCategoryId;
import ru.practicum.ewm.admin.mapper.CategoryMapper;
import ru.practicum.ewm.admin.model.Category;
import ru.practicum.ewm.admin.repository.AdminCategoriesRepository;
import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.dto.categories.CategoryInDto;

@Service
@RequiredArgsConstructor
public class AdminCategoriesServiceImpl implements AdminCategoriesService {
    private final AdminCategoriesRepository adminCategoriesRepository;

    @Override
    public CategoryFullDto updateCategory(CategoryFullDto categoryFullDto) throws NotFoundCategoryId {
        if (!adminCategoriesRepository.existsById(categoryFullDto.getId())) {
            throw new NotFoundCategoryId("Event with id=" + categoryFullDto.getId() + " was not found.");
        }
        Category category = CategoryMapper.dtoOutToCategory(categoryFullDto);
        return CategoryMapper.categoryToDtoOut(adminCategoriesRepository.save(category));
    }

    @Override
    public CategoryFullDto addCategory(CategoryInDto categoryInDto) {
        Category category = CategoryMapper.dtoInToCategory(categoryInDto);
        return CategoryMapper.categoryToDtoOut(adminCategoriesRepository.save(category));
    }

    @Override
    public void removeCategory(Long catId) throws NotFoundCategoryId {
        if (!adminCategoriesRepository.existsById(catId)) {
            throw new NotFoundCategoryId("Event with id=" + catId + " was not found.");
        }
        adminCategoriesRepository.deleteById(catId);
    }

}
