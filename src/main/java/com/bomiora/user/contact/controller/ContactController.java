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
    public ResponseEntity<Map<String, Object>> getMyContacts(
            @RequestParam("mb_id") String mbId) {
        try {
            List<Contact> contacts = contactService.getUserContacts(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", contacts.stream().map(this::convertToMap).collect(Collectors.toList()));
            
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
    public ResponseEntity<Map<String, Object>> getContactDetail(
            @PathVariable Integer wrId) {
        try {
            Optional<Contact> contact = contactService.getContactDetail(wrId);
            
            if (contact.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("data", convertToMap(contact.get()));
                
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
    public ResponseEntity<Map<String, Object>> createContact(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest) {
        try {
            System.out.println("====================================");
            System.out.println("📨 [문의 작성 요청] 받은 데이터:");
            System.out.println("====================================");
            System.out.println("mb_id: " + request.get("mb_id"));
            System.out.println("wr_name: " + request.get("wr_name"));
            System.out.println("wr_email: " + request.get("wr_email"));
            System.out.println("wr_subject: " + request.get("wr_subject"));
            System.out.println("wr_content: " + request.get("wr_content"));
            System.out.println("wr_5: " + request.get("wr_5"));
            System.out.println("wr_option: " + request.get("wr_option"));
            System.out.println("ca_name: " + request.get("ca_name"));
            System.out.println("====================================");
            
            Contact contact = new Contact();
            
            // 필수 필드
            contact.setMbId((String) request.get("mb_id"));
            contact.setWrSubject((String) request.get("wr_subject"));
            contact.setWrContent((String) request.get("wr_content"));
            contact.setWrName((String) request.get("wr_name"));
            contact.setWrEmail((String) request.get("wr_email"));
            
            // r_1: 작성자 이름 (비회원 문의 시스템 호환)
            contact.setWr1((String) request.get("wr_name"));
            System.out.println("✅ wr_1에 작성자 이름 저장: " + contact.getWr1());
            
            // 선택 필드
            if (request.get("ca_name") != null) {
                contact.setCaName((String) request.get("ca_name"));
            }
            
            //  wr_5: 휴대폰 번호 (숫자만)
            if (request.get("wr_5") != null) {
                contact.setWr5((String) request.get("wr_5"));
                System.out.println("✅ wr_5 설정 완료: " + contact.getWr5());
            } else {
                System.out.println("⚠️ wr_5 값이 null입니다!");
            }
            
            //  wr_option: 비밀글 옵션 (secret)
            if (request.get("wr_option") != null) {
                contact.setWrOption((String) request.get("wr_option"));
                System.out.println("✅ wr_option 설정 완료: " + contact.getWrOption());
            } else {
                System.out.println("⚠️ wr_option 값이 null입니다!");
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
            
            System.out.println("====================================");
            System.out.println("💾 [저장 전] Contact 객체 값:");
            System.out.println("====================================");
            System.out.println("mb_id: " + contact.getMbId());
            System.out.println("wr_name: " + contact.getWrName());
            System.out.println("wr_email: " + contact.getWrEmail());
            System.out.println("wr_subject: " + contact.getWrSubject());
            System.out.println("wr_content: " + contact.getWrContent());
            System.out.println("wr_1: " + contact.getWr1());
            System.out.println("wr_5: " + contact.getWr5());
            System.out.println("wr_option: " + contact.getWrOption());
            System.out.println("wr_ip: " + contact.getWrIp());
            System.out.println("====================================");
            
            Contact saved = contactService.createContact(contact);
            
            System.out.println("====================================");
            System.out.println("✅ [저장 후] Contact 객체 값:");
            System.out.println("====================================");
            System.out.println("wr_id: " + saved.getWrId());
            System.out.println("wr_num: " + saved.getWrNum());
            System.out.println("wr_reply: " + saved.getWrReply());
            System.out.println("wr_parent: " + saved.getWrParent());
            System.out.println("wr_is_comment: " + saved.getWrIsComment());
            System.out.println("mb_id: " + saved.getMbId());
            System.out.println("wr_name: " + saved.getWrName());
            System.out.println("wr_email: " + saved.getWrEmail());
            System.out.println("wr_subject: " + saved.getWrSubject());
            System.out.println("wr_1: " + saved.getWr1());
            System.out.println("wr_5: " + saved.getWr5());
            System.out.println("wr_option: " + saved.getWrOption());
            System.out.println("wr_ip: " + saved.getWrIp());
            System.out.println("wr_datetime: " + saved.getWrDatetime());
            System.out.println("====================================");
            
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
     * 문의 수정
     * PUT /api/contact/{wrId}
     */
    @PutMapping("/{wrId}")
    public ResponseEntity<Map<String, Object>> updateContact(
            @PathVariable Integer wrId,
            @RequestBody Map<String, Object> request) {
        try {
            System.out.println("====================================");
            System.out.println("✏️ [문의 수정 요청] wrId: " + wrId);
            System.out.println("====================================");
            System.out.println("wr_subject: " + request.get("wr_subject"));
            System.out.println("wr_content: " + request.get("wr_content"));
            System.out.println("====================================");
            
            // 기존 문의 조회
            Optional<Contact> contactOpt = contactService.getContactDetail(wrId);
            
            if (!contactOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "문의를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Contact contact = contactOpt.get();
            
            // 답변이 완료된 문의는 수정 불가
            if (contact.hasReply()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "답변이 완료된 문의는 수정할 수 없습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // 제목과 내용만 수정 가능
            if (request.get("wr_subject") != null) {
                contact.setWrSubject((String) request.get("wr_subject"));
            }
            if (request.get("wr_content") != null) {
                contact.setWrContent((String) request.get("wr_content"));
            }
            
            // 수정 시간 업데이트
            contact.setWrLast(java.time.LocalDateTime.now());
            
            Contact updated = contactService.updateContact(contact);
            
            System.out.println("✅ 문의 수정 완료 - wrId: " + updated.getWrId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "문의가 수정되었습니다.");
            response.put("data", convertToMap(updated));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 문의 수정 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "문의 수정 실패: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 문의 답변 조회 (wr_7 필드에서 가져옴)
     * GET /api/contact/{wrId}/replies
     */
    @GetMapping("/{wrId}/replies")
    public ResponseEntity<Map<String, Object>> getContactReplies(
            @PathVariable Integer wrId) {
        try {
            // 원글 조회
            Optional<Contact> contactOpt = contactService.getContactDetail(wrId);
            
            Map<String, Object> response = new HashMap<>();
            
            if (contactOpt.isPresent()) {
                Contact contact = contactOpt.get();
                
                // wr_is_comment = 1 이고 wr_7에 내용이 있으면 답변 있음
                if (contact.hasReply() && contact.getReplyContent() != null && !contact.getReplyContent().isEmpty()) {
                    // 답변을 Contact 형태로 변환하여 반환
                    Map<String, Object> replyMap = new HashMap<>();
                    replyMap.put("wr_id", contact.getWrId());
                    replyMap.put("wr_content", contact.getReplyContent()); // wr_7 내용
                    replyMap.put("wr_datetime", contact.getWrLast() != null ? contact.getWrLast().toString() : contact.getWrDatetime().toString());
                    replyMap.put("wr_name", "관리자");
                    replyMap.put("wr_option", contact.getWrOption());
                    
                    response.put("success", true);
                    response.put("data", java.util.Arrays.asList(replyMap)); // 배열로 반환
                } else {
                    response.put("success", true);
                    response.put("data", java.util.Collections.emptyList()); // 답변 없음
                }
            } else {
                response.put("success", false);
                response.put("message", "문의를 찾을 수 없습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ 답변 조회 API 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "답변 조회 실패: " + e.getMessage());
            
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
        map.put("wr_option", contact.getWrOption());
        map.put("wr_is_comment", contact.getWrIsComment() != null ? contact.getWrIsComment() : 0);
        return map;
    }
}

