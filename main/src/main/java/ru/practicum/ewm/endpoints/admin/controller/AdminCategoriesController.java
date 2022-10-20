package ru.practicum.ewm.endpoints.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.dto.categories.CategoryInDto;
import ru.practicum.ewm.endpoints.admin.service.AdminCategoriesService;
import ru.practicum.ewm.exception.CategoryNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoriesController {
    private final AdminCategoriesService adminCategoriesService;

    @PatchMapping
    public CategoryFullDto updateCategory(@Valid @RequestBody CategoryFullDto categoryFullDto) throws CategoryNotFoundException {
        return adminCategoriesService.updateCategory(categoryFullDto);
    }

    @PostMapping
    public CategoryFullDto addCategory(@Valid @RequestBody CategoryInDto categoryInDto) {
        return adminCategoriesService.addCategory(categoryInDto);
    }

    @DeleteMapping("{catId}")
    void removeCategory(@Positive @PathVariable Long catId) throws CategoryNotFoundException {
        adminCategoriesService.removeCategory(catId);
    }
}
