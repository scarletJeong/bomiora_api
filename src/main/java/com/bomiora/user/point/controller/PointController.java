package com.bomiora.user.point.controller;

import com.bomiora.user.point.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class PointController {
    
    @Autowired
    private PointService pointService;
    
    /**
     * 사용자 포인트 조회
     * GET /api/user/point?mb_id={userId}
     */
    @GetMapping("/point")
    public ResponseEntity<Map<String, Object>> getUserPoint(
            @RequestParam("mb_id") String userId) {
        try {
            System.out.println("💎 포인트 조회 API 호출 - userId: " + userId);
            
            Integer point = pointService.getUserPoint(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of(
                "po_mb_point", point,
                "point", point
            ));
            
            System.out.println("✅ 포인트 조회 API 응답 - point: " + point);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 포인트 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "포인트 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 사용자 포인트 내역 조회
     * GET /api/user/point/history?mb_id={userId}
     */
    @GetMapping("/point/history")
    public ResponseEntity<Map<String, Object>> getPointHistory(
            @RequestParam("mb_id") String userId) {
        try {
            System.out.println("📋 포인트 내역 조회 API 호출 - userId: " + userId);
            
            java.util.List<com.bomiora.user.point.entity.Point> history = pointService.getPointHistory(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", history.stream().map(p -> {
                Map<String, Object> pointMap = new HashMap<>();
                pointMap.put("po_id", p.getId());
                pointMap.put("mb_id", p.getUserId());
                pointMap.put("po_datetime", p.getDatetime() != null ? p.getDatetime().toString() : null);
                pointMap.put("po_content", p.getContent());
                pointMap.put("po_point", p.getPoint());
                pointMap.put("po_use_point", p.getUsePoint());
                pointMap.put("po_expired", p.getExpired());
                pointMap.put("po_expire_date", p.getExpireDate() != null ? p.getExpireDate().toString() : null);
                pointMap.put("po_use_date", p.getUseDate() != null ? p.getUseDate().toString() : null);
                pointMap.put("po_mb_point", p.getMbPoint());
                pointMap.put("po_rel_table", p.getRelTable());
                pointMap.put("po_rel_id", p.getRelId());
                pointMap.put("po_rel_action", p.getRelAction());
                return pointMap;
            }).collect(java.util.stream.Collectors.toList()));
            
            System.out.println("✅ 포인트 내역 조회 API 응답 - 내역 개수: " + history.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 포인트 내역 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "포인트 내역 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
