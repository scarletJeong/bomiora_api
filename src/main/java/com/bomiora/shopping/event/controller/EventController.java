package com.bomiora.shopping.event.controller;

import com.bomiora.shopping.event.entity.Event;
import com.bomiora.shopping.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
public class EventController {
    
    @Autowired
    private EventService eventService;
    
    /**
     * 진행중인 이벤트 목록 조회
     * GET /api/event/active
     */
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveEvents() {
        try {
            List<Event> events = eventService.getActiveEvents();
            List<Map<String, Object>> eventMaps = events.stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", eventMaps);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("진행중인 이벤트 목록 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "진행중인 이벤트 목록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * 종료된 이벤트 목록 조회
     * GET /api/event/ended
     */
    @GetMapping("/ended")
    public ResponseEntity<Map<String, Object>> getEndedEvents() {
        try {
            List<Event> events = eventService.getEndedEvents();
            List<Map<String, Object>> eventMaps = events.stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", eventMaps);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("종료된 이벤트 목록 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "종료된 이벤트 목록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * 이벤트 상세 조회
     * GET /api/event/{wrId}
     */
    @GetMapping("/{wrId}")
    public ResponseEntity<Map<String, Object>> getEventDetail(@PathVariable Integer wrId) {
        try {
            Optional<Event> eventOptional = eventService.getEventDetail(wrId);
            if (eventOptional.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("data", convertToMap(eventOptional.get()));
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "이벤트를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            System.out.println("이벤트 상세 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "이벤트 상세 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Event 엔티티를 Map으로 변환
     */
    private Map<String, Object> convertToMap(Event event) {
        Map<String, Object> map = new HashMap<>();
        map.put("wr_id", event.getWrId());
        map.put("wr_num", event.getWrNum());
        map.put("ca_name", event.getCaName());
        map.put("wr_subject", event.getWrSubject());
        map.put("wr_content", event.getWrContent());
        map.put("wr_link1", event.getWrLink1());
        map.put("wr_datetime", event.getWrDatetime() != null ? event.getWrDatetime().toString() : null);
        map.put("wr_last", event.getWrLast());
        map.put("wr_hit", event.getWrHit() != null ? event.getWrHit() : 0);
        map.put("wr_1", event.getWr1());
        map.put("wr_2", event.getWr2());
        map.put("is_active", event.isActive());
        return map;
    }
}
