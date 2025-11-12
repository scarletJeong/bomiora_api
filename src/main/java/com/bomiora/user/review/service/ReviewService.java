package com.bomiora.user.review.service;

import com.bomiora.user.review.dto.ReviewRequestDTO;
import com.bomiora.user.review.dto.ReviewResponseDTO;
import com.bomiora.user.review.dto.ReviewStatsDTO;
import com.bomiora.user.review.entity.Review;
import com.bomiora.user.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    /**
     * 리뷰 작성
     */
    @Transactional
    public Map<String, Object> createReview(ReviewRequestDTO request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 이미 해당 주문에 대한 리뷰가 있는지 확인
            if (request.getOdId() != null && 
                reviewRepository.existsByMbIdAndOdId(request.getMbId(), request.getOdId())) {
                result.put("success", false);
                result.put("message", "이미 해당 주문에 대한 리뷰를 작성하셨습니다.");
                return result;
            }
            
            // Review 엔티티 생성
            Review review = new Review();
            review.setMbId(request.getMbId());
            review.setOdId(request.getOdId());
            review.setItId(request.getItId());
            review.setIsName(request.getIsName());
            review.setIsTime(LocalDateTime.now());
            review.setIsConfirm(0); // 기본값: 미승인 (관리자 승인 필요)
            
            // 평점
            review.setIsScore1(request.getIsScore1() != null ? request.getIsScore1() : 0);
            review.setIsScore2(request.getIsScore2() != null ? request.getIsScore2() : 0);
            review.setIsScore3(request.getIsScore3() != null ? request.getIsScore3() : 0);
            review.setIsScore4(request.getIsScore4() != null ? request.getIsScore4() : 0);
            
            // 리뷰 종류
            review.setIsRvkind(request.getIsRvkind() != null ? request.getIsRvkind() : "general");
            
            // 추천 여부
            review.setIsRecommend(request.getIsRecommend() != null ? request.getIsRecommend() : "y");
            review.setIsGood(0); // 초기값
            
            // 리뷰 내용
            review.setIsPositiveReviewText(request.getIsPositiveReviewText());
            review.setIsNegativeReviewText(request.getIsNegativeReviewText());
            review.setIsMoreReviewText(request.getIsMoreReviewText());
            
            // 이미지 설정 (최대 10개)
            List<String> images = request.getImages();
            if (images != null && !images.isEmpty()) {
                if (images.size() > 0) review.setIsImg1(images.get(0));
                if (images.size() > 1) review.setIsImg2(images.get(1));
                if (images.size() > 2) review.setIsImg3(images.get(2));
                if (images.size() > 3) review.setIsImg4(images.get(3));
                if (images.size() > 4) review.setIsImg5(images.get(4));
                if (images.size() > 5) review.setIsImg6(images.get(5));
                if (images.size() > 6) review.setIsImg7(images.get(6));
                if (images.size() > 7) review.setIsImg8(images.get(7));
                if (images.size() > 8) review.setIsImg9(images.get(8));
                if (images.size() > 9) review.setIsImg10(images.get(9));
            }
            
            // 사용자 정보
            review.setIsBirthday(request.getIsBirthday());
            review.setIsWeight(request.getIsWeight());
            review.setIsHeight(request.getIsHeight());
            review.setIsPayMthod(request.getIsPayMthod());
            review.setIsOutageNum(request.getIsOutageNum());
            
            // 저장
            Review savedReview = reviewRepository.save(review);
            
            result.put("success", true);
            result.put("message", "리뷰가 성공적으로 작성되었습니다. 관리자 승인 후 게시됩니다.");
            result.put("review", new ReviewResponseDTO(savedReview));
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "리뷰 작성 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 특정 상품의 리뷰 목록 조회
     */
    public Map<String, Object> getProductReviews(String itId, String rvkind, int page, int size) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Review> reviewPage;
            
            if (rvkind != null && !rvkind.isEmpty()) {
                reviewPage = reviewRepository.findByItIdAndIsRvkindAndIsConfirmOrderByIsIdDesc(
                    itId, rvkind, 1, pageable);
            } else {
                reviewPage = reviewRepository.findByItIdAndIsConfirmOrderByIsIdDesc(
                    itId, 1, pageable);
            }
            
            List<ReviewResponseDTO> reviews = reviewPage.getContent().stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
            
            result.put("success", true);
            result.put("reviews", reviews);
            result.put("currentPage", reviewPage.getNumber());
            result.put("totalPages", reviewPage.getTotalPages());
            result.put("totalElements", reviewPage.getTotalElements());
            result.put("hasNext", reviewPage.hasNext());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "리뷰 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 특정 회원의 리뷰 목록 조회
     */
    public Map<String, Object> getMemberReviews(String mbId, int page, int size) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Review> reviewPage = reviewRepository.findByMbIdAndIsConfirmOrderByIsIdDesc(
                mbId, 1, pageable);
            
            List<ReviewResponseDTO> reviews = reviewPage.getContent().stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
            
            result.put("success", true);
            result.put("reviews", reviews);
            result.put("currentPage", reviewPage.getNumber());
            result.put("totalPages", reviewPage.getTotalPages());
            result.put("totalElements", reviewPage.getTotalElements());
            result.put("hasNext", reviewPage.hasNext());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "리뷰 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 전체 리뷰 목록 조회
     */
    public Map<String, Object> getAllReviews(String rvkind, int page, int size) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Review> reviewPage = reviewRepository.findByIsConfirmOrderByIsIdDesc(1, pageable);
            
            List<ReviewResponseDTO> reviews = reviewPage.getContent().stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
            
            result.put("success", true);
            result.put("reviews", reviews);
            result.put("currentPage", reviewPage.getNumber());
            result.put("totalPages", reviewPage.getTotalPages());
            result.put("totalElements", reviewPage.getTotalElements());
            result.put("hasNext", reviewPage.hasNext());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "리뷰 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 특정 상품의 리뷰 통계
     */
    public Map<String, Object> getProductReviewStats(String itId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            long totalCount = reviewRepository.countByItIdAndIsConfirm(itId, 1);
            Double averageScore = reviewRepository.getAverageScoreByItId(itId);
            
            ReviewStatsDTO stats = new ReviewStatsDTO();
            stats.setTotalCount(totalCount);
            stats.setAverageScore(averageScore != null ? averageScore : 0.0);
            
            result.put("success", true);
            result.put("stats", stats);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "리뷰 통계 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 리뷰 상세 조회
     */
    public Map<String, Object> getReviewById(Long isId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Review review = reviewRepository.findById(isId).orElse(null);
            
            if (review == null) {
                result.put("success", false);
                result.put("message", "리뷰를 찾을 수 없습니다.");
                return result;
            }
            
            result.put("success", true);
            result.put("review", new ReviewResponseDTO(review));
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "리뷰 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 리뷰 수정
     */
    @Transactional
    public Map<String, Object> updateReview(Long isId, ReviewRequestDTO request) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Review review = reviewRepository.findById(isId).orElse(null);
            
            if (review == null) {
                result.put("success", false);
                result.put("message", "리뷰를 찾을 수 없습니다.");
                return result;
            }
            
            // 작성자 확인
            if (!review.getMbId().equals(request.getMbId())) {
                result.put("success", false);
                result.put("message", "리뷰를 수정할 권한이 없습니다.");
                return result;
            }
            
            // 평점 업데이트
            if (request.getIsScore1() != null) review.setIsScore1(request.getIsScore1());
            if (request.getIsScore2() != null) review.setIsScore2(request.getIsScore2());
            if (request.getIsScore3() != null) review.setIsScore3(request.getIsScore3());
            if (request.getIsScore4() != null) review.setIsScore4(request.getIsScore4());
            
            // 리뷰 내용 업데이트
            if (request.getIsPositiveReviewText() != null) 
                review.setIsPositiveReviewText(request.getIsPositiveReviewText());
            if (request.getIsNegativeReviewText() != null) 
                review.setIsNegativeReviewText(request.getIsNegativeReviewText());
            if (request.getIsMoreReviewText() != null) 
                review.setIsMoreReviewText(request.getIsMoreReviewText());
            
            // 추천 여부
            if (request.getIsRecommend() != null) 
                review.setIsRecommend(request.getIsRecommend());
            
            // 이미지 업데이트
            List<String> images = request.getImages();
            if (images != null) {
                // 기존 이미지 초기화
                review.setIsImg1(null);
                review.setIsImg2(null);
                review.setIsImg3(null);
                review.setIsImg4(null);
                review.setIsImg5(null);
                review.setIsImg6(null);
                review.setIsImg7(null);
                review.setIsImg8(null);
                review.setIsImg9(null);
                review.setIsImg10(null);
                
                // 새 이미지 설정
                if (images.size() > 0) review.setIsImg1(images.get(0));
                if (images.size() > 1) review.setIsImg2(images.get(1));
                if (images.size() > 2) review.setIsImg3(images.get(2));
                if (images.size() > 3) review.setIsImg4(images.get(3));
                if (images.size() > 4) review.setIsImg5(images.get(4));
                if (images.size() > 5) review.setIsImg6(images.get(5));
                if (images.size() > 6) review.setIsImg7(images.get(6));
                if (images.size() > 7) review.setIsImg8(images.get(7));
                if (images.size() > 8) review.setIsImg9(images.get(8));
                if (images.size() > 9) review.setIsImg10(images.get(9));
            }
            
            Review updatedReview = reviewRepository.save(review);
            
            result.put("success", true);
            result.put("message", "리뷰가 성공적으로 수정되었습니다.");
            result.put("review", new ReviewResponseDTO(updatedReview));
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "리뷰 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 리뷰 삭제
     */
    @Transactional
    public Map<String, Object> deleteReview(Long isId, String mbId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Review review = reviewRepository.findById(isId).orElse(null);
            
            if (review == null) {
                result.put("success", false);
                result.put("message", "리뷰를 찾을 수 없습니다.");
                return result;
            }
            
            // 작성자 확인
            if (!review.getMbId().equals(mbId)) {
                result.put("success", false);
                result.put("message", "리뷰를 삭제할 권한이 없습니다.");
                return result;
            }
            
            reviewRepository.delete(review);
            
            result.put("success", true);
            result.put("message", "리뷰가 성공적으로 삭제되었습니다.");
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "리뷰 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 리뷰 도움됨 증가
     */
    @Transactional
    public Map<String, Object> incrementReviewHelpful(Long isId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Review review = reviewRepository.findById(isId).orElse(null);
            
            if (review == null) {
                result.put("success", false);
                result.put("message", "리뷰를 찾을 수 없습니다.");
                return result;
            }
            
            int currentGood = review.getIsGood() != null ? review.getIsGood() : 0;
            review.setIsGood(currentGood + 1);
            reviewRepository.save(review);
            
            result.put("success", true);
            result.put("message", "도움이 돼요가 증가했습니다.");
            result.put("isGood", review.getIsGood());
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "처리 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 주문에 대한 리뷰 작성 여부 확인
     */
    public Map<String, Object> checkReviewExists(String mbId, Long odId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            boolean exists = reviewRepository.existsByMbIdAndOdId(mbId, odId);
            
            result.put("success", true);
            result.put("exists", exists);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "확인 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
}

