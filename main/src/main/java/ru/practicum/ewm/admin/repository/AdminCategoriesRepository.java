package ru.practicum.ewm.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.admin.model.Category;

public interface AdminCategoriesRepository extends JpaRepository<Category, Long> {

}
