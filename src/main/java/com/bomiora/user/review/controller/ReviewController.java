package com.bomiora.user.review.controller;

import com.bomiora.user.review.dto.ReviewRequestDTO;
import com.bomiora.user.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 사용자 리뷰 컨트롤러
 */
@RestController
@RequestMapping("/api/user/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    /**
     * 리뷰 작성
     * POST /api/user/reviews
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createReview(@RequestBody ReviewRequestDTO request) {
        Map<String, Object> result = reviewService.createReview(request);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 특정 상품의 리뷰 목록 조회
     * GET /api/user/reviews/product/{itId}
     */
    @GetMapping("/product/{itId}")
    public ResponseEntity<Map<String, Object>> getProductReviews(
            @PathVariable String itId,
            @RequestParam(required = false) String rvkind,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> result = reviewService.getProductReviews(itId, rvkind, page, size);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 특정 회원의 리뷰 목록 조회
     * GET /api/user/reviews/member/{mbId}
     */
    @GetMapping("/member/{mbId}")
    public ResponseEntity<Map<String, Object>> getMemberReviews(
            @PathVariable String mbId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> result = reviewService.getMemberReviews(mbId, page, size);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 전체 리뷰 목록 조회
     * GET /api/user/reviews
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllReviews(
            @RequestParam(required = false) String rvkind,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Map<String, Object> result = reviewService.getAllReviews(rvkind, page, size);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 특정 상품의 리뷰 통계
     * GET /api/user/reviews/product/{itId}/stats
     */
    @GetMapping("/product/{itId}/stats")
    public ResponseEntity<Map<String, Object>> getProductReviewStats(@PathVariable String itId) {
        Map<String, Object> result = reviewService.getProductReviewStats(itId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 리뷰 상세 조회
     * GET /api/user/reviews/{isId}
     */
    @GetMapping("/{isId}")
    public ResponseEntity<Map<String, Object>> getReviewById(@PathVariable Long isId) {
        Map<String, Object> result = reviewService.getReviewById(isId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 리뷰 수정
     * PUT /api/user/reviews/{isId}
     */
    @PutMapping("/{isId}")
    public ResponseEntity<Map<String, Object>> updateReview(
            @PathVariable Long isId,
            @RequestBody ReviewRequestDTO request) {
        Map<String, Object> result = reviewService.updateReview(isId, request);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 리뷰 삭제
     * DELETE /api/user/reviews/{isId}
     */
    @DeleteMapping("/{isId}")
    public ResponseEntity<Map<String, Object>> deleteReview(
            @PathVariable Long isId,
            @RequestParam String mbId) {
        Map<String, Object> result = reviewService.deleteReview(isId, mbId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 리뷰 도움됨 증가
     * POST /api/user/reviews/{isId}/helpful
     */
    @PostMapping("/{isId}/helpful")
    public ResponseEntity<Map<String, Object>> incrementReviewHelpful(@PathVariable Long isId) {
        Map<String, Object> result = reviewService.incrementReviewHelpful(isId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 주문에 대한 리뷰 작성 여부 확인
     * GET /api/user/reviews/check
     */
    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkReviewExists(
            @RequestParam String mbId,
            @RequestParam Long odId) {
        Map<String, Object> result = reviewService.checkReviewExists(mbId, odId);
        return ResponseEntity.ok(result);
    }
}

