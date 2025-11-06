package com.bomiora.shopping.cart.repository;

import com.bomiora.shopping.cart.entity.HealthProfileCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthProfileCartRepository extends JpaRepository<HealthProfileCart, Integer> {
    
    /**
     * od_id로 조회
     * @param odId 주문 ID
     * @return HealthProfileCart 목록
     */
    List<HealthProfileCart> findByOdId(Long odId);
    
    /**
     * 사용자 ID와 od_id로 조회
     * @param mbId 사용자 ID
     * @param odId 주문 ID
     * @return HealthProfileCart
     */
    Optional<HealthProfileCart> findByMbIdAndOdId(String mbId, Long odId);
    
    /**
     * 사용자 ID로 조회
     * @param mbId 사용자 ID
     * @return HealthProfileCart 목록
     */
    List<HealthProfileCart> findByMbIdOrderByHpWdatetimeDesc(String mbId);
    
    /**
     * 사용자 ID, 상품 ID, 상태로 조회 (처방전 작성 후 장바구니 담기 전 조회용)
     * PHP: update ... where mb_id = ... and it_id = ... and hp_status = '쇼핑'
     * 여러 결과가 있을 경우 가장 최근 것(hp_wdatetime이 가장 최근)만 반환
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @param hpStatus 상태 ('쇼핑')
     * @return HealthProfileCart 목록 (최신순 정렬, 첫 번째 항목 사용)
     */
    @Query("SELECT h FROM HealthProfileCart h WHERE h.mbId = :mbId AND h.itId = :itId AND h.hpStatus = :hpStatus ORDER BY h.hpWdatetime DESC")
    List<HealthProfileCart> findByMbIdAndItIdAndHpStatusOrderByHpWdatetimeDesc(
        @Param("mbId") String mbId,
        @Param("itId") String itId,
        @Param("hpStatus") String hpStatus
    );
}

