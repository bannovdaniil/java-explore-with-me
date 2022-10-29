package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.model.Request;

import java.util.List;

public interface RequestsRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Request r " +
            " SET r.status = ru.practicum.ewm.model.RequestState.REJECTED " +
            " WHERE r.event.id = :eventId AND r.status = ru.practicum.ewm.model.RequestState.PENDING " +
            " ")
    void rejectAllPendingRequest(Long eventId);

    @Query("SELECT r FROM Request r " +
            " JOIN Event e ON r.event.id = e.id " +
            " WHERE e.id = :eventId AND e.initiator.id = :userId "
    )
    List<Request> findAllByInitiatorIdAndEventId(Long userId, Long eventId);
}
