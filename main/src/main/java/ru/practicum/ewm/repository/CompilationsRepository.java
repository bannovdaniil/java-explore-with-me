package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Compilation;

import java.util.List;

public interface CompilationsRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findAlLByPinned(Boolean pinned, Pageable pageable);
}
