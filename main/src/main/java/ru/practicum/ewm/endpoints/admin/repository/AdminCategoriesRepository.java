package ru.practicum.ewm.endpoints.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Category;

public interface AdminCategoriesRepository extends JpaRepository<Category, Long> {

}
