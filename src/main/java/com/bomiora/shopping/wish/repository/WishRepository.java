package com.bomiora.shopping.wish.repository;

import com.bomiora.shopping.wish.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer> {
    
    /**
     * 사용자 ID로 찜목록 조회
     * @param mbId 사용자 ID
     * @return 찜목록
     */
    List<Wish> findByMbIdOrderByWiTimeDesc(String mbId);
    
    /**
     * 사용자 ID와 상품 ID로 찜하기 조회
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @return 찜하기 정보
     */
    Optional<Wish> findByMbIdAndItId(String mbId, String itId);
    
    /**
     * 사용자 ID와 상품 ID로 찜하기 삭제
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     */
    void deleteByMbIdAndItId(String mbId, String itId);
    
    /**
     * 사용자가 특정 상품을 찜했는지 확인
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @return 찜하기 여부
     */
    boolean existsByMbIdAndItId(String mbId, String itId);
}

