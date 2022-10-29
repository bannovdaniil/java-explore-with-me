package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {

}
