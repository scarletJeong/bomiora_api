package com.bomiora.shopping.product.service;

import com.bomiora.shopping.product.dto.ProductDTO;
import com.bomiora.shopping.product.entity.Product;
import com.bomiora.shopping.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * 카테고리별 상품 목록 조회 - 실제 데이터베이스에서 조회
     */
    public List<ProductDTO> getProductsByCategory(String categoryId, String productKind, 
                                                  int page, int pageSize) {
        System.out.println("ProductService.getProductsByCategory 호출 - " +
                          "categoryId: " + categoryId + 
                          ", productKind: " + productKind +
                          ", page: " + page +
                          ", pageSize: " + pageSize);
        
        try {
            // 페이지네이션 설정 (Spring Data는 0부터 시작)
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            
            Page<Product> productPage;
            
            // 상품 종류가 있으면 필터링, 없으면 카테고리만으로 조회
            if (productKind != null && !productKind.isEmpty()) {
                productPage = productRepository.findByCategoryIdAndProductKind(
                    categoryId, productKind, pageable
                );
            } else {
                productPage = productRepository.findByCategoryId(categoryId, pageable);
            }
            
            // Entity를 DTO로 변환
            List<ProductDTO> products = productPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            
            System.out.println("데이터베이스에서 상품 목록 조회 완료 - 개수: " + products.size());
            return products;
            
        } catch (Exception e) {
            System.out.println("데이터베이스 조회 실패: " + e.getMessage());
            e.printStackTrace();
            // 에러 발생 시 빈 리스트 반환
            return List.of();
        }
    }
    
    /**
     * Entity를 DTO로 변환
     */
    private ProductDTO convertToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setOriginalPrice(entity.getOriginalPrice());
        dto.setImageUrl(processImageUrl(entity));
        
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryName(getCategoryName(entity.getCategoryId()));
        dto.setProductKind(entity.getProductKind());
        dto.setIsNew(entity.getIsNew());
        dto.setIsBest(entity.getIsBest());
        dto.setStock(entity.getStock() != null ? entity.getStock() : 0);
        // BigDecimal을 Double로 변환
        if (entity.getRating() != null) {
            dto.setRating(entity.getRating().doubleValue());
        }
        dto.setReviewCount(entity.getReviewCount() != null ? entity.getReviewCount() : 0);
        
        // additionalInfo에 상세 정보 추가 (it_basic, it_prescription, it_takeway, it_package, it_point, it_point_type 등)
        java.util.Map<String, Object> additionalInfo = new java.util.HashMap<>();
        additionalInfo.put("it_explan", entity.getDescription()); // HTML 상세 설명
        additionalInfo.put("it_basic", entity.getBasicDescription());
        additionalInfo.put("it_prescription", entity.getPrescription());
        additionalInfo.put("it_takeway", entity.getTakeway());
        additionalInfo.put("it_package", entity.getPackageInfo());
        additionalInfo.put("it_point", entity.getPoint());
        additionalInfo.put("it_point_type", entity.getPointType());
        additionalInfo.put("it_option_subject", entity.getOptionSubject());
        additionalInfo.put("it_img2", null); // 필요시 추가
        additionalInfo.put("it_img3", null); // 필요시 추가
        dto.setAdditionalInfo(additionalInfo);
        
        return dto;
    }
    
    /**
     * 상품 상세 정보 조회 - 실제 데이터베이스에서 조회
     */
    public ProductDTO getProductDetail(String productId) {
        System.out.println("ProductService.getProductDetail 호출 - productId: " + productId);
        
        try {
            Product product = productRepository.findById(productId)
                .orElse(null);
            
            if (product == null) {
                System.out.println("상품을 찾을 수 없음 - productId: " + productId);
                return null;
            }
            
            System.out.println("데이터베이스에서 상품 상세 조회 완료 - productId: " + productId);
            return convertToDTO(product);
            
        } catch (Exception e) {
            System.out.println("데이터베이스 조회 실패: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 인기 상품 목록 조회 - 실제 데이터베이스에서 조회
     */
    public List<ProductDTO> getPopularProducts(int limit) {
        System.out.println("ProductService.getPopularProducts 호출 - limit: " + limit);
        
        try {
            Pageable pageable = PageRequest.of(0, limit);
            List<Product> products = productRepository.findBestProducts(pageable);
            
            System.out.println("데이터베이스에서 인기 상품 조회 완료 - 개수: " + products.size());
            return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            System.out.println("데이터베이스 조회 실패: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * 신상품 목록 조회 - 실제 데이터베이스에서 조회
     */
    public List<ProductDTO> getNewProducts(int limit) {
        System.out.println("ProductService.getNewProducts 호출 - limit: " + limit);
        
        try {
            Pageable pageable = PageRequest.of(0, limit);
            List<Product> products = productRepository.findNewProducts(pageable);
            
            System.out.println("데이터베이스에서 신상품 조회 완료 - 개수: " + products.size());
            return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            System.out.println("데이터베이스 조회 실패: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
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
    
    /**
     * 이미지 URL 처리
     * - it_flutter_image_url 우선 사용 (폴더 경로 기반)
     * - 없으면 it_img1 사용 (기존 이미지 URL)
     * 
     * @param entity Product 엔티티
     * @return 처리된 이미지 URL (상대 경로 또는 전체 URL)
     */
    private String processImageUrl(Product entity) {
        // Flutter용 이미지 URL 우선 처리
        if (entity.getFlutterImageUrl() != null && !entity.getFlutterImageUrl().trim().isEmpty()) {
            return buildFlutterImageUrl(entity.getFlutterImageUrl().trim(), entity.getId());
        }
        
        // 기존 이미지 URL 처리
        if (entity.getImageUrl() != null && !entity.getImageUrl().isEmpty()) {
            return normalizeImageUrl(entity.getImageUrl().trim());
        }
        
        return null;
    }
    
    /**
     * Flutter용 이미지 URL 생성
     * it_flutter_image_url에는 폴더 경로만 저장됨 (예: /data/products/1691479590/)
     * 실제 이미지는 용도에 따라 자동으로 조합:
     * - 리스트용: {folder_path}{product_id}_list.jpg
     * 
     * @param folderPath 폴더 경로
     * @param productId 상품 ID
     * @return 처리된 이미지 URL
     */
    private String buildFlutterImageUrl(String folderPath, String productId) {
        // 폴더 경로 정규화
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }
        
        // 절대 URL인 경우
        if (folderPath.startsWith("http://") || folderPath.startsWith("https://")) {
            String baseUrl = folderPath.endsWith("/") 
                ? folderPath.substring(0, folderPath.length() - 1) 
                : folderPath;
            return baseUrl + "/" + productId + "_list.jpg";
        }
        
        // 상대 경로인 경우: /data/products/1691479590/1691479590_list.jpg
        if (!folderPath.startsWith("/")) {
            folderPath = "/" + folderPath;
        }
        return folderPath + productId + "_list.jpg";
    }
    
    /**
     * 기존 이미지 URL 정규화
     * @param imagePath 이미지 경로
     * @return 정규화된 이미지 URL
     */
    private String normalizeImageUrl(String imagePath) {
        // 이미 전체 URL인 경우 그대로 반환
        if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
            return imagePath;
        }
        
        // 상대 경로 정규화: /data/item/...
        return imagePath.startsWith("/") ? imagePath : "/" + imagePath;
    }
}

