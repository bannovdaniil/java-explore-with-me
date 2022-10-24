package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Request;

public interface RequestsRepository extends JpaRepository<Request, Long> {
}
