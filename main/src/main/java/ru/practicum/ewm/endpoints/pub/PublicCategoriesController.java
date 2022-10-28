package ru.practicum.ewm.endpoints.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Constants;
import ru.practicum.ewm.dto.categories.CategoryFullDto;
import ru.practicum.ewm.endpoints.pub.service.CategoriesService;
import ru.practicum.ewm.exception.CategoryNotFoundException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PublicCategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public List<CategoryFullDto> findAllCategories(@PositiveOrZero
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive
                                                   @RequestParam(name = "size", defaultValue = Constants.PAGE_SIZE_STRING) Integer size) {
        log.info("Public findAllCategories: {},{}", from, size);
        return categoriesService.findAllCategories(from, size);
    }

    @GetMapping("{catId}")
    public CategoryFullDto findCategoryById(@Positive @PathVariable Long catId) throws CategoryNotFoundException {
        log.info("Public findCategoryById: {}", catId);
        return categoriesService.findCategoryById(catId);
    }
}
