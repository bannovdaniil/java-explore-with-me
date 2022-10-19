package ru.practicum.ewm.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.admin.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
