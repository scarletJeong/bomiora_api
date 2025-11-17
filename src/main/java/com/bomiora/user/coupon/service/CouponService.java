package com.bomiora.user.coupon.service;

import com.bomiora.user.coupon.entity.Coupon;
import com.bomiora.user.coupon.repository.CouponRepository;
import com.bomiora.user.review.entity.Review;
import com.bomiora.user.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CouponService {
    
    @Autowired
    private CouponRepository couponRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    /**
     * 사용자의 모든 쿠폰 조회
     */
    public List<Coupon> getUserCoupons(String userId) {
        try {
            System.out.println("🎫 쿠폰 목록 조회 시작 - userId: " + userId);
            
            // mb_id로 직접 조회 테스트
            List<Coupon> allCoupons = couponRepository.findAll();
            System.out.println("📊 전체 쿠폰 개수: " + allCoupons.size());
            if (!allCoupons.isEmpty()) {
                System.out.println("📋 첫 번째 쿠폰 정보:");
                Coupon firstCoupon = allCoupons.get(0);
                System.out.println("  - cp_no: " + firstCoupon.getNo());
                System.out.println("  - cp_id: " + firstCoupon.getId());
                System.out.println("  - mb_id: " + firstCoupon.getUserId());
            }
            
            List<Coupon> coupons = couponRepository.findByUserId(userId);
            System.out.println("✅ 쿠폰 목록 조회 완료 - userId: " + userId + ", 쿠폰 개수: " + coupons.size() + "개");
            
            if (coupons.isEmpty()) {
                System.out.println("⚠️ 해당 사용자의 쿠폰이 없습니다. userId: " + userId);
            } else {
                System.out.println("📋 조회된 쿠폰 목록:");
                for (Coupon c : coupons) {
                    System.out.println("  - cp_no: " + c.getNo() + ", cp_id: " + c.getId() + ", mb_id: " + c.getUserId());
                }
            }
            
            return coupons;
        } catch (Exception e) {
            System.out.println("❌ 쿠폰 목록 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
    
    /**
     * 사용 가능한 쿠폰 조회
     */
    public List<Coupon> getAvailableCoupons(String userId) {
        try {
            LocalDate today = LocalDate.now();
            System.out.println("🎫 사용가능한 쿠폰 조회 시작 - userId: " + userId + ", today: " + today);
            
            // 먼저 사용자의 모든 쿠폰 조회
            List<Coupon> allUserCoupons = couponRepository.findByUserId(userId);
            System.out.println("📋 사용자의 전체 쿠폰 개수: " + allUserCoupons.size());
            
            // 각 쿠폰의 상태 확인
            for (Coupon c : allUserCoupons) {
                System.out.println("📋 쿠폰 정보:");
                System.out.println("  - cp_no: " + c.getNo());
                System.out.println("  - cp_id: " + c.getId());
                System.out.println("  - cp_start: " + c.getStartDate());
                System.out.println("  - cp_end: " + c.getEndDate());
                System.out.println("  - od_id: " + c.getOrderId());
                System.out.println("  - today: " + today);
                
                boolean dateValid = (c.getStartDate() != null && c.getEndDate() != null &&
                                   !today.isBefore(c.getStartDate()) && !today.isAfter(c.getEndDate()));
                boolean notUsed = (c.getOrderId() == null || c.getOrderId() == 0);
                
                System.out.println("  - 날짜 유효: " + dateValid + " (시작일 <= 오늘 <= 종료일)");
                System.out.println("  - 미사용: " + notUsed);
                System.out.println("  - 사용 가능: " + (dateValid && notUsed));
            }
            
            List<Coupon> availableCoupons = couponRepository.findAvailableCoupons(userId, today);
            System.out.println("✅ 사용가능한 쿠폰 조회 완료 - userId: " + userId + ", 쿠폰 개수: " + availableCoupons.size() + "개");
            
            if (availableCoupons.isEmpty()) {
                System.out.println("⚠️ 사용 가능한 쿠폰이 없습니다.");
            } else {
                System.out.println("📋 사용 가능한 쿠폰 목록:");
                for (Coupon c : availableCoupons) {
                    System.out.println("  - cp_no: " + c.getNo() + ", cp_id: " + c.getId());
                }
            }
            
            return availableCoupons;
        } catch (Exception e) {
            System.out.println("❌ 사용가능한 쿠폰 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
    
    /**
     * 사용한 쿠폰 조회
     */
    public List<Coupon> getUsedCoupons(String userId) {
        try {
            return couponRepository.findUsedCoupons(userId);
        } catch (Exception e) {
            System.out.println("❌ 사용한 쿠폰 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
    
    /**
     * 만료된 쿠폰 조회
     */
    public List<Coupon> getExpiredCoupons(String userId) {
        try {
            LocalDate today = LocalDate.now();
            return couponRepository.findExpiredCoupons(userId, today);
        } catch (Exception e) {
            System.out.println("❌ 만료된 쿠폰 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
    
    /**
     * 쿠폰 등록
     */
    public java.util.Map<String, Object> registerCoupon(String userId, String couponCode) {
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        
        try {
            System.out.println("🎫 쿠폰 등록 시작 - userId: " + userId + ", code: " + couponCode);
            
            // 쿠폰 코드로 쿠폰 조회 (템플릿 쿠폰 조회)
            java.util.Optional<Coupon> couponOpt = couponRepository.findByCouponId(couponCode);
            
            if (couponOpt.isEmpty()) {
                result.put("success", false);
                result.put("message", "유효하지 않은 쿠폰 코드입니다.");
                return result;
            }
            
            Coupon coupon = couponOpt.get();
            
            // 이미 등록된 쿠폰인지 확인
            java.util.Optional<Coupon> existingCoupon = couponRepository.findByCouponIdAndUserId(couponCode, userId);
            if (existingCoupon.isPresent()) {
                result.put("success", false);
                result.put("message", "이미 등록된 쿠폰입니다.");
                return result;
            }
            
            // 쿠폰을 사용자에게 할당 (cp_id는 동일하게 유지)
            // 주의: 실제로는 쿠폰 템플릿에서 사용자 쿠폰을 생성하는 로직이 필요할 수 있음
            // 현재는 동일한 cp_id를 가진 쿠폰을 여러 사용자가 등록할 수 있다고 가정
            Coupon userCoupon = new Coupon();
            userCoupon.setId(coupon.getId()); // cp_id는 동일
            userCoupon.setSubject(coupon.getSubject());
            userCoupon.setMethod(coupon.getMethod());
            userCoupon.setTarget(coupon.getTarget());
            userCoupon.setUserId(userId);
            userCoupon.setZoneId(coupon.getZoneId());
            userCoupon.setStartDate(coupon.getStartDate());
            userCoupon.setEndDate(coupon.getEndDate());
            userCoupon.setPrice(coupon.getPrice());
            userCoupon.setType(coupon.getType());
            userCoupon.setTrunc(coupon.getTrunc());
            userCoupon.setMinimum(coupon.getMinimum());
            userCoupon.setMaximum(coupon.getMaximum());
            userCoupon.setDatetime(java.time.LocalDateTime.now());
            userCoupon.setOrderId(null); // 초기값: 사용 전
            
            couponRepository.save(userCoupon);
            
            System.out.println("✅ 쿠폰 등록 완료");
            result.put("success", true);
            result.put("message", "쿠폰이 등록되었습니다.");
            return result;
            
        } catch (Exception e) {
            System.out.println("❌ 쿠폰 등록 오류: " + e.getMessage());
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "쿠폰 등록 중 오류가 발생했습니다: " + e.getMessage());
            return result;
        }
    }
    
    /**
     * 도움쿠폰 다운로드
     * @param mbId 회원 ID
     * @param itId 제품 ID
     * @param isId 리뷰 ID
     * @return 쿠폰 다운로드 결과
     */
    @Transactional
    public Map<String, Object> downloadHelpCoupon(String mbId, String itId, Integer isId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 리뷰 조회 및 검증
            Review review = reviewRepository.findById(isId.longValue())
                    .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));
            
            // 2. 서포터 리뷰인지 확인
            if (!"supporter".equals(review.getIsRvkind())) {
                throw new RuntimeException("서포터 리뷰만 도움쿠폰을 다운로드할 수 있습니다.");
            }
            
            // 3. 승인된 리뷰인지 확인
            if (review.getIsConfirm() == null || review.getIsConfirm() != 1) {
                throw new RuntimeException("승인된 리뷰만 도움쿠폰을 다운로드할 수 있습니다.");
            }
            
            // 4. 중복 다운로드 체크 (is_id로)
            boolean alreadyDownloaded = couponRepository.existsByUserIdAndReviewId(mbId, isId.longValue());
            if (alreadyDownloaded) {
                throw new RuntimeException("이미 다운로드하신 쿠폰입니다.");
            }
            
            // 5. 쿠폰 ID 생성
            String cpId = "HELP_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            
            // 6. 쿠폰 제목 생성
            String reviewerName = review.getIsName() != null ? review.getIsName() : "익명";
            String productName = review.getItName() != null ? 
                    (review.getItName().length() > 10 ? review.getItName().substring(0, 10) + "..." : review.getItName()) : 
                    "제품";
            String cpSubject = "[도움쿠폰] " + reviewerName + "님의 " + productName + " 할인쿠폰 (5%)";
            
            // 7. 쿠폰 생성
            Coupon coupon = new Coupon();
            coupon.setId(cpId);
            coupon.setSubject(cpSubject);
            coupon.setMethod(0);  // 개별 상품 할인
            coupon.setTarget(itId);
            coupon.setUserId(mbId);
            
            // 날짜 설정 (오늘부터 7일)
            LocalDate today = LocalDate.now();
            coupon.setStartDate(today);
            coupon.setEndDate(today.plusDays(6)); // 오늘 포함 7일
            
            coupon.setType(1);         // 정률 할인
            coupon.setPrice(5);        // 5%
            coupon.setTrunc(1);        // 1원 단위
            coupon.setMinimum(5000);   // 최소 주문금액
            coupon.setMaximum(5000);   // 최대 할인금액
            coupon.setDatetime(LocalDateTime.now());
            coupon.setReviewId(isId.longValue()); // 리뷰 ID 저장
            coupon.setInfluencerId("");  // 빈 문자열 (도움쿠폰은 인플루언서가 아님)
            coupon.setZoneId(0);  // 기본값
            coupon.setOrderId(0L);  // 0 = 사용하지 않은 쿠폰
            
            // 8. 쿠폰 저장
            couponRepository.save(coupon);
            
            // 9. 리뷰의 다운로드 카운트 증가
            review.setCzDownload((review.getCzDownload() != null ? review.getCzDownload() : 0) + 1);
            reviewRepository.save(review);
            
            int downloadCount = review.getCzDownload();
            
            System.out.println("✅ 도움쿠폰 다운로드 완료 - cpId: " + cpId + ", 다운로드 수: " + downloadCount);
            
            result.put("success", true);
            result.put("message", "쿠폰 발급이 완료되었습니다.\n지금 바로 할인 받고 구매해보세요!\n쿠폰은 [마이페이지 > 내쿠폰] 또는 결제 전 [쿠폰 선택]에서 확인할 수 있습니다.");
            result.put("downloadCount", downloadCount);
            result.put("cpId", cpId);
            
            return result;
            
        } catch (RuntimeException e) {
            throw e; // 비즈니스 로직 예외는 그대로 전달
        } catch (Exception e) {
            System.out.println("❌ 도움쿠폰 다운로드 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("쿠폰 다운로드 중 오류가 발생했습니다.");
        }
    }
}

