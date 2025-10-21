package com.bomiora.health.blood_pressure.controller;

import com.bomiora.health.blood_pressure.dto.BloodPressureDTO;
import com.bomiora.health.blood_pressure.service.BloodPressureService;
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
@RequestMapping("/api/health/blood-pressure")
public class BloodPressureController {
    
    @Autowired
    private BloodPressureService bloodPressureService;
    
    /**
     * 혈압 기록 추가
     * POST /api/health/blood-pressure
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addRecord(@RequestBody BloodPressureDTO dto) {
        try {
            BloodPressureDTO savedDto = bloodPressureService.addRecord(dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "혈압 기록이 추가되었습니다");
            response.put("data", savedDto);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println("혈압 기록 추가 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈압 기록 추가 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 혈압 기록 수정
     * PUT /api/health/blood-pressure/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRecord(
            @PathVariable Long id,
            @RequestBody BloodPressureDTO dto) {
        try {
            BloodPressureDTO updatedDto = bloodPressureService.updateRecord(id, dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "혈압 기록이 수정되었습니다");
            response.put("data", updatedDto);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("혈압 기록 수정 실패 - ID: " + id + ", " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.out.println("혈압 기록 수정 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈압 기록 수정 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 혈압 기록 삭제
     * DELETE /api/health/blood-pressure/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRecord(@PathVariable Long id) {
        try {
            bloodPressureService.deleteRecord(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "혈압 기록이 삭제되었습니다");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("혈압 기록 삭제 실패 - ID: " + id + ", " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.out.println("혈압 기록 삭제 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈압 기록 삭제 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 사용자의 모든 혈압 기록 조회
     * GET /api/health/blood-pressure?mb_id={mbId}
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getRecords(@RequestParam("mb_id") String mbId) {
        try {
            List<BloodPressureDTO> records = bloodPressureService.getRecords(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("혈압 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈압 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 최신 혈압 기록 조회
     * GET /api/health/blood-pressure/latest?mb_id={mbId}
     */
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestRecord(@RequestParam("mb_id") String mbId) {
        try {
            BloodPressureDTO record = bloodPressureService.getLatestRecord(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", record != null);
            response.put("data", record);
            
            if (record == null) {
                response.put("message", "혈압 기록이 없습니다");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("최신 혈압 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "최신 혈압 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 날짜 범위로 혈압 기록 조회
     * GET /api/health/blood-pressure/range?mb_id={mbId}&start_date={startDate}&end_date={endDate}
     */
    @GetMapping("/range")
    public ResponseEntity<Map<String, Object>> getRecordsByDateRange(
            @RequestParam("mb_id") String mbId,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<BloodPressureDTO> records = bloodPressureService.getRecordsByDateRange(mbId, startDate, endDate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("날짜 범위 혈압 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "날짜 범위 혈압 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 혈압 기록 개수 조회
     * GET /api/health/blood-pressure/count?mb_id={mbId}
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getRecordCount(@RequestParam("mb_id") String mbId) {
        try {
            long count = bloodPressureService.getRecordCount(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", count);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("혈압 기록 개수 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "혈압 기록 개수 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

