package com.bomiora.user.review.repository;

import com.bomiora.user.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    /**
     * 특정 상품의 리뷰 조회 (승인된 것만)
     */
    Page<Review> findByItIdAndIsConfirmOrderByIsIdDesc(String itId, Integer isConfirm, Pageable pageable);
    
    /**
     * 특정 회원의 리뷰 조회
     */
    Page<Review> findByMbIdAndIsConfirmOrderByIsIdDesc(String mbId, Integer isConfirm, Pageable pageable);
    
    /**
     * 전체 리뷰 조회 (승인된 것만)
     */
    Page<Review> findByIsConfirmOrderByIsIdDesc(Integer isConfirm, Pageable pageable);
    
    /**
     * 전체 리뷰 조회 - 리뷰 종류별 (승인된 것만)
     */
    Page<Review> findByIsRvkindAndIsConfirmOrderByIsIdDesc(String isRvkind, Integer isConfirm, Pageable pageable);
    
    /**
     * 특정 상품, 특정 리뷰 종류 조회
     */
    Page<Review> findByItIdAndIsRvkindAndIsConfirmOrderByIsIdDesc(
        String itId, String isRvkind, Integer isConfirm, Pageable pageable);
    
    /**
     * 특정 회원의 특정 주문에 대한 리뷰가 이미 존재하는지 확인
     */
    boolean existsByMbIdAndOdId(String mbId, Long odId);
    
    /**
     * 특정 상품의 리뷰 개수
     */
    long countByItIdAndIsConfirm(String itId, Integer isConfirm);
    
    /**
     * 특정 상품의 평균 평점 계산
     */
    @Query("SELECT AVG((r.isScore1 + r.isScore2 + r.isScore3 + r.isScore4) / 4.0) " +
           "FROM Review r WHERE r.itId = :itId AND r.isConfirm = 1")
    Double getAverageScoreByItId(@Param("itId") String itId);
    
    /**
     * 특정 회원의 리뷰 개수
     */
    long countByMbIdAndIsConfirm(String mbId, Integer isConfirm);
    
    /**
     * 특정 주문의 리뷰 조회
     */
    List<Review> findByOdId(Long odId);
}

