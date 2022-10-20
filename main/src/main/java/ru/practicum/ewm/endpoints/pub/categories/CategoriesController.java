package ru.practicum.ewm.endpoints.pub.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.endpoints.pub.categories.service.CategoriesService;
import ru.practicum.ewm.exception.CategoryNotFoundException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public List<CategoryFullDto> findAllCategories(@PositiveOrZero
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive
                                                   @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size) {
        return categoriesService.findAllCategories(from, size);
    }

    @GetMapping("{catId}")
    public CategoryFullDto findCategoriesById(@Positive @PathVariable Long catId) throws CategoryNotFoundException {
        return categoriesService.findCategoriesById(catId);
    }
}
