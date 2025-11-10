package com.bomiora.user.delivery.controller;

import com.bomiora.user.delivery.dto.OrderDetailDTO;
import com.bomiora.user.delivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 주문/배송 조회 Controller
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*") // CORS 허용
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 주문 목록 조회
     * 
     * GET /api/orders?mbId=test&period=3&status=all&page=0&size=10
     * 
     * @param mbId 회원 ID (필수)
     * @param period 기간 (개월 수: 1, 3, 6, 0=전체) 기본값: 0
     * @param status 상태 (all, cancel, preparing, delivering, finish) 기본값: all
     * @param page 페이지 번호 (0부터 시작) 기본값: 0
     * @param size 페이지 크기 기본값: 10
     * @return 주문 목록
     */
    @GetMapping
    public ResponseEntity<?> getOrderList(
        @RequestParam(required = true) String mbId,
        @RequestParam(defaultValue = "0") int period,
        @RequestParam(defaultValue = "all") String status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Map<String, Object> result = orderService.getOrderList(mbId, period, status, page, size);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * 주문 상세 조회
     * 
     * GET /api/orders/{odId}?mbId=test
     * 
     * @param odId 주문 ID
     * @param mbId 회원 ID (필수)
     * @return 주문 상세 정보
     */
    @GetMapping("/{odId}")
    public ResponseEntity<?> getOrderDetail(
        @PathVariable Long odId,
        @RequestParam(required = true) String mbId
    ) {
        try {
            OrderDetailDTO detail = orderService.getOrderDetail(odId, mbId);
            return ResponseEntity.ok(detail);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * 주문 취소
     * 
     * POST /api/orders/{odId}/cancel
     * Body: { "mbId": "test" }
     * 
     * @param odId 주문 ID
     * @param requestBody 요청 본문 (mbId 포함)
     * @return 취소 결과
     */
    @PostMapping("/{odId}/cancel")
    public ResponseEntity<?> cancelOrder(
        @PathVariable Long odId,
        @RequestBody Map<String, String> requestBody
    ) {
        try {
            String mbId = requestBody.get("mbId");
            if (mbId == null || mbId.trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "회원 ID가 필요합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            boolean result = orderService.cancelOrder(odId, mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", result);
            response.put("message", "주문이 취소되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "주문 취소 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * 구매 확정
     * 
     * POST /api/orders/{odId}/confirm
     * Body: { "mbId": "test" }
     * 
     * @param odId 주문 ID
     * @param requestBody 요청 본문 (mbId 포함)
     * @return 확정 결과
     */
    @PostMapping("/{odId}/confirm")
    public ResponseEntity<?> confirmPurchase(
        @PathVariable Long odId,
        @RequestBody Map<String, String> requestBody
    ) {
        try {
            String mbId = requestBody.get("mbId");
            if (mbId == null || mbId.trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "회원 ID가 필요합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            boolean result = orderService.confirmPurchase(odId, mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", result);
            response.put("message", "구매가 확정되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "구매 확정 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * 자동 확정 배치 처리 (스케줄러에서 호출)
     * 
     * POST /api/orders/batch/auto-confirm
     * 
     * @return 처리 결과
     */
    @PostMapping("/batch/auto-confirm")
    public ResponseEntity<?> processAutoConfirm() {
        try {
            orderService.processAutoConfirm();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "자동 확정 처리가 완료되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "자동 확정 처리 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

