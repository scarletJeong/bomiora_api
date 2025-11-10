package com.bomiora.user.delivery.repository;

import com.bomiora.user.delivery.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * 회원 ID로 주문 조회 (최신순)
     * 제로 날짜 컬럼만 NULLIF 처리
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId",
           nativeQuery = true)
    Page<Object[]> findOrdersByMbId(@Param("mbId") String mbId, Pageable pageable);
    
    /**
     * 회원 ID와 기간으로 주문 조회
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId AND NULLIF(od_time, '0000-00-00 00:00:00') >= :startDate " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId",
           nativeQuery = true)
    Page<Object[]> findOrdersByMbIdAndPeriod(
        @Param("mbId") String mbId,
        @Param("startDate") LocalDateTime startDate,
        Pageable pageable
    );
    
    /**
     * 회원 ID와 상태로 주문 조회 (취소/반품 제외)
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId AND od_status IN :statuses " +
           "AND (od_cancel_price IS NULL OR od_cancel_price = 0) " +
           "AND od_status NOT IN ('취소', '반품') " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId AND od_status IN :statuses AND (od_cancel_price IS NULL OR od_cancel_price = 0) AND od_status NOT IN ('취소', '반품')",
           nativeQuery = true)
    Page<Object[]> findOrdersByMbIdAndStatuses(
        @Param("mbId") String mbId,
        @Param("statuses") List<String> statuses,
        Pageable pageable
    );
    
    /**
     * 회원 ID, 기간, 상태로 주문 조회 (취소/반품 제외)
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId AND NULLIF(od_time, '0000-00-00 00:00:00') >= :startDate " +
           "AND od_status IN :statuses " +
           "AND (od_cancel_price IS NULL OR od_cancel_price = 0) " +
           "AND od_status NOT IN ('취소', '반품') " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId AND NULLIF(od_time, '0000-00-00 00:00:00') >= :startDate AND od_status IN :statuses AND (od_cancel_price IS NULL OR od_cancel_price = 0) AND od_status NOT IN ('취소', '반품')",
           nativeQuery = true)
    Page<Object[]> findOrdersByMbIdAndPeriodAndStatuses(
        @Param("mbId") String mbId,
        @Param("startDate") LocalDateTime startDate,
        @Param("statuses") List<String> statuses,
        Pageable pageable
    );
    
    /**
     * 배송중 주문 조회
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId AND od_status IN ('배송', '완료') " +
           "AND (delivery_completed IS NULL OR delivery_completed != 1) " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId AND od_status IN ('배송', '완료')",
           nativeQuery = true)
    Page<Object[]> findOrdersDelivering(
        @Param("mbId") String mbId,
        Pageable pageable
    );
    
    /**
     * 배송완료 주문 조회
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId AND delivery_completed = 1 " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId AND delivery_completed = 1",
           nativeQuery = true)
    Page<Object[]> findOrdersCompleted(
        @Param("mbId") String mbId,
        Pageable pageable
    );
    
    /**
     * 결제완료 주문 조회 (입금 상태, 취소/반품 제외)
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId AND od_status = '입금' " +
           "AND (od_cancel_price IS NULL OR od_cancel_price = 0) " +
           "AND od_status NOT IN ('취소', '반품') " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId AND od_status = '입금' AND (od_cancel_price IS NULL OR od_cancel_price = 0) AND od_status NOT IN ('취소', '반품')",
           nativeQuery = true)
    Page<Object[]> findOrdersPaymentCompleted(
        @Param("mbId") String mbId,
        Pageable pageable
    );
    
    /**
     * 결제완료 주문 조회 (기간 포함, 취소/반품 제외)
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId AND NULLIF(od_time, '0000-00-00 00:00:00') >= :startDate " +
           "AND od_status = '입금' " +
           "AND (od_cancel_price IS NULL OR od_cancel_price = 0) " +
           "AND od_status NOT IN ('취소', '반품') " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId AND NULLIF(od_time, '0000-00-00 00:00:00') >= :startDate AND od_status = '입금' AND (od_cancel_price IS NULL OR od_cancel_price = 0) AND od_status NOT IN ('취소', '반품')",
           nativeQuery = true)
    Page<Object[]> findOrdersPaymentCompletedByPeriod(
        @Param("mbId") String mbId,
        @Param("startDate") LocalDateTime startDate,
        Pageable pageable
    );
    
    /**
     * 주문취소 조회
     */
    @Query(value = "SELECT od_id, mb_id, od_name, od_email, od_hp, " +
           "od_addr1, od_addr2, od_addr3, od_status, " +
           "od_cart_count, od_cart_price, od_send_cost, od_send_cost2, " +
           "od_receipt_price, od_settle_case, " +
           "od_delivery_company, od_invoice, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE mb_id = :mbId AND od_status NOT IN ('주문', '입금', '준비', '배송', '완료') " +
           "ORDER BY od_id DESC",
           countQuery = "SELECT COUNT(*) FROM bomiora_shop_order WHERE mb_id = :mbId AND od_status NOT IN ('주문', '입금', '준비', '배송', '완료')",
           nativeQuery = true)
    Page<Object[]> findOrdersCancelled(
        @Param("mbId") String mbId,
        Pageable pageable
    );
    
    /**
     * 주문 ID와 회원 ID로 주문 상세 조회
     * 제로 날짜를 NULLIF로 변환
     */
    @Query(value = "SELECT " +
           "od_id, mb_id, od_name, od_email, od_tel, od_hp, " +
           "od_zip1, od_zip2, od_addr1, od_addr2, od_addr3, od_addr_jibeon, " +
           "od_b_name, od_b_tel, od_b_hp, " +
           "od_b_zip1, od_b_zip2, od_b_addr1, od_b_addr2, od_b_addr3, od_b_addr_jibeon, " +
           "od_memo, od_status, " +
           "od_cart_count, od_cart_price, od_cart_coupon, " +
           "od_send_cost, od_send_cost2, od_send_coupon, " +
           "od_receipt_price, od_cancel_price, od_receipt_point, od_coupon, od_misu, " +
           "od_settle_case, od_bank_account, od_delivery_company, od_invoice, " +
           "od_shop_memo, od_mod_history, " +
           "NULLIF(od_time, '0000-00-00 00:00:00') as od_time, " +
           "NULLIF(od_invoice_time, '0000-00-00 00:00:00') as od_invoice_time, " +
           "NULLIF(od_receipt_time, '0000-00-00 00:00:00') as od_receipt_time, " +
           "delivery_completed, admin_completed, " +
           "NULLIF(auto_confirm_at, '0000-00-00 00:00:00') as auto_confirm_at " +
           "FROM bomiora_shop_order " +
           "WHERE od_id = :odId AND mb_id = :mbId",
           nativeQuery = true)
    Optional<Object[]> findOrderDetailById(@Param("odId") Long odId, @Param("mbId") String mbId);
    
    /**
     * 자동 확정 대상 주문 조회 (빈 리스트 반환 - 제로 날짜 문제로 사용 안 함)
     */
    @Query(value = "SELECT * FROM bomiora_shop_order WHERE 1=0", nativeQuery = true)
    List<Order> findAutoConfirmTargets(@Param("now") LocalDateTime now);
    
    /**
     * 상품 ID 목록으로 이미지 URL 조회 (item_new 테이블의 it_img1)
     */
    @Query(value = "SELECT it_id, it_img1 FROM bomiora_shop_item_new WHERE it_id IN :itIds", nativeQuery = true)
    List<Object[]> findItemImagesByItIds(@Param("itIds") List<String> itIds);
}
