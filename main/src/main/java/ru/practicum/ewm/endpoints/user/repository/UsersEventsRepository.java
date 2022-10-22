package ru.practicum.ewm.endpoints.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface UsersEventsRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            " WHERE e.initiator.id IN :users " +
            " AND e.state IN :states " +
            " AND e.category.id IN :categories " +
            " AND e.eventDate BETWEEN :startDate AND :endDate "
    )
    List<Event> findAllByUsersAndStatesAndCetegories(Long[] users, String[] states, Long[] categories, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
