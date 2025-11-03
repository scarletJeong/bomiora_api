package com.bomiora.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 설정 정보 조회 (cf_use_point 등)
     * GET /api/config
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getConfig() {
        try {
            System.out.println("⚙️ 설정 조회 API 호출");
            
            // bomiora_config 테이블에서 cf_use_point 조회
            String sql = "SELECT cf_use_point FROM bomiora_config LIMIT 1";
            
            Map<String, Object> config = new HashMap<>();
            
            try {
                Integer cfUsePoint = jdbcTemplate.queryForObject(sql, Integer.class);
                config.put("cf_use_point", cfUsePoint != null && cfUsePoint == 1);
                System.out.println("✅ cf_use_point 조회: " + config.get("cf_use_point"));
            } catch (Exception e) {
                System.out.println("⚠️ cf_use_point 조회 실패, 기본값 사용: " + e.getMessage());
                // 기본값: true (포인트 사용 가능)
                config.put("cf_use_point", true);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", config);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 설정 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "설정 조회 실패: " + e.getMessage());
            
            // 기본값 반환
            Map<String, Object> defaultConfig = new HashMap<>();
            defaultConfig.put("cf_use_point", true);
            response.put("data", defaultConfig);
            
            return ResponseEntity.ok(response);
        }
    }
}
