package com.bomiora.shopping.cart.service;

import com.bomiora.shopping.cart.entity.Cart;
import com.bomiora.shopping.cart.repository.CartRepository;
import com.bomiora.shopping.product.entity.Product;
import com.bomiora.shopping.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartService {
    
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final HealthProfileCartService healthProfileCartService;
    
    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       HealthProfileCartService healthProfileCartService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.healthProfileCartService = healthProfileCartService;
    }
    
    /**
     * 장바구니에 상품 추가 
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @param quantity 수량
     * @param price 가격 (총 가격)
     * @param optionId 옵션 ID (선택사항)
     * @param optionText 옵션 텍스트 (선택사항)
     * @param optionPrice 옵션 가격 (선택사항)
     * @param odId 주문 ID (처방 예약 플로우의 경우)
     * @return 저장된 Cart 엔티티
     */
    @Transactional
    public Cart addToCart(String mbId, String itId, Integer quantity, Integer price,
                          String optionId, String optionText, Integer optionPrice, Long odId) {
        System.out.println("장바구니 추가 요청 - mbId: " + mbId + ", itId: " + itId + ", quantity: " + quantity);
        
        // 상품 정보 조회
        Optional<Product> productOpt = productRepository.findById(itId);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("상품을 찾을 수 없습니다: " + itId);
        }
        
        Product product = productOpt.get();
        
        // 동일 상품/옵션 조합이 있는지 확인 (UPDATE 로직)
        // 옵션이 없으면 빈 문자열로 검색 (옵션 없는 상품과 매칭)
        String ioIdForSearch = (optionId != null && !optionId.isEmpty()) ? optionId : "";
        Cart existingCart = cartRepository.findByMbIdAndItIdAndIoIdAndCtStatus(
            mbId, itId, ioIdForSearch, "쇼핑"
        );
        
        if (existingCart != null) {
            // UPDATE: 동일 상품/옵션일 경우 수량 증가
            System.out.println("동일 상품/옵션 발견 - 기존 수량: " + existingCart.getCtQty() + ", 추가 수량: " + quantity);
            existingCart.setCtQty(existingCart.getCtQty() + quantity);
            existingCart.setCtPrice(existingCart.getCtPrice() + price);
            existingCart.setCtTime(LocalDateTime.now());
            
            // 포인트 재계산
            Integer calculatedPoint = calculatePoint(product, optionId, optionPrice, existingCart.getCtQty());
            existingCart.setCtPoint(calculatedPoint);
            
            Cart updatedCart = cartRepository.save(existingCart);
            System.out.println("장바구니 수량 업데이트 완료 - ctId: " + updatedCart.getCtId());
            return updatedCart;
        }
        
        // INSERT: 새로운 상품/옵션 조합
        // od_id가 없으면 생성
        Long finalOdId = odId;
        if (finalOdId == null) {
            finalOdId = generateOrderId(mbId, itId);
        }
        
        // Cart 엔티티 생성
        Cart cart = new Cart();
        cart.setOdId(finalOdId);
        cart.setMbId(mbId);
        cart.setItId(itId);
        cart.setItName(product.getName() != null ? product.getName() : "");
        
        // it_subject는 빈값으로 무조건 설정
        cart.setItSubject("");
        
        cart.setCtStatus("쇼핑");
        cart.setCtPrice(price);
        cart.setCtQty(quantity);
        cart.setCtTime(LocalDateTime.now());
        
        // 상품에서 배송비 정보 복사 (PHP: $it['it_sc_type'] 등)
        cart.setItScType(product.getItScType() != null ? product.getItScType() : (byte) 0);
        cart.setItScMethod(product.getItScMethod() != null ? product.getItScMethod() : (byte) 0);
        
        // 배송비 정보 출력 (디버깅용)
        Integer itScPrice = product.getItScPrice() != null ? product.getItScPrice() : 0;
        Integer itScMinimum = product.getItScMinimum() != null ? product.getItScMinimum() : 0;
        System.out.println("📦 [배송비 정보] it_sc_price: " + itScPrice + ", it_sc_minimum: " + itScMinimum + " (상품 ID: " + itId + ")");
        
        cart.setItScPrice(itScPrice);
        cart.setItScMinimum(itScMinimum);
        cart.setItScQty(product.getItScQty() != null ? product.getItScQty() : 0);
        
        // ct_send_cost 계산 (PHP: cartupdate.php 로직)
        byte ctSendCost = calculateSendCost(product);
        cart.setCtSendCost(ctSendCost);
        
        // 옵션 정보 설정
        if (optionId != null && !optionId.isEmpty()) {
            cart.setIoId(optionId);
            // ct_option 형식: "디톡스 / 3일" (프론트엔드에서 이미 형식화되어 전달됨)
            cart.setCtOption(optionText != null ? optionText : "");
            System.out.println("📦 [옵션 정보] io_id: " + optionId + ", ct_option: " + (optionText != null ? optionText : ""));
            cart.setIoPrice(optionPrice != null ? optionPrice : 0);
            // io_type 설정 (선택옵션: 0, 추가옵션: 1) - 기본값은 0 (선택옵션)
            cart.setIoType((byte) 0);
        } else {
            cart.setIoId("");
            cart.setCtOption("");
            cart.setIoPrice(0);
            cart.setIoType((byte) 0);
        }
        
        // 포인트 계산 (PHP: get_item_point() 로직)
        Integer calculatedPoint = calculatePoint(product, optionId, optionPrice, quantity);
        cart.setCtPoint(calculatedPoint != null ? calculatedPoint : 0);
        
        // 기본값 설정
        cart.setCtHistory("");
        cart.setCpPrice(0);
        cart.setCtPointUse((byte) 0);
        cart.setCtStockUse((byte) 0);
        cart.setCtNotax((byte) 0);
        cart.setCtIp("127.0.0.1");
        cart.setCtDirect((byte) 0);
        cart.setCtSelect((byte) 0);
        cart.setInfCode("");
        cart.setCtOutput(Cart.OutputType.Y);
        
        // 처방 상품인지 확인 (it_kind가 'prescription'인 경우)
        String productKind = product.getProductKind();
        if (productKind != null && productKind.equals("prescription")) {
            cart.setCtKind(Cart.CartKind.prescription);
        } else {
            cart.setCtKind(Cart.CartKind.general);
        }
        
        cart.setCtMbInf("");
        cart.setCtInfPrice(0);
        cart.setCtSettlementStatus("N");
        cart.setCtSelectTime(LocalDateTime.now());
        
        Cart savedCart = cartRepository.save(cart);
        
        System.out.println("장바구니 추가 완료 - ctId: " + savedCart.getCtId() + ", odId: " + finalOdId);
        
        // 처방전 상품인 경우: HealthProfileCart의 od_id 업데이트
        // PHP: update ... set od_id = ... where mb_id = ... and it_id = ... and hp_status = '쇼핑'
        if (productKind != null && productKind.equals("prescription")) {
            try {
                boolean updated = healthProfileCartService.updateOdId(mbId, itId, finalOdId);
                if (updated) {
                    System.out.println("처방전 상품: HealthProfileCart의 od_id 업데이트 완료");
                } else {
                    System.out.println("처방전 상품: HealthProfileCart를 찾을 수 없음 (처방전 작성 전일 수 있음)");
                }
            } catch (Exception e) {
                System.err.println("처방전 상품: HealthProfileCart od_id 업데이트 실패: " + e.getMessage());
                // 에러가 발생해도 장바구니 추가는 성공한 것으로 처리
            }
        }
        
        return savedCart;
    }
    
    /**
     * ct_send_cost 계산 (PHP cartupdate.php 로직)
     * @param product 상품 정보
     * @return ct_send_cost 값 (0: 선불, 1: 착불, 2: 무료)
     */
    private byte calculateSendCost(Product product) {
        Byte itScType = product.getItScType();
        Byte itScMethod = product.getItScMethod();
        
        if (itScType == null) {
            return (byte) 0; // 기본값: 선불
        }
        
        // PHP: if($it['it_sc_type'] == 1) { $ct_send_cost = 2; } // 무료
        if (itScType == 1) {
            return (byte) 2; // 무료
        }
        
        // PHP: else if($it['it_sc_type'] > 1 && $it['it_sc_method'] == 1) { $ct_send_cost = 1; } // 착불
        if (itScType > 1 && itScMethod != null && itScMethod == 1) {
            return (byte) 1; // 착불
        }
        
        // 기본값: 선불
        return (byte) 0;
    }
    
    /**
     * 포인트 계산 (PHP get_item_point() 로직)
     * @param product 상품 정보
     * @param optionId 옵션 ID
     * @param optionPrice 옵션 가격
     * @param quantity 수량
     * @return 계산된 포인트
     */
    private Integer calculatePoint(Product product, String optionId, Integer optionPrice, Integer quantity) {
        // 선택옵션인 경우: 상품 가격 기반 계산
        if (optionId != null && !optionId.isEmpty()) {
            // PHP: get_item_point($it, $io_id) - 상품 가격 기반 계산
            Integer basePrice = product.getPrice() != null ? product.getPrice() : 0;
            Integer totalPrice = basePrice + (optionPrice != null ? optionPrice : 0);
            
            // 포인트 타입에 따라 계산 (1: 고정, 2: 비율)
            Integer pointType = product.getPointType();
            Integer point = product.getPoint();
            
            if (pointType != null && point != null) {
                if (pointType == 1) {
                    // 고정 포인트
                    return point * quantity;
                } else if (pointType == 2) {
                    // 비율 포인트 (예: 1%)
                    return (int) (totalPrice * point / 100) * quantity;
                }
            }
            
            // 기본값: 총 가격의 1%
            return (int) (totalPrice * 0.01) * quantity;
        } else {
            // 추가옵션인 경우: $it['it_supply_point'] 고정 포인트
            Integer supplyPoint = product.getSupplyPoint();
            if (supplyPoint != null) {
                return supplyPoint * quantity;
            }
        }
        
        return 0;
    }
    
    /**
     * 주문 ID(od_id) 생성
     * 타임스탬프 기반으로 생성 (예: 20250101123456 형태)
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @return 생성된 od_id
     */
    @Transactional
    public Long generateOrderId(String mbId, String itId) {
        // 현재 타임스탬프를 기반으로 od_id 생성 (YYYYMMDDHHmmss + 랜덤 4자리)
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String timestamp = String.format("%04d%02d%02d%02d%02d%02d",
            now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
            now.getHour(), now.getMinute(), now.getSecond());
        
        // 랜덤 4자리 추가 (중복 방지)
        int random = (int)(Math.random() * 10000);
        String randomStr = String.format("%04d", random);
        
        // 최종 od_id 생성 (타임스탬프 + 랜덤)
        String odIdStr = timestamp + randomStr;
        Long odId = Long.parseLong(odIdStr);
        
        System.out.println("주문 ID 생성 - mbId: " + mbId + ", itId: " + itId + ", odId: " + odId);
        
        return odId;
    }
    
    /**
     * 사용자 장바구니 조회 (ct_status가 '쇼핑'인 것만)
     * @param mbId 사용자 ID
     * @return 장바구니 목록
     */
    public List<Cart> getCartByUserId(String mbId) {
        System.out.println("장바구니 조회 요청 - 사용자 ID: " + mbId);
        List<Cart> carts = cartRepository.findByMbIdAndCtStatus(mbId, "쇼핑");
        System.out.println("장바구니 조회 완료 - 개수: " + carts.size());
        return carts;
    }
    
    /**
     * 사용자 장바구니 조회 (상태 필터링)
     * @param mbId 사용자 ID
     * @param ctStatus 장바구니 상태
     * @return 장바구니 목록
     */
    public List<Cart> getCartByUserIdAndStatus(String mbId, String ctStatus) {
        System.out.println("장바구니 조회 요청 - 사용자 ID: " + mbId + ", 상태: " + ctStatus);
        List<Cart> carts = cartRepository.findByMbIdAndCtStatus(mbId, ctStatus);
        System.out.println("장바구니 조회 완료 - 개수: " + carts.size());
        return carts;
    }
    
    /**
     * 장바구니 배송비 계산 (PHP get_sendcost() 로직)
     * @param carts 장바구니 목록
     * @return 계산된 배송비
     */
    @Transactional(readOnly = true)
    public Integer calculateShippingCost(List<Cart> carts) {
        if (carts == null || carts.isEmpty()) {
            return 0;
        }
        
        System.out.println("배송비 계산 시작 - 장바구니 항목 수: " + carts.size());
        
        int totalShippingCost = 0; // 상품별 배송비 합계
        int totalPriceForDefault = 0; // 쇼핑몰 기본설정 사용 상품들의 합계
        int defaultShippingCount = 0; // 쇼핑몰 기본설정 사용 상품 개수
        
        // 상품별로 그룹화 (같은 it_id의 여러 옵션 합산)
        Map<String, List<Cart>> productGroupMap = new HashMap<>();
        for (Cart cart : carts) {
            String itId = cart.getItId();
            productGroupMap.computeIfAbsent(itId, k -> new ArrayList<>()).add(cart);
        }
        
        // 각 상품별로 배송비 계산
        for (Map.Entry<String, List<Cart>> entry : productGroupMap.entrySet()) {
            String itId = entry.getKey();
            List<Cart> productCarts = entry.getValue();
            
            // 같은 상품의 여러 옵션 합계 계산
            int productTotalPrice = 0;
            int productTotalQty = 0;
            
            for (Cart cart : productCarts) {
                // PHP 로직: SUM(IF(io_type = 1, (io_price * ct_qty), ((ct_price + io_price) * ct_qty)))
                // io_type = 1 (추가옵션): io_price * ct_qty
                // io_type = 0 (선택옵션): (ct_price + io_price) * ct_qty
                if (cart.getIoType() != null && cart.getIoType() == 1) {
                    // 추가옵션: io_price * ct_qty
                    productTotalPrice += (cart.getIoPrice() != null ? cart.getIoPrice() : 0) * cart.getCtQty();
                } else {
                    // 선택옵션: (ct_price + io_price) * ct_qty
                    productTotalPrice += (cart.getCtPrice() + (cart.getIoPrice() != null ? cart.getIoPrice() : 0)) * cart.getCtQty();
                }
                productTotalQty += cart.getCtQty();
            }
            
            // 첫 번째 장바구니 항목의 배송비 설정 사용 (같은 상품이므로 동일)
            Cart firstCart = productCarts.get(0);
            Byte itScType = firstCart.getItScType();
            
            // 상품별 배송비 계산
            Integer itemShippingCost = calculateItemShippingCost(itId, productTotalPrice, productTotalQty, firstCart);
            
            if (itemShippingCost != null) {
                if (itemShippingCost == -1) {
                    // 쇼핑몰 기본설정 사용 (it_sc_type = 0)
                    totalPriceForDefault += productTotalPrice;
                    defaultShippingCount++;
                } else if (itemShippingCost > 0) {
                    // 상품별 배송비 부과
                    totalShippingCost += itemShippingCost;
                }
                // itemShippingCost == 0 이면 무료배송이므로 추가하지 않음
            }
        }
        
        // 쇼핑몰 기본설정 배송비 계산 (현재는 기본값 0원, 나중에 g5_shop_default 테이블 연동 시 구현)
        // TODO: g5_shop_default 테이블에서 de_send_cost_limit, de_send_cost_list 조회하여 계산
        int defaultShippingCost = 0;
        if (defaultShippingCount > 0 && totalPriceForDefault > 0) {
            // 임시로 기본값: 30,000원 이상 무료
            if (totalPriceForDefault < 30000) {
                defaultShippingCost = 3000; // 기본 배송비 3,000원
            } else {
                defaultShippingCost = 0; // 30,000원 이상 무료
            }
        }
        
        int finalShippingCost = totalShippingCost + defaultShippingCost;
        System.out.println("배송비 계산 완료 - 상품별 배송비: " + totalShippingCost + ", 기본설정 배송비: " + defaultShippingCost + ", 총 배송비: " + finalShippingCost);
        
        return finalShippingCost;
    }
    
    /**
     * 상품별 배송비 계산 (PHP get_item_sendcost() 로직)
     * @param itId 상품 ID
     * @param price 상품 합계 금액 (같은 상품의 여러 옵션 합산)
     * @param qty 상품 합계 수량
     * @param cart 장바구니 항목 (배송비 설정 참조용)
     * @return 배송비 (-1: 쇼핑몰 기본설정 사용, 0: 무료, >0: 배송비 금액)
     */
    private Integer calculateItemShippingCost(String itId, Integer price, Integer qty, Cart cart) {
        Byte itScType = cart.getItScType();
        
        if (itScType == null) {
            return 0; // 기본값: 무료
        }
        
        // it_sc_type = 0: 쇼핑몰 기본설정 사용
        if (itScType == 0) {
            return -1; // 나중에 전체 합계로 계산
        }
        
        // it_sc_type = 1: 무료배송
        if (itScType == 1) {
            return 0;
        }
        
        // it_sc_type = 2: 조건부 무료배송
        if (itScType == 2) {
            Integer itScMinimum = cart.getItScMinimum();
            Integer itScPrice = cart.getItScPrice();
            
            if (itScMinimum != null && price >= itScMinimum) {
                return 0; // 무료
            } else {
                return (itScPrice != null ? itScPrice : 0); // 배송비 부과
            }
        }
        
        // it_sc_type = 3: 유료배송
        if (itScType == 3) {
            Integer itScPrice = cart.getItScPrice();
            return (itScPrice != null ? itScPrice : 0);
        }
        
        // it_sc_type = 4: 수량별 부과
        if (itScType == 4) {
            Integer itScPrice = cart.getItScPrice();
            Integer itScQty = cart.getItScQty();
            
            if (itScPrice != null && itScQty != null && itScQty > 0) {
                int q = (int) Math.ceil((double) qty / itScQty);
                return itScPrice * q;
            }
            return 0;
        }
        
        return 0; // 기본값: 무료
    }
    
    /**
     * 장바구니 항목 수량 업데이트
     * @param ctId 장바구니 ID
     * @param newQuantity 새로운 수량
     * @return 업데이트된 Cart 엔티티 (없으면 null)
     */
    @Transactional
    public Cart updateCartQuantity(Integer ctId, Integer newQuantity) {
        System.out.println("장바구니 수량 업데이트 요청 - ctId: " + ctId + ", newQuantity: " + newQuantity);
        
        if (newQuantity < 1) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
        
        Optional<Cart> cartOpt = cartRepository.findById(ctId);
        if (cartOpt.isEmpty()) {
            System.out.println("장바구니 항목 없음 - ctId: " + ctId);
            return null;
        }
        
        Cart cart = cartOpt.get();
        
        // 상품 정보 조회
        Optional<Product> productOpt = productRepository.findById(cart.getItId());
        if (productOpt.isEmpty()) {
            throw new RuntimeException("상품을 찾을 수 없습니다: " + cart.getItId());
        }
        
        Product product = productOpt.get();
        
        // 단가 계산 (기존 가격 / 기존 수량)
        Integer unitPrice = cart.getCtPrice() / cart.getCtQty();
        
        // 새 가격 계산
        Integer newPrice = unitPrice * newQuantity;
        
        // 수량 및 가격 업데이트
        cart.setCtQty(newQuantity);
        cart.setCtPrice(newPrice);
        cart.setCtTime(LocalDateTime.now());
        
        // 포인트 재계산
        Integer calculatedPoint = calculatePoint(product, cart.getIoId(), cart.getIoPrice(), newQuantity);
        cart.setCtPoint(calculatedPoint);
        
        Cart updatedCart = cartRepository.save(cart);
        System.out.println("장바구니 수량 업데이트 완료 - ctId: " + ctId + ", 수량: " + newQuantity + ", 가격: " + newPrice);
        
        return updatedCart;
    }
    
    /**
     * 장바구니 항목 삭제
     * @param ctId 장바구니 ID
     * @return 삭제 성공 여부
     */
    @Transactional
    public boolean deleteCartItem(Integer ctId) {
        System.out.println("장바구니 삭제 요청 - ctId: " + ctId);
        
        Optional<Cart> cartOpt = cartRepository.findById(ctId);
        if (cartOpt.isPresent()) {
            cartRepository.delete(cartOpt.get());
            System.out.println("장바구니 삭제 완료 - ctId: " + ctId);
            return true;
        } else {
            System.out.println("장바구니 항목 없음 - ctId: " + ctId);
            return false;
        }
    }
}

