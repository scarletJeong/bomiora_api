package com.bomiora.user.questionnaire.controller;

import com.bomiora.user.questionnaire.dto.ApiResponseDto;
import com.bomiora.user.questionnaire.dto.HealthProfileDto;
import com.bomiora.user.questionnaire.dto.HealthProfileRequestDto;
import com.bomiora.user.questionnaire.service.HealthProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/questionnaire")
// CORS 설정은 CorsConfig.java에서 전역 설정으로 처리
public class HealthProfileController {
    
    private final HealthProfileService healthProfileService;
    
    // 생성자 주입
    public HealthProfileController(HealthProfileService healthProfileService) {
        this.healthProfileService = healthProfileService;
    }
    
    /**
     * 사용자 문진표 조회
     * GET /api/questionnaire/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDto<HealthProfileDto>> getHealthProfile(@PathVariable String userId) {
        try {
            System.out.println("문진표 조회 API 호출 - 사용자 ID: " + userId);
            
            Optional<HealthProfileDto> healthProfile = healthProfileService.getHealthProfile(userId);
            
            if (healthProfile.isPresent()) {
                return ResponseEntity.ok(ApiResponseDto.success("문진표 조회 성공", healthProfile.get()));
            } else {
                return ResponseEntity.ok(ApiResponseDto.success("문진표가 없습니다", null));
            }
            
        } catch (Exception e) {
            System.out.println("문진표 조회 중 오류 발생 - 사용자 ID: " + userId + ", 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("문진표 조회 중 오류가 발생했습니다", e.getMessage()));
        }
    }
    
    /**
     * 문진표 저장/수정
     * POST /api/questionnaire
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<HealthProfileDto>> saveHealthProfile(
            @Valid @RequestBody HealthProfileRequestDto requestDto) {
        try {
            System.out.println("문진표 저장 API 호출 - 사용자 ID: " + requestDto.getMbId());
            
            HealthProfileDto savedProfile = healthProfileService.saveHealthProfile(requestDto);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("문진표가 저장되었습니다", savedProfile));
            
        } catch (Exception e) {
            System.out.println("문진표 저장 중 오류 발생 - 사용자 ID: " + requestDto.getMbId() + ", 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("문진표 저장 중 오류가 발생했습니다", e.getMessage()));
        }
    }
    
    /**
     * 문진표 수정
     * PUT /api/questionnaire/{pfNo}
     */
    @PutMapping("/{pfNo}")
    public ResponseEntity<ApiResponseDto<HealthProfileDto>> updateHealthProfile(
            @PathVariable Long pfNo,
            @Valid @RequestBody HealthProfileRequestDto requestDto) {
        try {
            System.out.println("문진표 수정 API 호출 - 문진표 번호: " + pfNo + ", 사용자 ID: " + requestDto.getMbId());
            
            HealthProfileDto updatedProfile = healthProfileService.updateHealthProfile(pfNo, requestDto.getMbId(), requestDto);
            
            return ResponseEntity.ok(ApiResponseDto.success("문진표가 수정되었습니다", updatedProfile));
            
        } catch (IllegalArgumentException e) {
            System.out.println("문진표 수정 실패 - 문진표 번호: " + pfNo + ", 사용자 ID: " + requestDto.getMbId() + ", 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println("문진표 수정 중 오류 발생 - 문진표 번호: " + pfNo + ", 사용자 ID: " + requestDto.getMbId() + ", 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("문진표 수정 중 오류가 발생했습니다", e.getMessage()));
        }
    }
    
    /**
     * 문진표 삭제
     * DELETE /api/questionnaire/{pfNo}
     */
    @DeleteMapping("/{pfNo}")
    public ResponseEntity<ApiResponseDto<Void>> deleteHealthProfile(
            @PathVariable Long pfNo,
            @RequestParam String mbId) {
        try {
            System.out.println("문진표 삭제 API 호출 - 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId);
            
            healthProfileService.deleteHealthProfile(pfNo, mbId);
            
            return ResponseEntity.ok(ApiResponseDto.success("문진표가 삭제되었습니다", null));
            
        } catch (IllegalArgumentException e) {
            System.out.println("문진표 삭제 실패 - 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId + ", 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println("문진표 삭제 중 오류 발생 - 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId + ", 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("문진표 삭제 중 오류가 발생했습니다", e.getMessage()));
        }
    }
    
    /**
     * 문진표 존재 여부 확인
     * GET /api/questionnaire/{userId}/exists
     */
    @GetMapping("/{userId}/exists")
    public ResponseEntity<ApiResponseDto<Boolean>> hasHealthProfile(@PathVariable String userId) {
        try {
            System.out.println("문진표 존재 여부 확인 API 호출 - 사용자 ID: " + userId);
            
            boolean exists = healthProfileService.hasHealthProfile(userId);
            
            return ResponseEntity.ok(ApiResponseDto.success("조회 완료", exists));
            
        } catch (Exception e) {
            System.out.println("문진표 존재 여부 확인 중 오류 발생 - 사용자 ID: " + userId + ", 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("문진표 존재 여부 확인 중 오류가 발생했습니다", e.getMessage()));
        }
    }
    
    /**
     * 건강 상태 분석 (추가 기능)
     * GET /api/questionnaire/{userId}/analysis
     */
    @GetMapping("/{userId}/analysis")
    public ResponseEntity<ApiResponseDto<Object>> analyzeHealthProfile(@PathVariable String userId) {
        try {
            System.out.println("건강 상태 분석 API 호출 - 사용자 ID: " + userId);
            
            Optional<HealthProfileDto> healthProfile = healthProfileService.getHealthProfile(userId);
            
            if (healthProfile.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("문진표를 찾을 수 없습니다"));
            }
            
            // TODO: 실제 건강 상태 분석 로직 구현
            // BMI 계산, 목표 달성 가능성 평가 등
            
            return ResponseEntity.ok(ApiResponseDto.success("건강 상태 분석 완료", null));
            
        } catch (Exception e) {
            System.out.println("건강 상태 분석 중 오류 발생 - 사용자 ID: " + userId + ", 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("건강 상태 분석 중 오류가 발생했습니다", e.getMessage()));
        }
    }
}
