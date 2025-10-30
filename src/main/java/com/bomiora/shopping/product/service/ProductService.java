package com.bomiora.shopping.product.service;

import com.bomiora.shopping.product.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    
    /**
     * 카테고리별 상품 목록 조회
     * TODO: 실제 데이터베이스나 bomiora.kr PHP 서버에서 데이터 가져오기 구현 필요
     */
    public List<ProductDTO> getProductsByCategory(String categoryId, String productKind, 
                                                  int page, int pageSize) {
        System.out.println("ProductService.getProductsByCategory 호출 - " +
                          "categoryId: " + categoryId + 
                          ", productKind: " + productKind +
                          ", page: " + page +
                          ", pageSize: " + pageSize);
        
        // TODO: 실제 구현 필요
        // 1. bomiora.kr PHP 서버의 /shop/list.php를 호출하여 HTML 파싱
        // 2. 또는 데이터베이스에서 직접 조회
        // 3. 또는 외부 API 호출
        
        // 임시로 더미 데이터 반환 (개발/테스트용)
        List<ProductDTO> products = new ArrayList<>();
        
        // 카테고리 이름 매핑
        String categoryName = getCategoryName(categoryId);
        
        // 더미 데이터 생성 (실제 데이터로 교체 필요)
        for (int i = 1; i <= pageSize && i <= 10; i++) {
            ProductDTO product = new ProductDTO();
            product.setId(categoryId + "_" + i);
            product.setName(categoryName + " 상품 " + i);
            product.setDescription(categoryName + " 카테고리의 " + i + "번째 상품입니다");
            product.setPrice(50000 + (i * 10000));
            product.setOriginalPrice(60000 + (i * 12000));
            product.setImageUrl(null); // 실제 이미지 URL로 교체 필요
            product.setCategoryId(categoryId);
            product.setCategoryName(categoryName);
            product.setProductKind(productKind);
            product.setIsNew(i % 3 == 0);
            product.setIsBest(i % 4 == 0);
            product.setStock(100 - i * 5);
            product.setRating(4.0 + (i % 5) * 0.2);
            product.setReviewCount(10 + i * 5);
            
            products.add(product);
        }
        
        System.out.println("상품 목록 생성 완료 - 개수: " + products.size());
        return products;
    }
    
    /**
     * 상품 상세 정보 조회
     */
    public ProductDTO getProductDetail(String productId) {
        System.out.println("ProductService.getProductDetail 호출 - productId: " + productId);
        
        // TODO: 실제 구현 필요
        // 1. bomiora.kr PHP 서버의 /shop/item.php?id={productId} 호출
        // 2. 또는 데이터베이스에서 직접 조회
        
        // 임시로 더미 데이터 반환
        ProductDTO product = new ProductDTO();
        product.setId(productId);
        product.setName("상품 상세 " + productId);
        product.setDescription("상품 ID: " + productId + "에 대한 상세 정보입니다");
        product.setPrice(50000);
        product.setOriginalPrice(60000);
        product.setImageUrl(null);
        product.setCategoryId("50");
        product.setCategoryName("건강/면역");
        product.setProductKind("prescription");
        product.setIsNew(true);
        product.setIsBest(false);
        product.setStock(100);
        product.setRating(4.5);
        product.setReviewCount(23);
        
        return product;
    }
    
    /**
     * 인기 상품 목록 조회
     */
    public List<ProductDTO> getPopularProducts(int limit) {
        System.out.println("ProductService.getPopularProducts 호출 - limit: " + limit);
        
        // TODO: 실제 구현 필요
        List<ProductDTO> products = new ArrayList<>();
        
        for (int i = 1; i <= limit; i++) {
            ProductDTO product = new ProductDTO();
            product.setId("popular_" + i);
            product.setName("인기 상품 " + i);
            product.setDescription("인기 상품 " + i + "번입니다");
            product.setPrice(50000 + i * 5000);
            product.setOriginalPrice(null);
            product.setImageUrl(null);
            product.setCategoryId("50");
            product.setCategoryName("인기 상품");
            product.setProductKind("general");
            product.setIsNew(false);
            product.setIsBest(true);
            product.setStock(50);
            product.setRating(4.8);
            product.setReviewCount(100 + i * 10);
            
            products.add(product);
        }
        
        return products;
    }
    
    /**
     * 신상품 목록 조회
     */
    public List<ProductDTO> getNewProducts(int limit) {
        System.out.println("ProductService.getNewProducts 호출 - limit: " + limit);
        
        // TODO: 실제 구현 필요
        List<ProductDTO> products = new ArrayList<>();
        
        for (int i = 1; i <= limit; i++) {
            ProductDTO product = new ProductDTO();
            product.setId("new_" + i);
            product.setName("신상품 " + i);
            product.setDescription("신상품 " + i + "번입니다");
            product.setPrice(40000 + i * 3000);
            product.setOriginalPrice(50000 + i * 4000);
            product.setImageUrl(null);
            product.setCategoryId("50");
            product.setCategoryName("신상품");
            product.setProductKind("general");
            product.setIsNew(true);
            product.setIsBest(false);
            product.setStock(200);
            product.setRating(4.0 + i * 0.1);
            product.setReviewCount(5 + i * 2);
            
            products.add(product);
        }
        
        return products;
    }
    
    /**
     * 카테고리 ID로 카테고리 이름 조회
     */
    private String getCategoryName(String categoryId) {
        switch (categoryId) {
            case "10": return "다이어트";
            case "20": return "디톡스";
            case "50": return "건강/면역";
            case "80": return "심신안정";
            default: return "기타";
        }
    }
}

