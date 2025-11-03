package com.bomiora.shopping.product.repository;

import com.bomiora.shopping.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    /**
     * 카테고리별 상품 목록 조회 (it_use = 1인 상품만)
     */
    @Query("SELECT p FROM Product p WHERE p.categoryId = :categoryId " +
           "AND p.useFlag = 1 " +
           "ORDER BY p.id DESC")
    Page<Product> findByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);
    
    /**
     * 카테고리 및 상품 종류별 상품 목록 조회 (it_use = 1인 상품만)
     */
    @Query("SELECT p FROM Product p WHERE p.categoryId = :categoryId " +
           "AND (:productKind IS NULL OR p.productKind = :productKind) " +
           "AND p.useFlag = 1 " +
           "ORDER BY p.id DESC")
    Page<Product> findByCategoryIdAndProductKind(
        @Param("categoryId") String categoryId,
        @Param("productKind") String productKind,
        Pageable pageable
    );
    
    /**
     * 상품 ID로 조회
     */
    Optional<Product> findById(String id);
    
    /**
     * 인기 상품 조회 (it_type4 = 1이고 it_use = 1인 상품, 정렬: 등록일)
     */
    @Query("SELECT p FROM Product p WHERE p.isBestFlag = 1 " +
           "AND p.useFlag = 1 " +
           "ORDER BY p.createdAt DESC")
    List<Product> findBestProducts(org.springframework.data.domain.Pageable pageable);
    
    /**
     * 신상품 조회 (it_type3 = 1이고 it_use = 1인 상품)
     */
    @Query("SELECT p FROM Product p WHERE p.isNewFlag = 1 " +
           "AND p.useFlag = 1 " +
           "ORDER BY p.createdAt DESC")
    List<Product> findNewProducts(org.springframework.data.domain.Pageable pageable);
    
    /**
     * 카테고리별 상품 개수 조회 (it_use = 1인 상품만)
     */
    @Query("SELECT COUNT(p) FROM Product p WHERE p.categoryId = :categoryId " +
           "AND p.useFlag = 1")
    long countByCategoryId(@Param("categoryId") String categoryId);
    
    /**
     * 카테고리 및 상품 종류별 상품 개수 조회 (it_use = 1인 상품만)
     */
    @Query("SELECT COUNT(p) FROM Product p WHERE p.categoryId = :categoryId " +
           "AND (:productKind IS NULL OR p.productKind = :productKind) " +
           "AND p.useFlag = 1")
    long countByCategoryIdAndProductKind(
        @Param("categoryId") String categoryId,
        @Param("productKind") String productKind
    );
}

