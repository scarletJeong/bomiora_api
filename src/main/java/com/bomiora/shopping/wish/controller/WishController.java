package com.bomiora.shopping.wish.controller;

import com.bomiora.shopping.product.entity.Product;
import com.bomiora.shopping.product.repository.ProductRepository;
import com.bomiora.shopping.wish.entity.Wish;
import com.bomiora.shopping.wish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wish")
public class WishController {
    
    @Autowired
    private WishService wishService;
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * 찜하기 추가/제거 (토글)
     * POST /api/wish/toggle
     */
    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleWish(@RequestBody Map<String, String> request) {
        try {
            String mbId = request.get("mb_id");
            String itId = request.get("it_id");
            
            if (mbId == null || itId == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "mb_id와 it_id가 필요합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            boolean isAdded = wishService.toggleWish(mbId, itId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("is_wished", isAdded);
            response.put("message", isAdded ? "찜하기가 추가되었습니다." : "찜하기가 제거되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("찜하기 토글 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "찜하기 처리 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 찜하기 상태 확인
     * GET /api/wish/check?mb_id={mbId}&it_id={itId}
     */
    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkWish(
            @RequestParam String mb_id,
            @RequestParam String it_id) {
        try {
            boolean isWished = wishService.isWished(mb_id, it_id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("is_wished", isWished);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("찜하기 확인 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "찜하기 확인 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 찜목록 조회
     * GET /api/wish/list?mb_id={mbId}&category={category}
     * category: "all" (전체), "prescription" (비대면 진료), "product" (제품)
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getWishList(
            @RequestParam String mb_id,
            @RequestParam(required = false, defaultValue = "all") String category) {
        try {
            List<Wish> wishes = wishService.getWishList(mb_id);
            
            // 상품 정보와 함께 변환
            List<Map<String, Object>> wishList = wishes.stream()
                .map(wish -> {
                    Map<String, Object> wishMap = new HashMap<>();
                    wishMap.put("wi_id", wish.getWiId());
                    wishMap.put("it_id", wish.getItId());
                    wishMap.put("wi_time", wish.getWiTime());
                    
                    // 상품 정보 조회
                    Product product = productRepository.findById(wish.getItId()).orElse(null);
                    if (product != null) {
                        wishMap.put("product_name", product.getName());
                        wishMap.put("product_price", product.getPrice());
                        wishMap.put("product_kind", product.getProductKind());
                        wishMap.put("image_url", product.getFlutterImageUrl() != null ? 
                            product.getFlutterImageUrl() : product.getImageUrl());
                        wishMap.put("it_img", product.getImageUrl());
                        wishMap.put("it_img1", product.getImageUrl());
                    }
                    
                    return wishMap;
                })
                .filter(wishMap -> {
                    // 카테고리 필터링
                    if (category.equals("all")) {
                        return true;
                    }
                    String productKind = (String) wishMap.get("product_kind");
                    if (category.equals("prescription")) {
                        return productKind != null && productKind.equals("prescription");
                    } else if (category.equals("product")) {
                        return productKind != null && productKind.equals("general");
                    }
                    return true;
                })
                .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", wishList);
            response.put("count", wishList.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("찜목록 조회 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "찜목록 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 찜하기 삭제
     * DELETE /api/wish/remove
     */
    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, Object>> removeWish(@RequestBody Map<String, String> request) {
        try {
            String mbId = request.get("mb_id");
            String itId = request.get("it_id");
            
            if (mbId == null || itId == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "mb_id와 it_id가 필요합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            wishService.removeWish(mbId, itId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "찜하기가 삭제되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("찜하기 삭제 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "찜하기 삭제 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

