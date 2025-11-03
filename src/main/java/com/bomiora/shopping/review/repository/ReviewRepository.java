package com.bomiora.shopping.review.repository;

import com.bomiora.shopping.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
    /**
     * 제품별 리뷰 목록 조회
     */
    List<Review> findByProductIdOrderByCreatedAtDesc(String productId);
    
    /**
     * 제품별 리뷰 종류별 조회
     */
    @Query("SELECT r FROM Review r WHERE r.productId = :productId AND r.reviewKind = :reviewKind ORDER BY r.createdAt DESC")
    List<Review> findByProductIdAndReviewKind(@Param("productId") String productId, @Param("reviewKind") String reviewKind);
    
    /**
     * 제품별 리뷰 통계 조회
     */
    @Query("SELECT " +
           "AVG((r.score1 + r.score2 + r.score3 + r.score4) / 4.0) as avgScore, " +
           "COUNT(CASE WHEN r.recommend = 'y' THEN 1 END) as recommendCount, " +
           "COUNT(r) as totalCount " +
           "FROM Review r WHERE r.productId = :productId")
    Object[] getReviewStats(@Param("productId") String productId);
}
