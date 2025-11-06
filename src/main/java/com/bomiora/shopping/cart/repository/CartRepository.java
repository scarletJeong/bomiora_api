package com.bomiora.shopping.cart.repository;

import com.bomiora.shopping.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    
    /**
     * 사용자 ID와 상태로 장바구니 조회
     * @param mbId 사용자 ID
     * @param ctStatus 장바구니 상태 (예: "쇼핑")
     * @return 장바구니 목록
     */
    @Query("SELECT c FROM Cart c WHERE c.mbId = :mbId AND c.ctStatus = :ctStatus ORDER BY c.ctTime DESC")
    List<Cart> findByMbIdAndCtStatus(@Param("mbId") String mbId, @Param("ctStatus") String ctStatus);
    
    /**
     * 사용자 ID로 장바구니 조회
     * @param mbId 사용자 ID
     * @return 장바구니 목록
     */
    List<Cart> findByMbIdOrderByCtTimeDesc(String mbId);
    
    /**
     * 장바구니 ID로 조회
     * @param ctId 장바구니 ID
     * @return 장바구니 정보
     */
    Cart findByCtId(Integer ctId);
    
    /**
     * 동일 상품/옵션 조합 조회 (UPDATE용)
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @param ioId 옵션 ID (빈 문자열 가능)
     * @param ctStatus 장바구니 상태
     * @return 장바구니 항목 (없으면 null)
     */
    @Query("SELECT c FROM Cart c WHERE c.mbId = :mbId AND c.itId = :itId AND " +
           "((:ioId = '' AND (c.ioId IS NULL OR c.ioId = '')) OR c.ioId = :ioId) AND c.ctStatus = :ctStatus")
    Cart findByMbIdAndItIdAndIoIdAndCtStatus(
        @Param("mbId") String mbId,
        @Param("itId") String itId,
        @Param("ioId") String ioId,
        @Param("ctStatus") String ctStatus
    );
}

