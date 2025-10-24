package com.bomiora.health.steps.controller;

import com.bomiora.health.steps.dto.*;
import com.bomiora.health.steps.service.StepsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/steps")
@CrossOrigin(origins = "*")
public class StepsController {
    
    private static final Logger log = LoggerFactory.getLogger(StepsController.class);
    
    private final StepsService stepsService;
    
    public StepsController(StepsService stepsService) {
        this.stepsService = stepsService;
    }
    
    // 걸음수 기록 생성
    @PostMapping
    public ResponseEntity<StepsRecordDTO> createStepsRecord(@RequestBody StepsRequestDTO requestDTO) {
        try {
            log.info("걸음수 기록 생성 요청 - 사용자 ID: {}, 날짜: {}", requestDTO.getUserId(), requestDTO.getRecordDate());
            
            StepsRecordDTO result = stepsService.createStepsRecord(requestDTO);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            log.error("걸음수 기록 생성 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("걸음수 기록 생성 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 걸음수 기록 수정
    @PutMapping("/{recordId}")
    public ResponseEntity<StepsRecordDTO> updateStepsRecord(
            @PathVariable Long recordId, 
            @RequestBody StepsRequestDTO requestDTO) {
        try {
            log.info("걸음수 기록 수정 요청 - ID: {}", recordId);
            
            StepsRecordDTO result = stepsService.updateStepsRecord(recordId, requestDTO);
            
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("걸음수 기록 수정 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("걸음수 기록 수정 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 걸음수 기록 삭제
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteStepsRecord(@PathVariable Long recordId) {
        try {
            log.info("걸음수 기록 삭제 요청 - ID: {}", recordId);
            
            stepsService.deleteStepsRecord(recordId);
            
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("걸음수 기록 삭제 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("걸음수 기록 삭제 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 오늘의 걸음수 기록 조회
    @GetMapping("/today/{userId}")
    public ResponseEntity<StepsRecordDTO> getTodayStepsRecord(@PathVariable Long userId) {
        try {
            log.info("오늘의 걸음수 기록 조회 요청 - 사용자 ID: {}", userId);
            
            StepsRecordDTO result = stepsService.getTodayStepsRecord(userId);
            
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("오늘의 걸음수 기록 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 특정 날짜의 걸음수 기록 조회
    @GetMapping("/date/{userId}")
    public ResponseEntity<StepsRecordDTO> getStepsRecordByDate(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            log.info("특정 날짜 걸음수 기록 조회 요청 - 사용자 ID: {}, 날짜: {}", userId, date);
            
            StepsRecordDTO result = stepsService.getStepsRecordByDate(userId, date);
            
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("특정 날짜 걸음수 기록 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 주간 걸음수 기록 조회
    @GetMapping("/weekly/{userId}")
    public ResponseEntity<List<StepsRecordDTO>> getWeeklyStepsRecords(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        try {
            log.info("주간 걸음수 기록 조회 요청 - 사용자 ID: {}, 시작일: {}", userId, startDate);
            
            List<StepsRecordDTO> result = stepsService.getWeeklyStepsRecords(userId, startDate);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("주간 걸음수 기록 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 월간 걸음수 기록 조회
    @GetMapping("/monthly/{userId}")
    public ResponseEntity<List<StepsRecordDTO>> getMonthlyStepsRecords(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        try {
            log.info("월간 걸음수 기록 조회 요청 - 사용자 ID: {}, 년도: {}, 월: {}", userId, year, month);
            
            List<StepsRecordDTO> result = stepsService.getMonthlyStepsRecords(userId, year, month);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("월간 걸음수 기록 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 걸음수 통계 조회
    @GetMapping("/statistics/{userId}")
    public ResponseEntity<StepsStatisticsDTO> getStepsStatistics(@PathVariable Long userId) {
        try {
            log.info("걸음수 통계 조회 요청 - 사용자 ID: {}", userId);
            
            StepsStatisticsDTO result = stepsService.getStepsStatistics(userId);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("걸음수 통계 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 연동 상태 업데이트 (향후 애플/갤럭시 연동용)
    @PutMapping("/{recordId}/sync-status")
    public ResponseEntity<Void> updateSyncStatus(
            @PathVariable Long recordId,
            @RequestParam String syncStatus,
            @RequestParam(required = false) String errorMessage) {
        try {
            log.info("연동 상태 업데이트 요청 - ID: {}, 상태: {}", recordId, syncStatus);
            
            // TODO: 향후 연동 기능 구현 시 사용
            // stepsService.updateSyncStatus(recordId, syncStatus, errorMessage);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("연동 상태 업데이트 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 연동 데이터 동기화 (향후 애플/갤럭시 연동용)
    @PostMapping("/sync/{userId}")
    public ResponseEntity<Void> syncStepsData(@PathVariable Long userId) {
        try {
            log.info("걸음수 데이터 동기화 요청 - 사용자 ID: {}", userId);
            
            // TODO: 향후 연동 기능 구현 시 사용
            // stepsService.syncStepsData(userId);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("걸음수 데이터 동기화 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 건강 앱 연동 설정 (향후 애플/갤럭시 연동용)
    @PostMapping("/connect/{userId}")
    public ResponseEntity<Void> connectHealthApp(
            @PathVariable Long userId,
            @RequestParam String appType,
            @RequestParam String authToken) {
        try {
            log.info("건강 앱 연동 설정 요청 - 사용자 ID: {}, 앱 타입: {}", userId, appType);
            
            // TODO: 향후 연동 기능 구현 시 사용
            // stepsService.connectHealthApp(userId, appType, authToken);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("건강 앱 연동 설정 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // 건강 앱 연동 해제 (향후 애플/갤럭시 연동용)
    @DeleteMapping("/disconnect/{userId}")
    public ResponseEntity<Void> disconnectHealthApp(@PathVariable Long userId) {
        try {
            log.info("건강 앱 연동 해제 요청 - 사용자 ID: {}", userId);
            
            // TODO: 향후 연동 기능 구현 시 사용
            // stepsService.disconnectHealthApp(userId);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("건강 앱 연동 해제 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
