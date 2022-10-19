package ru.practicum.ewm.admin.controller.categories;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.admin.categories.CategoryOutDto;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    @PatchMapping
    public CategoryOutDto updateCategory(CategoryOutDto categoryOutDto) {
        return null;
    }
}
