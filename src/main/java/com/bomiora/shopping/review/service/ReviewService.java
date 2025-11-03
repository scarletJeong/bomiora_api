package com.bomiora.shopping.review.service;

import com.bomiora.shopping.review.entity.Review;
import com.bomiora.shopping.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    /**
     * 제품별 리뷰 목록 조회
     * 
     * @param productId 제품 ID
     * @param reviewKind 리뷰 종류 ('general', 'supporter', null=전체)
     * @param page 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 크기
     * @return 리뷰 목록
     */
    public List<Review> getProductReviews(String productId, String reviewKind, int page, int pageSize) {
        try {
            System.out.println("🔍 리뷰 목록 조회 시작 - productId: " + productId + ", reviewKind: " + reviewKind);
            
            List<Review> allReviews;
            if (reviewKind != null && !reviewKind.isEmpty()) {
                allReviews = reviewRepository.findByProductIdAndReviewKind(productId, reviewKind);
            } else {
                allReviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
            }
            
            // 페이지네이션 적용
            int start = (page - 1) * pageSize;
            int end = Math.min(start + pageSize, allReviews.size());
            
            if (start >= allReviews.size()) {
                System.out.println("✅ 리뷰 목록 조회 완료 - 개수: 0 (페이지 범위 초과)");
                return List.of();
            }
            
            List<Review> reviews = allReviews.subList(start, end);
            
            System.out.println("✅ 리뷰 목록 조회 완료 - 개수: " + reviews.size() + " / 전체: " + allReviews.size());
            return reviews;
        } catch (Exception e) {
            System.out.println("❌ 리뷰 목록 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * 제품별 리뷰 통계 조회
     * 
     * @param productId 제품 ID
     * @return 리뷰 통계 정보
     */
    public Map<String, Object> getReviewStats(String productId) {
        try {
            System.out.println("📊 리뷰 통계 조회 시작 - productId: " + productId);
            
            List<Review> allReviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
            
            if (allReviews.isEmpty()) {
                return Map.of(
                    "averageScore", 0.0,
                    "recommendCount", 0,
                    "totalCount", 0
                );
            }
            
            double totalScore = 0;
            int scoreCount = 0;
            int recommendCount = 0;
            
            // 각 점수별 통계
            int score1Count = 0, score1Sum = 0;
            int score2Count = 0, score2Sum = 0;
            int score3Count = 0, score3Sum = 0;
            int score4Count = 0, score4Sum = 0;
            
            for (Review review : allReviews) {
                Double avgScore = review.getAverageScore();
                if (avgScore > 0) {
                    totalScore += avgScore;
                    scoreCount++;
                }
                
                if ("y".equalsIgnoreCase(review.getRecommend())) {
                    recommendCount++;
                }
                
                // 각 점수별 합산
                if (review.getScore1() != null && review.getScore1() > 0) {
                    score1Sum += review.getScore1();
                    score1Count++;
                }
                if (review.getScore2() != null && review.getScore2() > 0) {
                    score2Sum += review.getScore2();
                    score2Count++;
                }
                if (review.getScore3() != null && review.getScore3() > 0) {
                    score3Sum += review.getScore3();
                    score3Count++;
                }
                if (review.getScore4() != null && review.getScore4() > 0) {
                    score4Sum += review.getScore4();
                    score4Count++;
                }
            }
            
            double averageScore = scoreCount > 0 ? totalScore / scoreCount : 0.0;
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("averageScore", averageScore);
            stats.put("recommendCount", recommendCount);
            stats.put("totalCount", allReviews.size());
            
            // 각 점수별 평균
            stats.put("score1Avg", score1Count > 0 ? (double) score1Sum / score1Count : 0.0);
            stats.put("score2Avg", score2Count > 0 ? (double) score2Sum / score2Count : 0.0);
            stats.put("score3Avg", score3Count > 0 ? (double) score3Sum / score3Count : 0.0);
            stats.put("score4Avg", score4Count > 0 ? (double) score4Sum / score4Count : 0.0);
            
            System.out.println("✅ 리뷰 통계 조회 완료 - 평균: " + averageScore + ", 총 개수: " + allReviews.size());
            return stats;
        } catch (Exception e) {
            System.out.println("❌ 리뷰 통계 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return Map.of(
                "averageScore", 0.0,
                "recommendCount", 0,
                "totalCount", 0
            );
        }
    }
}
