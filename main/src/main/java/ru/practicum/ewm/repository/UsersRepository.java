package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {
    @Query(
            "SELECT u FROM User as u WHERE u.id in :ids"
    )
    List<User> findAllByIds(Long[] ids, Pageable pageable);
}
