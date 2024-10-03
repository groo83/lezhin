package com.lezhin.assignment.domain.contentevent.repository;

import com.lezhin.assignment.domain.contentevent.ContentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ContentEventRepository extends JpaRepository<ContentEvent, Long> {

    @Query("SELECT ce FROM ContentEvent ce WHERE ce.content.id = :contentId AND :currentTime BETWEEN ce.startDate AND ce.endDate")
    List<ContentEvent> findActiveEventsByContentId(@Param("contentId") Long contentId, @Param("currentTime") LocalDateTime currentTime);
}