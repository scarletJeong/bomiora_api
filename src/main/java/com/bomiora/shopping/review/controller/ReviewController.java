package com.bomiora.shopping.review.controller;

import com.bomiora.shopping.review.entity.Review;
import com.bomiora.shopping.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    /**
     * 제품별 리뷰 목록 조회
     * GET /api/reviews/product?it_id={productId}&is_rvkind={reviewKind}&page={page}&pageSize={pageSize}
     */
    @GetMapping("/product")
    public ResponseEntity<Map<String, Object>> getProductReviews(
            @RequestParam("it_id") String productId,
            @RequestParam(required = false) String is_rvkind,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        try {
            System.out.println("🔍 리뷰 목록 조회 API 호출 - productId: " + productId + 
                             ", reviewKind: " + is_rvkind + 
                             ", page: " + page + 
                             ", pageSize: " + pageSize);
            
            List<Review> reviews = reviewService.getProductReviews(productId, is_rvkind, page, pageSize);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reviews);
            response.put("total", reviews.size());
            response.put("page", page);
            response.put("pageSize", pageSize);
            
            System.out.println("✅ 리뷰 목록 조회 API 응답 - 개수: " + reviews.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 리뷰 목록 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "리뷰 목록 조회 실패: " + e.getMessage());
            response.put("data", List.of());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 제품별 리뷰 통계 조회
     * GET /api/reviews/stats?it_id={productId}
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getReviewStats(
            @RequestParam("it_id") String productId) {
        try {
            System.out.println("📊 리뷰 통계 조회 API 호출 - productId: " + productId);
            
            Map<String, Object> stats = reviewService.getReviewStats(productId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", stats);
            
            System.out.println("✅ 리뷰 통계 조회 API 응답");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 리뷰 통계 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "리뷰 통계 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
