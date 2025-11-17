package com.bomiora.user.address.controller;

import com.bomiora.user.address.dto.AddressDTO;
import com.bomiora.user.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 배송지 관리 Controller
 */
@RestController
@RequestMapping("/api/user/address")
@CrossOrigin(origins = "*")
public class AddressController {
    
    @Autowired
    private AddressService addressService;
    
    /**
     * 배송지 목록 조회
     * GET /api/user/address?mbId=test
     */
    @GetMapping
    public ResponseEntity<?> getAddressList(@RequestParam(required = true) String mbId) {
        try {
            List<AddressDTO> addresses = addressService.getAddressList(mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", addresses);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * 배송지 상세 조회
     * GET /api/user/address/{id}?mbId=test
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressDetail(
            @PathVariable Long id,
            @RequestParam(required = true) String mbId) {
        try {
            AddressDTO address = addressService.getAddressDetail(id, mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", address);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * 배송지 추가
     * POST /api/user/address
     * Body: AddressDTO
     */
    @PostMapping
    public ResponseEntity<?> addAddress(@RequestBody AddressDTO dto) {
        try {
            AddressDTO saved = addressService.addAddress(dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", saved);
            response.put("message", "배송지가 추가되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * 배송지 수정
     * PUT /api/user/address/{id}
     * Body: AddressDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable Long id,
            @RequestBody AddressDTO dto) {
        try {
            AddressDTO updated = addressService.updateAddress(id, dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", updated);
            response.put("message", "배송지가 수정되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * 배송지 삭제
     * DELETE /api/user/address/{id}?mbId=test
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(
            @PathVariable Long id,
            @RequestParam(required = true) String mbId) {
        try {
            addressService.deleteAddress(id, mbId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "배송지가 삭제되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}

