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
}
