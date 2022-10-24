package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Request;

import java.util.List;

public interface RequestsRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long userId);
}
