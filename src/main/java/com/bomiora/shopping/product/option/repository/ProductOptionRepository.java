package com.bomiora.shopping.product.option.repository;

import com.bomiora.shopping.product.option.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, String> {
    
    /**
     * 제품 ID로 옵션 목록 조회 (사용 가능한 옵션만)
     */
    List<ProductOption> findByProductIdAndUseFlag(String productId, Integer useFlag);
    
    /**
     * 제품 ID로 모든 옵션 조회
     */
    List<ProductOption> findByProductId(String productId);
}

