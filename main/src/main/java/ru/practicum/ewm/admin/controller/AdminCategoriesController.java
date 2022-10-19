package ru.practicum.ewm.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.admin.exception.NotFoundCategoryId;
import ru.practicum.ewm.admin.service.AdminCategoriesService;
import ru.practicum.ewm.dto.admin.categories.CategoryFullDto;
import ru.practicum.ewm.dto.admin.categories.CategoryInDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoriesController {
    private final AdminCategoriesService adminCategoriesService;

    @PatchMapping
    public CategoryFullDto updateCategory(@Valid @RequestBody CategoryFullDto categoryFullDto) throws NotFoundCategoryId {
        return adminCategoriesService.updateCategory(categoryFullDto);
    }

    @PostMapping
    public CategoryFullDto addCategory(@Valid @RequestBody CategoryInDto categoryInDto) {
        return adminCategoriesService.addCategory(categoryInDto);
    }

    @DeleteMapping("{catId}")
    void removeCategory(@Positive @PathVariable Long catId) throws NotFoundCategoryId {
        adminCategoriesService.removeCategory(catId);
    }
}
