package com.bomiora.shopping.cart.controller;

import com.bomiora.shopping.cart.entity.Cart;
import com.bomiora.shopping.cart.service.CartService;
import com.bomiora.shopping.cart.service.HealthProfileCartService;
import com.bomiora.shopping.product.entity.Product;
import com.bomiora.shopping.product.repository.ProductRepository;
import com.bomiora.user.healthprofile.dto.HealthProfileRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private HealthProfileCartService healthProfileCartService;
    
    /**
     * 장바구니에 상품 추가
     * POST /api/cart/add
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Map<String, Object> request) {
        try {
            System.out.println("장바구니 추가 요청");
            
            String mbId = (String) request.get("mb_id");
            String itId = (String) request.get("it_id");
            Integer quantity = request.get("quantity") != null ? 
                (request.get("quantity") instanceof Integer ? (Integer) request.get("quantity") : 
                 Integer.parseInt(request.get("quantity").toString())) : 1;
            Integer price = request.get("price") != null ?
                (request.get("price") instanceof Integer ? (Integer) request.get("price") :
                 Integer.parseInt(request.get("price").toString())) : 0;
            String optionId = (String) request.get("option_id");
            String optionText = (String) request.get("option_text");
            Integer optionPrice = request.get("option_price") != null ?
                (request.get("option_price") instanceof Integer ? (Integer) request.get("option_price") :
                 Integer.parseInt(request.get("option_price").toString())) : null;
            Long odId = request.get("od_id") != null ?
                (request.get("od_id") instanceof Long ? (Long) request.get("od_id") :
                 Long.parseLong(request.get("od_id").toString())) : null;
            
            if (mbId == null || itId == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "mb_id와 it_id가 필요합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            Cart cart = cartService.addToCart(mbId, itId, quantity, price, optionId, optionText, optionPrice, odId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "장바구니에 추가되었습니다.");
            response.put("data", convertCartToMap(cart));
            
            System.out.println("장바구니 추가 완료 - ctId: " + cart.getCtId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("장바구니 추가 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "장바구니 추가 중 오류가 발생했습니다.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 장바구니 조회
     * GET /api/cart?mb_id={userId}&ct_status={status}
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCart(
            @RequestParam("mb_id") String mbId,
            @RequestParam(required = false, defaultValue = "쇼핑") String ct_status) {
        try {
            System.out.println("장바구니 조회 요청 - mbId: " + mbId + ", ct_status: " + ct_status);
            
            List<Cart> carts = cartService.getCartByUserIdAndStatus(mbId, ct_status);
            
            // Cart 엔티티를 Map으로 변환
            List<Map<String, Object>> cartList = carts.stream()
                    .map(this::convertCartToMap)
                    .toList();
            
            // 배송비 계산
            Integer shippingCost = cartService.calculateShippingCost(carts);
            
            // 총 구매금액 계산
            int totalPrice = carts.stream()
                    .mapToInt(Cart::getCtPrice)
                    .sum();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", cartList);
            response.put("total", cartList.size());
            response.put("shipping_cost", shippingCost);
            response.put("total_price", totalPrice);
            
            System.out.println("장바구니 조회 완료 - 개수: " + cartList.size() + ", 배송비: " + shippingCost + "원, 총 구매금액: " + totalPrice + "원");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("장바구니 조회 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "장바구니 조회 중 오류가 발생했습니다.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 주문 ID(od_id) 생성
     * POST /api/cart/generate-order-id
     */
    @PostMapping("/generate-order-id")
    public ResponseEntity<Map<String, Object>> generateOrderId(
            @RequestBody Map<String, String> request) {
        try {
            String mbId = request.get("mb_id");
            String itId = request.get("it_id");
            
            if (mbId == null || itId == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "mb_id와 it_id가 필요합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            System.out.println("주문 ID 생성 요청 - mbId: " + mbId + ", itId: " + itId);
            
            Long odId = cartService.generateOrderId(mbId, itId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("od_id", odId);
            response.put("message", "주문 ID가 생성되었습니다.");
            
            System.out.println("주문 ID 생성 완료 - odId: " + odId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("주문 ID 생성 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "주문 ID 생성 중 오류가 발생했습니다.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * HealthProfileCart 저장
     * POST /api/cart/save-health-profile-cart
     */
    @PostMapping("/save-health-profile-cart")
    public ResponseEntity<Map<String, Object>> saveHealthProfileCart(
            @RequestBody Map<String, Object> request) {
        try {
            System.out.println("HealthProfileCart 저장 요청");
            
            // HealthProfileRequestDto 생성
            HealthProfileRequestDto requestDto = new HealthProfileRequestDto();
            requestDto.setMbId((String) request.get("mb_id"));
            requestDto.setItId((String) request.get("it_id"));
            requestDto.setAnswer1((String) request.get("answer1"));
            requestDto.setAnswer2((String) request.get("answer2"));
            requestDto.setAnswer3((String) request.get("answer3"));
            requestDto.setAnswer4((String) request.get("answer4"));
            requestDto.setAnswer5((String) request.get("answer5"));
            requestDto.setAnswer6((String) request.get("answer6"));
            requestDto.setAnswer7((String) request.get("answer7"));
            requestDto.setAnswer8((String) request.get("answer8"));
            requestDto.setAnswer9((String) request.get("answer9"));
            requestDto.setAnswer10((String) request.get("answer10"));
            requestDto.setAnswer11((String) request.get("answer11"));
            requestDto.setAnswer12((String) request.get("answer12"));
            requestDto.setAnswer13((String) request.get("answer13"));
            requestDto.setAnswer13Period((String) request.get("answer13Period"));
            requestDto.setAnswer13Dosage((String) request.get("answer13Dosage"));
            requestDto.setAnswer13Medicine((String) request.get("answer13Medicine"));
            requestDto.setAnswer71((String) request.get("answer71"));
            requestDto.setAnswer13Sideeffect((String) request.get("answer13Sideeffect"));
            requestDto.setPfMemo((String) request.get("pfMemo"));
            
            // 예약 정보 파싱
            Long odId = null;
            if (request.get("od_id") != null) {
                try {
                    odId = Long.parseLong(request.get("od_id").toString());
                } catch (NumberFormatException e) {
                    System.err.println("od_id 파싱 오류: " + e.getMessage());
                    throw new RuntimeException("od_id 형식이 올바르지 않습니다: " + request.get("od_id"));
                }
            } else {
                throw new RuntimeException("od_id는 필수입니다.");
            }
            String reservationDateStr = (String) request.get("reservationDate");
            String reservationTime = (String) request.get("reservationTime");
            String reservationName = (String) request.get("reservationName");
            String reservationTel = (String) request.get("reservationTel");
            String doctorName = (String) request.get("doctorName");
            
            // 예약 시간 파싱 (예: "18:30" -> 시작 시간, 종료 시간 계산)
            String reservationEndTime = "";
            if (reservationTime != null && !reservationTime.isEmpty()) {
                try {
                    String[] timeParts = reservationTime.split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);
                    // 30분 추가
                    int endMinute = minute + 30;
                    int endHour = hour;
                    if (endMinute >= 60) {
                        endHour++;
                        endMinute -= 60;
                    }
                    reservationEndTime = String.format("%02d:%02d", endHour, endMinute);
                } catch (Exception e) {
                    reservationEndTime = reservationTime;
                }
            }
            
            // 예약 일자 파싱
            LocalDate reservationDate = null;
            if (reservationDateStr != null && !reservationDateStr.isEmpty()) {
                try {
                    reservationDate = LocalDate.parse(reservationDateStr.substring(0, 10)); // ISO 날짜 형식
                } catch (Exception e) {
                    System.err.println("예약 일자 파싱 오류: " + e.getMessage());
                }
            }
            
            // HealthProfileCart 저장
            healthProfileCartService.saveHealthProfileCart(
                requestDto,
                odId,
                reservationDate,
                reservationTime,
                reservationEndTime,
                reservationName,
                reservationTel,
                doctorName
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "HealthProfileCart 저장 완료");
            
            System.out.println("HealthProfileCart 저장 완료 - odId: " + odId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("HealthProfileCart 저장 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "HealthProfileCart 저장 중 오류가 발생했습니다.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 장바구니 항목 수량 업데이트
     * PUT /api/cart/update/{ctId}
     */
    @PutMapping("/update/{ctId}")
    public ResponseEntity<Map<String, Object>> updateCartQuantity(
            @PathVariable Integer ctId,
            @RequestBody Map<String, Object> request) {
        try {
            System.out.println("장바구니 수량 업데이트 요청 - ctId: " + ctId);
            
            Integer quantity = request.get("quantity") != null ?
                (request.get("quantity") instanceof Integer ? (Integer) request.get("quantity") :
                 Integer.parseInt(request.get("quantity").toString())) : null;
            
            if (quantity == null || quantity < 1) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "수량은 1개 이상이어야 합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            Cart updatedCart = cartService.updateCartQuantity(ctId, quantity);
            
            if (updatedCart != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "수량이 변경되었습니다.");
                response.put("data", convertCartToMap(updatedCart));
                System.out.println("장바구니 수량 업데이트 완료 - ctId: " + ctId);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "장바구니 항목을 찾을 수 없습니다.");
                System.out.println("장바구니 항목 없음 - ctId: " + ctId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("장바구니 수량 업데이트 오류: " + e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            System.err.println("장바구니 수량 업데이트 오류: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "장바구니 수량 업데이트 중 오류가 발생했습니다.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 장바구니 항목 삭제
     * DELETE /api/cart/remove/{ctId}
     */
    @DeleteMapping("/remove/{ctId}")
    public ResponseEntity<Map<String, Object>> removeCartItem(@PathVariable Integer ctId) {
        try {
            System.out.println("장바구니 삭제 요청 - ctId: " + ctId);
            
            boolean deleted = cartService.deleteCartItem(ctId);
            
            Map<String, Object> response = new HashMap<>();
            if (deleted) {
                response.put("success", true);
                response.put("message", "장바구니에서 삭제되었습니다.");
                System.out.println("장바구니 삭제 완료 - ctId: " + ctId);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "장바구니 항목을 찾을 수 없습니다.");
                System.out.println("장바구니 항목 없음 - ctId: " + ctId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            System.err.println("장바구니 삭제 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "장바구니 삭제 중 오류가 발생했습니다.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Cart 엔티티를 Map으로 변환
     */
    private Map<String, Object> convertCartToMap(Cart cart) {
        Map<String, Object> map = new HashMap<>();
        map.put("ct_id", cart.getCtId());
        map.put("od_id", cart.getOdId());
        map.put("mb_id", cart.getMbId());
        map.put("it_id", cart.getItId());
        map.put("it_name", cart.getItName());
        map.put("it_subject", cart.getItSubject());
        map.put("ct_status", cart.getCtStatus());
        map.put("ct_price", cart.getCtPrice());
        map.put("ct_option", cart.getCtOption());
        map.put("ct_qty", cart.getCtQty());
        map.put("io_id", cart.getIoId());
        map.put("io_price", cart.getIoPrice());
        map.put("ct_kind", cart.getCtKind() != null ? cart.getCtKind().name() : "general");
        map.put("ct_time", cart.getCtTime() != null ? cart.getCtTime().toString() : null);
        
        // 상품 이미지 조회 (Product 테이블에서)
        // data/item/{it_id}/...jpg 형식으로 생성
        String imageUrl = null;
        Optional<Product> productOpt = productRepository.findById(cart.getItId());
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            String imageFileName = null;
            
            // Flutter 이미지 URL이 있으면 우선 사용, 없으면 일반 이미지 URL 사용
            if (product.getFlutterImageUrl() != null && !product.getFlutterImageUrl().isEmpty()) {
                imageFileName = product.getFlutterImageUrl();
            } else if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
                imageFileName = product.getImageUrl();
            }
            
            // 이미지 파일명이 있으면 data/item/{it_id}/ 형식으로 생성
            if (imageFileName != null && !imageFileName.isEmpty()) {
                // 이미 전체 URL인 경우 파일명만 추출
                if (imageFileName.contains("/")) {
                    imageFileName = imageFileName.substring(imageFileName.lastIndexOf("/") + 1);
                }
                // data/item/{it_id}/ 파일명 형식으로 생성 (상대 경로)
                // 프론트엔드에서 ImageUrlHelper를 통해 전체 URL로 변환됨
                imageUrl = "data/item/" + cart.getItId() + "/" + imageFileName;
            }
        }
        map.put("image_url", imageUrl);
        map.put("it_img", imageUrl); // 하위 호환성을 위해
        map.put("it_img1", imageUrl); // 하위 호환성을 위해
        
        return map;
    }
}

