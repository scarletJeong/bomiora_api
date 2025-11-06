package com.bomiora.user.contact.controller;

import com.bomiora.user.contact.entity.Contact;
import com.bomiora.user.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    
    @Autowired
    private ContactService contactService;
    
    /**
     * 내 문의내역 조회
     * GET /api/contact/list?mb_id={userId}
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getMyInquiries(
            @RequestParam("mb_id") String mbId) {
        try {
            List<Contact> inquiries = contactService.getUserInquiries(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", inquiries.stream().map(this::convertToMap).collect(Collectors.toList()));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 문의내역 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "문의내역 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 문의 상세 조회
     * GET /api/contact/{wrId}
     */
    @GetMapping("/{wrId}")
    public ResponseEntity<Map<String, Object>> getInquiryDetail(
            @PathVariable Integer wrId) {
        try {
            Optional<Contact> inquiry = contactService.getInquiryDetail(wrId);
            
            if (inquiry.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("data", convertToMap(inquiry.get()));
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "문의를 찾을 수 없습니다.");
                
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            System.out.println("❌ 문의 상세 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "문의 상세 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 문의 작성
     * POST /api/contact/create
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createInquiry(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest) {
        try {
            Contact contact = new Contact();
            
            // 필수 필드
            contact.setMbId((String) request.get("mb_id"));
            contact.setWrSubject((String) request.get("wr_subject"));
            contact.setWrContent((String) request.get("wr_content"));
            contact.setWrName((String) request.get("wr_name"));
            contact.setWrEmail((String) request.get("wr_email"));
            
            // 선택 필드
            if (request.get("ca_name") != null) {
                contact.setCaName((String) request.get("ca_name"));
            }
            
            // IP 주소
            String clientIp = httpRequest.getRemoteAddr();
            if (clientIp == null || clientIp.isEmpty()) {
                clientIp = httpRequest.getHeader("X-Forwarded-For");
                if (clientIp == null || clientIp.isEmpty()) {
                    clientIp = "0.0.0.0";
                }
            }
            contact.setWrIp(clientIp);
            
            Contact saved = contactService.createInquiry(contact);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "문의가 등록되었습니다.");
            response.put("data", convertToMap(saved));
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println("❌ 문의 작성 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "문의 등록 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 문의 답변 목록 조회
     * GET /api/contact/{wrId}/replies
     */
    @GetMapping("/{wrId}/replies")
    public ResponseEntity<Map<String, Object>> getInquiryReplies(
            @PathVariable Integer wrId) {
        try {
            List<Contact> replies = contactService.getInquiryReplies(wrId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", replies.stream().map(this::convertToMap).collect(Collectors.toList()));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 답변 목록 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "답변 목록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Contact 엔티티를 Map으로 변환
     */
    private Map<String, Object> convertToMap(Contact contact) {
        Map<String, Object> map = new HashMap<>();
        map.put("wr_id", contact.getWrId());
        map.put("wr_subject", contact.getWrSubject());
        map.put("wr_content", contact.getWrContent());
        map.put("mb_id", contact.getMbId());
        map.put("wr_name", contact.getWrName());
        map.put("wr_email", contact.getWrEmail());
        map.put("wr_datetime", contact.getWrDatetime() != null ? contact.getWrDatetime().toString() : null);
        map.put("wr_last", contact.getWrLast() != null ? contact.getWrLast().toString() : null);
        map.put("wr_comment", contact.getWrComment() != null ? contact.getWrComment() : 0);
        map.put("wr_reply", contact.getWrReply());
        map.put("wr_parent", contact.getWrParent());
        map.put("ca_name", contact.getCaName());
        map.put("wr_hit", contact.getWrHit() != null ? contact.getWrHit() : 0);
        return map;
    }
}

