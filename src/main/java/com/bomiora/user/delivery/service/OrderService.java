package com.bomiora.user.delivery.service;

import com.bomiora.user.delivery.dto.*;
import com.bomiora.user.delivery.entity.Order;
import com.bomiora.user.delivery.entity.OrderCart;
import com.bomiora.user.delivery.repository.OrderCartRepository;
import com.bomiora.user.delivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderCartRepository orderCartRepository;
    
    /**
     * 주문 목록 조회
     * 
     * @param mbId 회원 ID
     * @param period 기간 (개월 수: 1, 3, 6, 0=전체)
     * @param status 상태 (all, cancel, preparing, delivering, finish)
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @return 주문 목록
     */
    public Map<String, Object> getOrderList(String mbId, int period, String status, int page, int size) {
        // 자동 확정 처리 (조회 전에 실행)
        processAutoConfirm();
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            LocalDateTime startDate = period > 0 ? LocalDateTime.now().minusMonths(period) : null;
            
            Page<Object[]> ordersPage;
            
            // 상태별 필터링
            switch (status) {
                case "payment": // 결제완료 ('입금' 상태, 취소/반품 제외)
                    if (startDate != null) {
                        ordersPage = orderRepository.findOrdersPaymentCompletedByPeriod(mbId, startDate, pageable);
                    } else {
                        ordersPage = orderRepository.findOrdersPaymentCompleted(mbId, pageable);
                    }
                    break;
                    
                case "cancel": // 주문취소
                    ordersPage = orderRepository.findOrdersCancelled(mbId, pageable);
                    break;
                    
                case "preparing": // 배송준비중 ('준비' 상태만, '입금'은 결제완료 탭에서 처리)
                    if (startDate != null) {
                        ordersPage = orderRepository.findOrdersByMbIdAndPeriodAndStatuses(
                            mbId, startDate, Arrays.asList("준비"), pageable);
                    } else {
                        ordersPage = orderRepository.findOrdersByMbIdAndStatuses(
                            mbId, Arrays.asList("준비"), pageable);
                    }
                    break;
                    
                case "delivering": // 배송중 ('배송', '완료' 중 미수령)
                    ordersPage = orderRepository.findOrdersDelivering(mbId, pageable);
                    break;
                    
                case "finish": // 배송완료 (수령확인 완료)
                    ordersPage = orderRepository.findOrdersCompleted(mbId, pageable);
                    break;
                    
                default: // 전체
                    if (startDate != null) {
                        ordersPage = orderRepository.findOrdersByMbIdAndPeriod(mbId, startDate, pageable);
                    } else {
                        ordersPage = orderRepository.findOrdersByMbId(mbId, pageable);
                    }
                    break;
            }
            
            List<Object[]> orders = ordersPage.getContent();
            
            // 주문 ID 목록
            List<Long> odIds = orders.stream()
                .map(row -> ((Number) row[0]).longValue())
                .collect(Collectors.toList());
            
            // 주문 상품 목록 조회
            List<OrderCart> allCarts = orderCartRepository.findByOdIdInOrderByOdIdDescCtIdAsc(odIds);
            
            // 상품 이미지 URL 조회 (it_id 리스트 수집)
            List<String> itIds = allCarts.stream()
                .map(OrderCart::getItId)
                .filter(itId -> itId != null && !itId.isEmpty())
                .distinct()
                .collect(Collectors.toList());
            
            // 이미지 URL 맵 생성
            Map<String, String> imageUrlMap = new HashMap<>();
            if (!itIds.isEmpty()) {
                List<Object[]> imageResults = orderRepository.findItemImagesByItIds(itIds);
                for (Object[] row : imageResults) {
                    String itId = (String) row[0];
                    String img1 = row[1] != null ? (String) row[1] : null;
                    if (img1 != null && !img1.isEmpty()) {
                        imageUrlMap.put(itId, img1);
                    }
                }
            }
            
            // 주문별로 상품 그룹화
            Map<Long, List<OrderCart>> cartsMap = allCarts.stream()
                .collect(Collectors.groupingBy(OrderCart::getOdId));
            
            // DTO 변환
            List<OrderListDTO> orderList = orders.stream()
                .map(row -> convertToOrderListDTO(row, cartsMap.get(((Number) row[0]).longValue()), imageUrlMap))
                .collect(Collectors.toList());
            
            // 결과 반환
            result.put("orders", orderList);
            result.put("currentPage", page);
            result.put("totalPages", ordersPage.getTotalPages());
            result.put("totalItems", ordersPage.getTotalElements());
            result.put("hasNext", ordersPage.hasNext());
            
            return result;
            
        } catch (Exception e) {
            // 제로 날짜 에러 등 발생 시 빈 목록 반환
            result.put("orders", new ArrayList<>());
            result.put("currentPage", page);
            result.put("totalPages", 0);
            result.put("totalItems", 0L);
            result.put("hasNext", false);
            
            return result;
        }
    }
    
    /**
     * 주문 상세 조회
     * 
     * @param odId 주문 ID
     * @param mbId 회원 ID
     * @return 주문 상세 정보
     */
    public OrderDetailDTO getOrderDetail(Long odId, String mbId) {
        try {
            // 주문 조회 (Object[] 배열로 받음)
            Object[] orderRow = orderRepository.findOrderDetailById(odId, mbId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
            
            // 주문 상품 조회
            List<OrderCart> carts = orderCartRepository.findByOdIdAndMbIdOrderByCtIdAsc(odId, mbId);
            
            // 상품 이미지 URL 조회 (it_id 리스트 수집)
            List<String> itIds = carts.stream()
                .map(OrderCart::getItId)
                .filter(itId -> itId != null && !itId.isEmpty())
                .distinct()
                .collect(Collectors.toList());
            
            // 이미지 URL 맵 생성
            Map<String, String> imageUrlMap = new HashMap<>();
            if (!itIds.isEmpty()) {
                List<Object[]> imageResults = orderRepository.findItemImagesByItIds(itIds);
                for (Object[] row : imageResults) {
                    String itId = (String) row[0];
                    String img1 = row[1] != null ? (String) row[1] : null;
                    if (img1 != null && !img1.isEmpty()) {
                        imageUrlMap.put(itId, img1);
                    }
                }
            }
            
            // DTO 변환
            return convertToOrderDetailDTO(orderRow, carts, imageUrlMap);
        } catch (Exception e) {
            throw new RuntimeException("주문 정보를 불러올 수 없습니다.");
        }
    }
    
    /**
     * 주문 취소
     * 
     * @param odId 주문 ID
     * @param mbId 회원 ID
     * @return 취소 결과
     */
    @Transactional
    public boolean cancelOrder(Long odId, String mbId) {
        try {
            Order order = orderRepository.findById(odId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
            
            // 회원 확인
            if (!order.getMbId().equals(mbId)) {
                throw new RuntimeException("주문 정보가 일치하지 않습니다.");
            }
            
            // 취소 가능 상태 확인 ('주문', '입금', '준비'만 취소 가능)
            if (!Arrays.asList("주문", "입금", "준비").contains(order.getOdStatus())) {
                throw new RuntimeException("취소할 수 없는 상태입니다.");
            }
            
            // 이미 취소된 주문 확인
            if (order.getOdCancelPrice() != null && order.getOdCancelPrice() > 0) {
                throw new RuntimeException("이미 취소된 주문입니다.");
            }
            
            // 상태 변경
            order.setOdStatus("취소");
            order.setStatusChangedAt(LocalDateTime.now());
            orderRepository.save(order);
            
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * 구매 확정
     * 
     * @param odId 주문 ID
     * @param mbId 회원 ID
     * @return 확정 결과
     */
    @Transactional
    public boolean confirmPurchase(Long odId, String mbId) {
        try {
            Order order = orderRepository.findById(odId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
            
            // 회원 확인
            if (!order.getMbId().equals(mbId)) {
                throw new RuntimeException("주문 정보가 일치하지 않습니다.");
            }
            
            // 확정 가능 상태 확인 ('배송' 또는 '완료' 상태여야 함)
            if (!Arrays.asList("배송", "완료").contains(order.getOdStatus())) {
                throw new RuntimeException("구매 확정할 수 없는 상태입니다.");
            }
            
            // 이미 확정된 주문 확인
            if (order.getDeliveryCompleted() != null && order.getDeliveryCompleted() == 1) {
                throw new RuntimeException("이미 구매가 확정된 주문입니다.");
            }
            
            // 수령 확인 처리
            order.setDeliveryCompleted(1);
            order.setDeliveryCompletedAt(LocalDateTime.now());
            order.setOdStatus("완료");
            orderRepository.save(order);
            
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * 자동 확정 처리 (배치 작업)
     * auto_confirm_at이 현재 시간보다 이전인 주문을 자동으로 확정
     */
    @Transactional
    public void processAutoConfirm() {
        try {
            List<Order> targets = orderRepository.findAutoConfirmTargets(LocalDateTime.now());
            
            for (Order order : targets) {
                order.setDeliveryCompleted(1);
                order.setDeliveryCompletedAt(order.getAutoConfirmAt());
                order.setOdStatus("완료");
            }
            
            if (!targets.isEmpty()) {
                orderRepository.saveAll(targets);
            }
        } catch (Exception e) {
            // 제로 날짜 에러 등 예외 발생 시 무시하고 계속 진행 (자동 확정 기능은 선택적)
        }
    }
    
    /**
     * Object[] 배열을 OrderListDTO로 변환
     * 배열 순서: od_id, mb_id, od_name, od_email, od_hp, 
     *            od_addr1, od_addr2, od_addr3, od_status,
     *            od_cart_count, od_cart_price, od_send_cost, od_send_cost2,
     *            od_receipt_price, od_settle_case,
     *            od_delivery_company, od_invoice,
     *            od_time, od_invoice_time,
     *            delivery_completed, admin_completed, auto_confirm_at
     */
    private OrderListDTO convertToOrderListDTO(Object[] row, List<OrderCart> carts, Map<String, String> imageUrlMap) {
        OrderListDTO dto = new OrderListDTO();
        
        int idx = 0;
        dto.setOdId(((Number) row[idx++]).longValue());  // 0: od_id
        String mbId = (String) row[idx++];               // 1: mb_id
        String odName = (String) row[idx++];             // 2: od_name
        String odEmail = (String) row[idx++];            // 3: od_email
        String odHp = (String) row[idx++];               // 4: od_hp
        String odAddr1 = (String) row[idx++];            // 5: od_addr1
        String odAddr2 = (String) row[idx++];            // 6: od_addr2
        String odAddr3 = (String) row[idx++];            // 7: od_addr3
        String odStatus = (String) row[idx++];           // 8: od_status
        Integer odCartCount = toInteger(row[idx++]);     // 9: od_cart_count
        Integer odCartPrice = toInteger(row[idx++]);     // 10: od_cart_price
        Integer odSendCost = toInteger(row[idx++]);      // 11: od_send_cost
        Integer odSendCost2 = toInteger(row[idx++]);     // 12: od_send_cost2
        Integer odReceiptPrice = toInteger(row[idx++]);  // 13: od_receipt_price
        String odSettleCase = (String) row[idx++];       // 14: od_settle_case
        String odDeliveryCompany = (String) row[idx++];  // 15: od_delivery_company
        String odInvoice = (String) row[idx++];          // 16: od_invoice
        
        // 날짜 변환 (Timestamp -> LocalDateTime)
        LocalDateTime odTime = toLocalDateTime(row[idx++]);  // 17: od_time (NULLIF 처리됨)
        LocalDateTime odInvoiceTime = toLocalDateTime(row[idx++]);  // 18: od_invoice_time
        
        Integer deliveryCompleted = toInteger(row[idx++]);  // 19: delivery_completed
        Integer adminCompleted = toInteger(row[idx++]);     // 20: admin_completed
        
        LocalDateTime autoConfirmAt = toLocalDateTime(row[idx++]);  // 21: auto_confirm_at
        
        dto.setOrderDate(formatDate(odTime));
        dto.setOrderDateTime(formatDateTime(odTime));
        dto.setOdStatus(odStatus);
        dto.setDisplayStatus(getCustomerStatusDisplay(odStatus, deliveryCompleted, adminCompleted, autoConfirmAt));
        dto.setOdCartCount(odCartCount);
        dto.setTotalPrice(odReceiptPrice);
        
        // 주문 상품 목록 변환
        if (carts != null) {
            List<OrderItemDTO> items = carts.stream()
                .map(cart -> convertToOrderItemDTO(cart, imageUrlMap))
                .collect(Collectors.toList());
            dto.setItems(items);
        }
        
        return dto;
    }
    
    /**
     * Object[] 배열을 OrderDetailDTO로 변환
     * 배열 순서: od_id, mb_id, od_name, od_email, od_tel, od_hp,
     *            od_zip1, od_zip2, od_addr1, od_addr2, od_addr3, od_addr_jibeon,
     *            od_b_name, od_b_tel, od_b_hp,
     *            od_b_zip1, od_b_zip2, od_b_addr1, od_b_addr2, od_b_addr3, od_b_addr_jibeon,
     *            od_memo, od_status,
     *            od_cart_count, od_cart_price, od_cart_coupon,
     *            od_send_cost, od_send_cost2, od_send_coupon,
     *            od_receipt_price, od_cancel_price, od_receipt_point, od_coupon, od_misu,
     *            od_settle_case, od_bank_account, od_delivery_company, od_invoice,
     *            od_shop_memo, od_mod_history,
     *            od_time, od_invoice_time, od_receipt_time,
     *            delivery_completed, admin_completed, auto_confirm_at
     */
    private OrderDetailDTO convertToOrderDetailDTO(Object[] row, List<OrderCart> carts, Map<String, String> imageUrlMap) {
        OrderDetailDTO dto = new OrderDetailDTO();
        
        // JPA가 2차원 배열로 반환하는 경우 처리
        Object[] actualRow = row;
        if (row.length == 1 && row[0] instanceof Object[]) {
            actualRow = (Object[]) row[0];
        }
        
        int idx = 0;
        Long odId = toInteger(actualRow[idx++]).longValue();   // 0: od_id
        String mbId = (String) actualRow[idx++];               // 1: mb_id
        String odName = (String) actualRow[idx++];             // 2: od_name
        String odEmail = (String) actualRow[idx++];            // 3: od_email
        String odTel = (String) actualRow[idx++];              // 4: od_tel
        String odHp = (String) actualRow[idx++];               // 5: od_hp
        String odZip1 = (String) actualRow[idx++];             // 6: od_zip1
        String odZip2 = (String) actualRow[idx++];             // 7: od_zip2
        String odAddr1 = (String) actualRow[idx++];            // 8: od_addr1
        String odAddr2 = (String) actualRow[idx++];            // 9: od_addr2
        String odAddr3 = (String) actualRow[idx++];            // 10: od_addr3
        String odAddrJibeon = (String) actualRow[idx++];       // 11: od_addr_jibeon
        String odBName = (String) actualRow[idx++];            // 12: od_b_name
        String odBTel = (String) actualRow[idx++];             // 13: od_b_tel
        String odBHp = (String) actualRow[idx++];              // 14: od_b_hp
        String odBZip1 = (String) actualRow[idx++];            // 15: od_b_zip1
        String odBZip2 = (String) actualRow[idx++];            // 16: od_b_zip2
        String odBAddr1 = (String) actualRow[idx++];           // 17: od_b_addr1
        String odBAddr2 = (String) actualRow[idx++];           // 18: od_b_addr2
        String odBAddr3 = (String) actualRow[idx++];           // 19: od_b_addr3
        String odBAddrJibeon = (String) actualRow[idx++];      // 20: od_b_addr_jibeon
        String odMemo = (String) actualRow[idx++];             // 21: od_memo
        String odStatus = (String) actualRow[idx++];           // 22: od_status
        Integer odCartCount = toInteger(actualRow[idx++]);     // 23: od_cart_count
        Integer odCartPrice = toInteger(actualRow[idx++]);     // 24: od_cart_price
        Integer odCartCoupon = toInteger(actualRow[idx++]);    // 25: od_cart_coupon
        Integer odSendCost = toInteger(actualRow[idx++]);      // 26: od_send_cost
        Integer odSendCost2 = toInteger(actualRow[idx++]);     // 27: od_send_cost2
        Integer odSendCoupon = toInteger(actualRow[idx++]);    // 28: od_send_coupon
        Integer odReceiptPrice = toInteger(actualRow[idx++]);  // 29: od_receipt_price
        Integer odCancelPrice = toInteger(actualRow[idx++]);   // 30: od_cancel_price
        Integer odReceiptPoint = toInteger(actualRow[idx++]);  // 31: od_receipt_point
        Integer odCoupon = toInteger(actualRow[idx++]);        // 32: od_coupon
        Integer odMisu = toInteger(actualRow[idx++]);          // 33: od_misu
        String odSettleCase = (String) actualRow[idx++];       // 34: od_settle_case
        String odBankAccount = (String) actualRow[idx++];      // 35: od_bank_account
        String odDeliveryCompany = (String) actualRow[idx++];  // 36: od_delivery_company
        String odInvoice = (String) actualRow[idx++];          // 37: od_invoice
        String odShopMemo = (String) actualRow[idx++];         // 38: od_shop_memo
        String odModHistory = (String) actualRow[idx++];       // 39: od_mod_history
        
        // 날짜 변환
        LocalDateTime odTime = toLocalDateTime(actualRow[idx++]);           // 40: od_time
        LocalDateTime odInvoiceTime = toLocalDateTime(actualRow[idx++]);    // 41: od_invoice_time
        LocalDateTime odReceiptTime = toLocalDateTime(actualRow[idx++]);    // 42: od_receipt_time
        
        Integer deliveryCompleted = toInteger(actualRow[idx++]);  // 43: delivery_completed
        Integer adminCompleted = toInteger(actualRow[idx++]);     // 44: admin_completed
        LocalDateTime autoConfirmAt = toLocalDateTime(actualRow[idx++]);    // 45: auto_confirm_at
        
        // 주문 기본 정보
        dto.setOdId(odId);
        dto.setOrderDate(formatDateTime(odTime));
        dto.setOdStatus(odStatus);
        dto.setDisplayStatus(getCustomerStatusDisplay(odStatus, deliveryCompleted, adminCompleted, autoConfirmAt));
        
        // 배송 정보
        dto.setRecipientName(odName);
        dto.setRecipientPhone(odHp);
        dto.setRecipientAddress(odAddr1);
        dto.setRecipientAddressDetail(odAddr2 + " " + odAddr3);
        dto.setDeliveryMessage(odMemo);
        dto.setDeliveryCompany(odDeliveryCompany);
        dto.setTrackingNumber(odInvoice);
        
        // 주문 상품 목록
        List<OrderItemDTO> items = carts.stream()
            .map(cart -> convertToOrderItemDTO(cart, imageUrlMap))
            .collect(Collectors.toList());
        dto.setProducts(items);
        
        // 결제 정보
        dto.setProductPrice(odCartPrice);
        dto.setDeliveryFee(odSendCost + odSendCost2);
        dto.setDiscountAmount(odCartCoupon + odSendCoupon + odCoupon + odReceiptPoint);
        dto.setTotalPrice(odReceiptPrice);
        dto.setPaymentMethod(odSettleCase);
        
        // 간편결제 상세 정보 (od_bank_account에서 추출)
        if (odBankAccount != null && !odBankAccount.isEmpty() && 
            (odSettleCase.contains("간편결제") || odSettleCase.contains("신용카드"))) {
            // 카카오페이, 네이버페이 등 추출
            if (odBankAccount.contains("카카오")) {
                dto.setPaymentMethodDetail(" (카카오페이)");
            } else if (odBankAccount.contains("네이버")) {
                dto.setPaymentMethodDetail(" (네이버페이)");
            } else if (odBankAccount.contains("토스")) {
                dto.setPaymentMethodDetail(" (토스페이)");
            } else if (!odBankAccount.isEmpty() && !odBankAccount.equals("0")) {
                dto.setPaymentMethodDetail(" (" + odBankAccount + ")");
            }
        }
        
        // 주문자 정보
        dto.setOrdererName(odBName);
        dto.setOrdererPhone(odBHp);
        dto.setOrdererEmail(odEmail);
        
        // 취소 정보 파싱 (취소/반품 상태인 경우만)
        if (odStatus.equals("취소") || odStatus.equals("반품")) {
            parseCancelInfo(dto, odShopMemo, odModHistory);
        }
        
        return dto;
    }
    
    /**
     * 취소 정보 파싱
     */
    private void parseCancelInfo(OrderDetailDTO dto, String odShopMemo, String odModHistory) {
        // 1. 고객 직접 취소 확인 (od_shop_memo)
        if (odShopMemo != null && odShopMemo.contains("주문자 본인 직접 취소")) {
            dto.setCancelType("고객직접");
            // "주문자 본인 직접 취소 - 2025-09-19 20:27:58 (취소이유 : 고객직접취소)"
            if (odShopMemo.contains("취소이유")) {
                String[] parts = odShopMemo.split("취소이유");
                if (parts.length > 1) {
                    String reason = parts[1].replace(":", "").replace(")", "").trim();
                    dto.setCancelReason(reason);
                }
            }
        }
        // 2. 시스템 자동 취소 확인 (od_shop_memo)
        else if (odShopMemo != null && odShopMemo.contains("시스템 자동 취소")) {
            dto.setCancelType("시스템자동");
            // "시스템 자동 취소 - 2025-09-17 09:33:17 (취소이유 : 가상계좌 입금기한 초과)"
            if (odShopMemo.contains("취소이유")) {
                String[] parts = odShopMemo.split("취소이유");
                if (parts.length > 1) {
                    String reason = parts[1].replace(":", "").replace(")", "").trim();
                    dto.setCancelReason(reason);
                }
            }
        }
        // 3. 관리자 취소 확인 (od_mod_history)
        else if (odModHistory != null && odModHistory.contains("주문취소 처리")) {
            dto.setCancelType("관리자");
            // "2025-11-05 17:12:25 skcompany 주문취소 처리 PG 결제 승인취소 처리"
            dto.setCancelReason("관리자가 주문을 취소했습니다.");
        }
    }
    
    /**
     * OrderCart 엔티티를 OrderItemDTO로 변환
     */
    private OrderItemDTO convertToOrderItemDTO(OrderCart cart, Map<String, String> imageUrlMap) {
        OrderItemDTO dto = new OrderItemDTO(
            cart.getCtId(),
            cart.getItId(),
            cart.getItName(),
            cart.getItSubject(),
            cart.getCtOption(),
            cart.getCtQty(),
            cart.getCtPrice(),
            cart.getIoPrice() != null ? cart.getIoPrice() : 0,
            cart.getCtStatus()
        );
        
        // 이미지 URL 설정
        if (imageUrlMap != null && cart.getItId() != null) {
            String imageUrl = imageUrlMap.get(cart.getItId());
            dto.setImageUrl(imageUrl != null ? imageUrl : "");
        } else {
            dto.setImageUrl("");
        }
        
        return dto;
    }
    
    /**
     * 사용자에게 표시되는 주문 상태 변환 (Object[] 배열용)
     */
    private String getCustomerStatusDisplay(String odStatus, Integer deliveryCompleted, 
                                            Integer adminCompleted, LocalDateTime autoConfirmAt) {
        
        // 1. 수령 확인 완료
        if (deliveryCompleted != null && deliveryCompleted == 1) {
            return "배송완료";
        }
        
        // 2. 관리자 완료 처리 + 자동 확정 시간 경과
        if (adminCompleted != null && adminCompleted == 1 && 
            autoConfirmAt != null && autoConfirmAt.isBefore(LocalDateTime.now())) {
            return "배송완료";
        }
        
        // 3. 관리자 완료 처리 + 배송 상태
        if (adminCompleted != null && adminCompleted == 1 && 
            (odStatus.equals("배송") || odStatus.equals("완료"))) {
            return "배송중";
        }
        
        // 4. 일반 상태 변환
        switch (odStatus) {
            case "주문":
            case "입금":
                return "결제완료";
            case "준비":
                return "배송준비중";
            case "배송":
            case "완료":
                return "배송중";
            case "취소":
            case "반품":
                return "취소/반품";
            default:
                return odStatus;
        }
    }
    
    /**
     * 사용자에게 표시되는 주문 상태 변환 (Order 엔티티용)
     */
    private String getCustomerStatusDisplay(Order order) {
        return getCustomerStatusDisplay(
            order.getOdStatus(),
            order.getDeliveryCompleted(),
            order.getAdminCompleted(),
            order.getAutoConfirmAt()
        );
    }
    
    /**
     * Object를 Integer로 변환
     */
    private Integer toInteger(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }
    
    /**
     * Object를 LocalDateTime으로 변환 (Timestamp 처리)
     */
    private LocalDateTime toLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Timestamp) {
            return ((Timestamp) value).toLocalDateTime();
        }
        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }
        return null;
    }
    
    /**
     * 날짜 포맷팅 (yyyy.MM.dd)
     */
    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
    
    /**
     * 날짜시간 포맷팅 (yyyy.MM.dd HH:mm)
     */
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}

