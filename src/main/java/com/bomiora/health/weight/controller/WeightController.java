package com.bomiora.health.weight.controller;

import com.bomiora.health.weight.entity.Weight;
import com.bomiora.health.weight.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/health/weight")
@CrossOrigin(origins = {"http://localhost:5000", "http://localhost:5001"})
public class WeightController {

    @Autowired
    private WeightRepository weightRepository;

    /**
     * 체중 기록 생성
     * POST /api/health/weight
     */
    @PostMapping
    public ResponseEntity<?> createWeight(@RequestBody Map<String, Object> request) {
        try {
            Long mbNo = Long.valueOf(request.get("mb_no").toString());
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

            Weight record = new Weight();
            record.setMbNo(mbNo);
            record.setMeasuredAt(measuredAt);
            record.setWeight(weight);
            record.setHeight(height);
            record.setBmi(bmi);
            record.setNotes(notes);

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
     * GET /api/health/weight?mb_no={mbNo}
     */
    @GetMapping
    public ResponseEntity<?> getWeights(@RequestParam Long mb_no) {
        try {
            List<Weight> records = weightRepository.findByMbNoOrderByMeasuredAtDesc(mb_no);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("records", records);
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
     * GET /api/health/weight/latest?mb_no={mbNo}
     */
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestWeight(@RequestParam Long mb_no) {
        try {
            Optional<Weight> record = weightRepository.findFirstByMbNoOrderByMeasuredAtDesc(mb_no);

            Map<String, Object> response = new HashMap<>();
            
            if (record.isPresent()) {
                response.put("success", true);
                response.put("record", record.get());
            } else {
                response.put("success", false);
                response.put("record", null);
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
     * GET /api/health/weight/{date}?mb_no={mbNo}
     */
    @GetMapping("/{date}")
    public ResponseEntity<?> getWeightsByDate(
            @PathVariable String date,
            @RequestParam Long mb_no) {
        try {
            LocalDate targetDate = LocalDate.parse(date);
            LocalDateTime startOfDay = targetDate.atStartOfDay();
            LocalDateTime endOfDay = targetDate.atTime(LocalTime.MAX);

            List<Weight> records = weightRepository.findByMbNoAndDateRange(
                mb_no, startOfDay, endOfDay
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("records", records);
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
