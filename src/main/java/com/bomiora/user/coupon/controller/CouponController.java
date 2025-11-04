package com.bomiora.user.coupon.controller;

import com.bomiora.user.coupon.entity.Coupon;
import com.bomiora.user.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class CouponController {
    
    @Autowired
    private CouponService couponService;
    
    /**
     * 사용자의 모든 쿠폰 조회
     * GET /api/user/coupons?mb_id={userId}
     */
    @GetMapping("/coupons")
    public ResponseEntity<Map<String, Object>> getUserCoupons(
            @RequestParam("mb_id") String userId) {
        try {
            System.out.println("🎫 쿠폰 목록 조회 API 호출 - userId: " + userId);
            
            List<Coupon> coupons = couponService.getUserCoupons(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", coupons.stream().map(c -> {
                Map<String, Object> couponMap = new HashMap<>();
                couponMap.put("cp_no", c.getNo());
                couponMap.put("cp_id", c.getId());
                couponMap.put("cp_subject", c.getSubject());
                couponMap.put("cp_method", c.getMethod());
                couponMap.put("cp_target", c.getTarget());
                couponMap.put("mb_id", c.getUserId());
                couponMap.put("cz_id", c.getZoneId());
                couponMap.put("cp_start", c.getStartDate() != null ? c.getStartDate().toString() : null);
                couponMap.put("cp_end", c.getEndDate() != null ? c.getEndDate().toString() : null);
                couponMap.put("cp_price", c.getPrice());
                couponMap.put("cp_type", c.getType());
                couponMap.put("cp_trunc", c.getTrunc());
                couponMap.put("cp_minimum", c.getMinimum());
                couponMap.put("cp_maximum", c.getMaximum());
                couponMap.put("od_id", c.getOrderId());
                couponMap.put("cp_datetime", c.getDatetime() != null ? c.getDatetime().toString() : null);
                return couponMap;
            }).collect(java.util.stream.Collectors.toList()));
            
            System.out.println("✅ 쿠폰 목록 조회 API 응답 - 쿠폰 개수: " + coupons.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 쿠폰 목록 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "쿠폰 목록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 사용 가능한 쿠폰 조회
     * GET /api/user/coupons/available?mb_id={userId}
     */
    @GetMapping("/coupons/available")
    public ResponseEntity<Map<String, Object>> getAvailableCoupons(
            @RequestParam("mb_id") String userId) {
        try {
            List<Coupon> coupons = couponService.getAvailableCoupons(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", _convertCouponsToMap(coupons));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "사용가능한 쿠폰 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 사용한 쿠폰 조회
     * GET /api/user/coupons/used?mb_id={userId}
     */
    @GetMapping("/coupons/used")
    public ResponseEntity<Map<String, Object>> getUsedCoupons(
            @RequestParam("mb_id") String userId) {
        try {
            List<Coupon> coupons = couponService.getUsedCoupons(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", _convertCouponsToMap(coupons));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "사용한 쿠폰 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 만료된 쿠폰 조회
     * GET /api/user/coupons/expired?mb_id={userId}
     */
    @GetMapping("/coupons/expired")
    public ResponseEntity<Map<String, Object>> getExpiredCoupons(
            @RequestParam("mb_id") String userId) {
        try {
            List<Coupon> coupons = couponService.getExpiredCoupons(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", _convertCouponsToMap(coupons));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "만료된 쿠폰 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 쿠폰 등록
     * POST /api/user/coupons/register
     */
    @PostMapping("/coupons/register")
    public ResponseEntity<Map<String, Object>> registerCoupon(
            @RequestBody Map<String, String> request) {
        try {
            String userId = request.get("mb_id");
            String couponCode = request.get("cp_id");
            
            System.out.println("🎫 쿠폰 등록 API 호출 - userId: " + userId + ", code: " + couponCode);
            
            Map<String, Object> result = couponService.registerCoupon(userId, couponCode);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println("❌ 쿠폰 등록 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "쿠폰 등록 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Coupon 리스트를 Map 리스트로 변환
     */
    private List<Map<String, Object>> _convertCouponsToMap(List<Coupon> coupons) {
        return coupons.stream().map(c -> {
            Map<String, Object> couponMap = new HashMap<>();
            couponMap.put("cp_no", c.getNo());
            couponMap.put("cp_id", c.getId());
            couponMap.put("cp_subject", c.getSubject());
            couponMap.put("cp_method", c.getMethod());
            couponMap.put("cp_target", c.getTarget());
            couponMap.put("mb_id", c.getUserId());
            couponMap.put("cz_id", c.getZoneId());
            couponMap.put("cp_start", c.getStartDate() != null ? c.getStartDate().toString() : null);
            couponMap.put("cp_end", c.getEndDate() != null ? c.getEndDate().toString() : null);
            couponMap.put("cp_price", c.getPrice());
            couponMap.put("cp_type", c.getType());
            couponMap.put("cp_trunc", c.getTrunc());
            couponMap.put("cp_minimum", c.getMinimum());
            couponMap.put("cp_maximum", c.getMaximum());
            couponMap.put("od_id", c.getOrderId());
            couponMap.put("cp_datetime", c.getDatetime() != null ? c.getDatetime().toString() : null);
            return couponMap;
        }).collect(java.util.stream.Collectors.toList());
    }
}

