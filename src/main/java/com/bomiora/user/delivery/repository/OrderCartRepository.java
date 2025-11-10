package com.bomiora.user.delivery.repository;

import com.bomiora.user.delivery.entity.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
    
    /**
     * 주문 ID로 상품 목록 조회
     */
    List<OrderCart> findByOdIdOrderByCtIdAsc(Long odId);
    
    /**
     * 주문 ID와 회원 ID로 상품 목록 조회
     */
    List<OrderCart> findByOdIdAndMbIdOrderByCtIdAsc(Long odId, String mbId);
    
    /**
     * 여러 주문 ID로 상품 목록 조회
     */
    List<OrderCart> findByOdIdInOrderByOdIdDescCtIdAsc(List<Long> odIds);
}

