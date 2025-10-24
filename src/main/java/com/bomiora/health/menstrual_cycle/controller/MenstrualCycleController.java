package com.bomiora.health.menstrual_cycle.controller;

import com.bomiora.health.menstrual_cycle.dto.MenstrualCycleDTO;
import com.bomiora.health.menstrual_cycle.service.MenstrualCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/health/menstrual-cycle")
public class MenstrualCycleController {
    
    @Autowired
    private MenstrualCycleService menstrualCycleService;
    
    /**
     * 생리주기 기록 추가
     * POST /api/health/menstrual-cycle
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addRecord(@RequestBody MenstrualCycleDTO dto) {
        try {
            MenstrualCycleDTO savedDto = menstrualCycleService.addRecord(dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "생리주기 기록이 추가되었습니다");
            response.put("data", savedDto);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println("생리주기 기록 추가 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "생리주기 기록 추가 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 생리주기 기록 수정
     * PUT /api/health/menstrual-cycle/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRecord(
            @PathVariable Long id,
            @RequestBody MenstrualCycleDTO dto) {
        try {
            MenstrualCycleDTO updatedDto = menstrualCycleService.updateRecord(id, dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "생리주기 기록이 수정되었습니다");
            response.put("data", updatedDto);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("생리주기 기록 수정 실패 - ID: " + id + ", " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.out.println("생리주기 기록 수정 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "생리주기 기록 수정 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 생리주기 기록 삭제
     * DELETE /api/health/menstrual-cycle/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRecord(@PathVariable Long id) {
        try {
            menstrualCycleService.deleteRecord(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "생리주기 기록이 삭제되었습니다");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("생리주기 기록 삭제 실패 - ID: " + id + ", " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            System.out.println("생리주기 기록 삭제 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "생리주기 기록 삭제 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 사용자의 모든 생리주기 기록 조회
     * GET /api/health/menstrual-cycle?mb_id={mbId}
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getRecords(@RequestParam String mb_id) {
        try {
            List<MenstrualCycleDTO> records = menstrualCycleService.getRecords(mb_id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "생리주기 기록 조회 성공");
            response.put("data", records);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("생리주기 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "생리주기 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 최신 생리주기 기록 조회
     * GET /api/health/menstrual-cycle/latest?mb_id={mbId}
     */
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestRecord(@RequestParam String mb_id) {
        try {
            MenstrualCycleDTO record = menstrualCycleService.getLatestRecord(mb_id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "최신 생리주기 기록 조회 성공");
            response.put("data", record);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("최신 생리주기 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "최신 생리주기 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 날짜 범위로 생리주기 기록 조회
     * GET /api/health/menstrual-cycle/range?mb_id={mbId}&start_date={startDate}&end_date={endDate}
     */
    @GetMapping("/range")
    public ResponseEntity<Map<String, Object>> getRecordsByDateRange(
            @RequestParam String mb_id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date) {
        try {
            List<MenstrualCycleDTO> records = menstrualCycleService.getRecordsByDateRange(mb_id, start_date, end_date);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "날짜 범위 생리주기 기록 조회 성공");
            response.put("data", records);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("날짜 범위 생리주기 기록 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "날짜 범위 생리주기 기록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 생리주기 통계 조회
     * GET /api/health/menstrual-cycle/stats?mb_id={mbId}
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(@RequestParam String mb_id) {
        try {
            Double averageCycleLength = menstrualCycleService.getAverageCycleLength(mb_id);
            Double averagePeriodLength = menstrualCycleService.getAveragePeriodLength(mb_id);
            long recordCount = menstrualCycleService.getRecordCount(mb_id);
            List<MenstrualCycleDTO> recentRecords = menstrualCycleService.getRecentSixMonthsRecords(mb_id);
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("averageCycleLength", averageCycleLength);
            stats.put("averagePeriodLength", averagePeriodLength);
            stats.put("recordCount", recordCount);
            stats.put("recentRecords", recentRecords);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "생리주기 통계 조회 성공");
            response.put("data", stats);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("생리주기 통계 조회 실패: " + e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "생리주기 통계 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
