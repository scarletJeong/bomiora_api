package com.bomiora.health.weight.controller;

import com.bomiora.health.weight.entity.Weight;
import com.bomiora.health.weight.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/health/weight")
public class WeightController {

    @Autowired
    private WeightRepository weightRepository;

    // 파일 업로드 디렉토리
    private static final String UPLOAD_DIR = "/home/ubuntu/weight_images/";

    /**
     * 이미지 파일 업로드
     * POST /api/health/weight/upload-image
     */
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 업로드 디렉토리 생성
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 고유한 파일명 생성
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;

            // 파일 저장
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // 파일 URL 생성
            String fileUrl = "/api/health/weight/images/" + filename;

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("filename", filename);
            response.put("url", fileUrl);
            response.put("message", "이미지 업로드 성공");

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "이미지 업로드 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 이미지 파일 서빙
     * GET /api/health/weight/images/{filename}
     */
    @GetMapping("/images/{filename}")
    public ResponseEntity<?> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "image/jpeg";
            }

            return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(imageBytes);

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 체중 기록 생성
     * POST /api/health/weight
     */
    @PostMapping
    public ResponseEntity<?> createWeight(@RequestBody Map<String, Object> request) {
        try {
            String mbId = request.get("mb_id").toString();
            Double weight = Double.valueOf(request.get("weight").toString());
            
            String measuredAtStr = request.get("measured_at").toString();
            LocalDateTime measuredAt = LocalDateTime.parse(measuredAtStr);
            
            Double height = request.get("height") != null 
                ? Double.valueOf(request.get("height").toString()) 
                : null;
            
            // BMI 자동 계산
            Double bmi = Weight.calculateBMI(weight, height);
            
            String notes = request.get("notes") != null 
                ? request.get("notes").toString() 
                : null;

            // 이미지 URL 처리
            String frontImagePath = request.get("front_image_path") != null 
                ? request.get("front_image_path").toString() 
                : null;

            String sideImagePath = request.get("side_image_path") != null 
                ? request.get("side_image_path").toString() 
                : null;

            Weight record = new Weight();
            record.setMbId(mbId);
            record.setMeasuredAt(measuredAt);
            record.setWeight(weight);
            record.setHeight(height);
            record.setBmi(bmi);
            record.setNotes(notes);
            record.setFrontImagePath(frontImagePath);
            record.setSideImagePath(sideImagePath);

            Weight savedRecord = weightRepository.save(record);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("record", savedRecord);
            response.put("message", "체중 기록이 저장되었습니다");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "체중 기록 저장 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 체중 기록 수정
     * PUT /api/health/weight/{recordId}
     */
    @PutMapping("/{recordId}")
    public ResponseEntity<?> updateWeight(
            @PathVariable Long recordId,
            @RequestBody Map<String, Object> request) {
        try {
            Optional<Weight> optionalRecord = weightRepository.findById(recordId);
            
            if (!optionalRecord.isPresent()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "기록을 찾을 수 없습니다");
                return ResponseEntity.notFound().build();
            }

            Weight record = optionalRecord.get();

            if (request.containsKey("weight")) {
                record.setWeight(Double.valueOf(request.get("weight").toString()));
            }
            
            if (request.containsKey("height")) {
                Object heightValue = request.get("height");
                record.setHeight(heightValue != null ? Double.valueOf(heightValue.toString()) : null);
            }
            
            if (request.containsKey("measured_at")) {
                record.setMeasuredAt(LocalDateTime.parse(request.get("measured_at").toString()));
            }
            
            if (request.containsKey("notes")) {
                Object notesValue = request.get("notes");
                record.setNotes(notesValue != null ? notesValue.toString() : null);
            }

            if (request.containsKey("front_image_path")) {
                Object frontImagePathValue = request.get("front_image_path");
                record.setFrontImagePath(frontImagePathValue != null ? frontImagePathValue.toString() : null);
            }

            if (request.containsKey("side_image_path")) {
                Object sideImagePathValue = request.get("side_image_path");
                record.setSideImagePath(sideImagePathValue != null ? sideImagePathValue.toString() : null);
            }

            // BMI 재계산
            record.setBmi(Weight.calculateBMI(record.getWeight(), record.getHeight()));

            Weight updatedRecord = weightRepository.save(record);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("record", updatedRecord);
            response.put("message", "체중 기록이 수정되었습니다");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "체중 기록 수정 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 체중 기록 삭제
     * DELETE /api/health/weight/{recordId}
     */
    @DeleteMapping("/{recordId}")
    public ResponseEntity<?> deleteWeight(@PathVariable Long recordId) {
        try {
            if (!weightRepository.existsById(recordId)) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "기록을 찾을 수 없습니다");
                return ResponseEntity.notFound().build();
            }

            weightRepository.deleteById(recordId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "체중 기록이 삭제되었습니다");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "체중 기록 삭제 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 회원의 모든 체중 기록 조회
     * GET /api/health/weight?mb_id={mbId}
     */
    @GetMapping
    public ResponseEntity<?> getWeights(@RequestParam String mb_id) {
        try {
            List<Weight> records = weightRepository.findByMbIdOrderByMeasuredAtDesc(mb_id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            response.put("count", records.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "체중 기록 조회 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 최신 체중 기록 조회
     * GET /api/health/weight/latest?mb_id={mbId}
     */
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestWeight(@RequestParam String mb_id) {
        try {
            Optional<Weight> record = weightRepository.findFirstByMbIdOrderByMeasuredAtDesc(mb_id);

            Map<String, Object> response = new HashMap<>();
            
            if (record.isPresent()) {
                response.put("success", true);
                response.put("data", record.get());
            } else {
                response.put("success", false);
                response.put("data", null);
                response.put("message", "체중 기록이 없습니다");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "체중 기록 조회 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 특정 날짜의 체중 기록 조회
     * GET /api/health/weight/{date}?mb_id={mbId}
     */
    @GetMapping("/{date}")
    public ResponseEntity<?> getWeightsByDate(
            @PathVariable String date,
            @RequestParam String mb_id) {
        try {
            LocalDate targetDate = LocalDate.parse(date);
            LocalDateTime startOfDay = targetDate.atStartOfDay();
            LocalDateTime endOfDay = targetDate.atTime(LocalTime.MAX);

            List<Weight> records = weightRepository.findByMbIdAndDateRange(
                mb_id, startOfDay, endOfDay
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            response.put("count", records.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "체중 기록 조회 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
