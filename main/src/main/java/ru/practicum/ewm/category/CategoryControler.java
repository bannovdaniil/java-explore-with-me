package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.exception.NotFoundCategoryId;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class CategoryControler {

    private final CategoryService categoriesService;

    @GetMapping
    public List<CategoryFullDto> findAllCategories(@PositiveOrZero
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive
                                                   @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size) {
        return categoriesService.findAllCategories(from, size);
    }

    @GetMapping("{catId}")
    public CategoryFullDto findCategoriesById(@Positive @PathVariable Long catId) throws NotFoundCategoryId {
        return categoriesService.findCategoriesById(catId);
    }
}
