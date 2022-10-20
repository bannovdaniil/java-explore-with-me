package ru.practicum.ewm.pub.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.admin.model.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
}
