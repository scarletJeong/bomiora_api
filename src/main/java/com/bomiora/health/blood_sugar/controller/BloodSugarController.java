package com.bomiora.health.blood_sugar.controller;

import com.bomiora.health.blood_sugar.dto.BloodSugarDTO;
import com.bomiora.health.blood_sugar.service.BloodSugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/health/blood-sugar")
public class BloodSugarController {
    
    @Autowired
    private BloodSugarService bloodSugarService;
    
    /**
     * 혈당 기록 추가
     * POST /api/health/blood-sugar
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addRecord(@RequestBody BloodSugarDTO dto) {
        try {
            BloodSugarDTO savedDto = bloodSugarService.addRecord(dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "혈당 기록이 추가되었습니다");
            response.put("data", savedDto);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println("혈당 기록 추가 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈당 기록 추가 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 혈당 기록 수정
     * PUT /api/health/blood-sugar/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRecord(
            @PathVariable Long id,
            @RequestBody BloodSugarDTO dto) {
        try {
            BloodSugarDTO updatedDto = bloodSugarService.updateRecord(id, dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "혈당 기록이 수정되었습니다");
            response.put("data", updatedDto);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("혈당 기록 수정 실패 - ID: " + id + ", " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.out.println("혈당 기록 수정 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈당 기록 수정 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 혈당 기록 삭제
     * DELETE /api/health/blood-sugar/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRecord(@PathVariable Long id) {
        try {
            bloodSugarService.deleteRecord(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "혈당 기록이 삭제되었습니다");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("혈당 기록 삭제 실패 - ID: " + id + ", " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.out.println("혈당 기록 삭제 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈당 기록 삭제 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 사용자의 모든 혈당 기록 조회
     * GET /api/health/blood-sugar?mb_id={mbId}
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getRecords(@RequestParam("mb_id") String mbId) {
        try {
            List<BloodSugarDTO> records = bloodSugarService.getRecords(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("혈당 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈당 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 최신 혈당 기록 조회
     * GET /api/health/blood-sugar/latest?mb_id={mbId}
     */
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestRecord(@RequestParam("mb_id") String mbId) {
        try {
            BloodSugarDTO record = bloodSugarService.getLatestRecord(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", record != null);
            response.put("data", record);
            
            if (record == null) {
                response.put("message", "혈당 기록이 없습니다");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("최신 혈당 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "최신 혈당 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 날짜 범위로 혈당 기록 조회
     * GET /api/health/blood-sugar/range?mb_id={mbId}&start_date={startDate}&end_date={endDate}
     */
    @GetMapping("/range")
    public ResponseEntity<Map<String, Object>> getRecordsByDateRange(
            @RequestParam("mb_id") String mbId,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<BloodSugarDTO> records = bloodSugarService.getRecordsByDateRange(mbId, startDate, endDate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("날짜 범위 혈당 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "날짜 범위 혈당 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 측정 유형별 혈당 기록 조회
     * GET /api/health/blood-sugar/type?mb_id={mbId}&measurement_type={type}
     */
    @GetMapping("/type")
    public ResponseEntity<Map<String, Object>> getRecordsByMeasurementType(
            @RequestParam("mb_id") String mbId,
            @RequestParam("measurement_type") String measurementType) {
        try {
            List<BloodSugarDTO> records = bloodSugarService.getRecordsByMeasurementType(mbId, measurementType);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("측정 유형별 혈당 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "측정 유형별 혈당 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 혈당 상태별 기록 조회
     * GET /api/health/blood-sugar/status?mb_id={mbId}&status={status}
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getRecordsByStatus(
            @RequestParam("mb_id") String mbId,
            @RequestParam("status") String status) {
        try {
            List<BloodSugarDTO> records = bloodSugarService.getRecordsByStatus(mbId, status);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("혈당 상태별 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈당 상태별 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 혈당 기록 개수 조회
     * GET /api/health/blood-sugar/count?mb_id={mbId}
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getRecordCount(@RequestParam("mb_id") String mbId) {
        try {
            long count = bloodSugarService.getRecordCount(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", count);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("혈당 기록 개수 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈당 기록 개수 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 특정 기간 내 혈당 기록 개수 조회
     * GET /api/health/blood-sugar/count/range?mb_id={mbId}&start_date={startDate}&end_date={endDate}
     */
    @GetMapping("/count/range")
    public ResponseEntity<Map<String, Object>> getRecordCountByDateRange(
            @RequestParam("mb_id") String mbId,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            long count = bloodSugarService.getRecordCountByDateRange(mbId, startDate, endDate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", count);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("기간별 혈당 기록 개수 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "기간별 혈당 기록 개수 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
