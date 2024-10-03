package com.lezhin.assignment.domain.contentview.repository;

import com.lezhin.assignment.domain.contentview.ContentViewHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentViewHistoryRepository extends JpaRepository<ContentViewHistory, Long> {

    List<ContentViewHistory> findByContentId(Long contentId);

    @Query("SELECT ch.contentId, COUNT(ch) as viewCount " +
            "FROM ContentViewHistory ch " +
            "GROUP BY ch.contentId " +
            "ORDER BY viewCount DESC")
    List<Object[]> findTopNContentByViewCount(Pageable pageable);
}
