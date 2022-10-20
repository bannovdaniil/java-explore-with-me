package ru.practicum.ewm.endpoints.pub.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
}
