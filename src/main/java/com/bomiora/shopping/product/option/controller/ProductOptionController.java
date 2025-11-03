package com.bomiora.shopping.product.option.controller;

import com.bomiora.shopping.product.option.dto.ProductOptionDTO;
import com.bomiora.shopping.product.option.service.ProductOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
// CORS 설정은 CorsConfig.java에서 전역 설정으로 처리
public class ProductOptionController {
    
    @Autowired
    private ProductOptionService optionService;
    
    /**
     * 제품 옵션 목록 조회
     * GET /api/products/{productId}/options
     */
    @GetMapping("/{productId}/options")
    public ResponseEntity<Map<String, Object>> getProductOptions(
            @PathVariable String productId) {
        
        try {
            List<ProductOptionDTO> options = optionService.getProductOptions(productId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", options);
            response.put("message", "옵션 목록 조회 성공");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "옵션 목록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
}

