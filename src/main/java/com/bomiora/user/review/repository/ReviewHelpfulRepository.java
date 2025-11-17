package com.bomiora.user.review.repository;

import com.bomiora.user.review.entity.ReviewHelpful;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewHelpfulRepository extends JpaRepository<ReviewHelpful, Long> {
    
    /**
     * 특정 사용자가 특정 제품의 특정 리뷰에 이미 "도움이 돼요"를 눌렀는지 확인
     * (기존 PHP 로직과 동일: it_id, is_id, mb_id 모두 체크)
     */
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM ReviewHelpful h " +
           "WHERE h.itId = :itId AND h.reviewId = :reviewId AND h.mbId = :mbId")
    boolean existsByItIdAndReviewIdAndMbId(
        @Param("itId") String itId, 
        @Param("reviewId") Long reviewId, 
        @Param("mbId") String mbId
    );
}

