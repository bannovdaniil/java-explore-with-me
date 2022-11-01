package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Like;
import ru.practicum.ewm.model.LikeType;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByEventIdAndUserId(Long userId, Long eventId);

    Optional<Like> findByUserIdAndEventIdAndType(Long userId, Long eventId, LikeType likeType);
}