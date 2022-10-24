package ru.practicum.ewm.endpoints.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.endpoints.pub.service.CategoriesService;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.model.dto.categories.CategoryFullDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class PublicCategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public List<CategoryFullDto> findAllCategories(@PositiveOrZero
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive
                                                   @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size) {
        return categoriesService.findAllCategories(from, size);
    }

    @GetMapping("{catId}")
    public CategoryFullDto findCategoryById(@Positive @PathVariable Long catId) throws CategoryNotFoundException {
        return categoriesService.findCategoryById(catId);
    }
}
