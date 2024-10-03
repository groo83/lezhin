package com.lezhin.assignment.domain.purchase.repository;

import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.purchase.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // 작품별 구매수를 기준으로 인기작품 조회
    @Query("SELECT p.content FROM Purchase p GROUP BY p.content ORDER BY COUNT(p) DESC")
    List<Content> findPopularContentByPurchaseCount(Pageable pageable);

    Purchase findByContentId(Long contentId);
}