package com.bomiora.user.delivery.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_shop_order")
public class Order {
    
    @Id
    @Column(name = "od_id")
    private Long odId;
    
    @Column(name = "mb_id")
    private String mbId;
    
    @Column(name = "od_name")
    private String odName;
    
    @Column(name = "od_email")
    private String odEmail;
    
    @Column(name = "od_tel")
    private String odTel;
    
    @Column(name = "od_hp")
    private String odHp;
    
    // 배송지 정보
    @Column(name = "od_zip1", length = 3, columnDefinition = "char(3)")
    private String odZip1;
    
    @Column(name = "od_zip2", length = 3, columnDefinition = "char(3)")
    private String odZip2;
    
    @Column(name = "od_addr1")
    private String odAddr1;
    
    @Column(name = "od_addr2")
    private String odAddr2;
    
    @Column(name = "od_addr3")
    private String odAddr3;
    
    @Column(name = "od_addr_jibeon")
    private String odAddrJibeon;
    
    // 주문자 정보 (결제자)
    @Column(name = "od_b_name")
    private String odBName;
    
    @Column(name = "od_b_tel")
    private String odBTel;
    
    @Column(name = "od_b_hp")
    private String odBHp;
    
    @Column(name = "od_b_zip1", length = 3, columnDefinition = "char(3)")
    private String odBZip1;
    
    @Column(name = "od_b_zip2", length = 3, columnDefinition = "char(3)")
    private String odBZip2;
    
    @Column(name = "od_b_addr1")
    private String odBAddr1;
    
    @Column(name = "od_b_addr2")
    private String odBAddr2;
    
    @Column(name = "od_b_addr3")
    private String odBAddr3;
    
    @Column(name = "od_b_addr_jibeon")
    private String odBAddrJibeon;
    
    @Column(name = "od_memo", columnDefinition = "TEXT")
    private String odMemo;
    
    // 금액 정보
    @Column(name = "od_cart_count")
    private Integer odCartCount;
    
    @Column(name = "od_cart_price")
    private Integer odCartPrice;
    
    @Column(name = "od_cart_coupon")
    private Integer odCartCoupon;
    
    @Column(name = "od_send_cost")
    private Integer odSendCost;
    
    @Column(name = "od_send_cost2")
    private Integer odSendCost2;
    
    @Column(name = "od_send_cost3")
    private Integer odSendCost3;
    
    @Column(name = "od_send_coupon")
    private Integer odSendCoupon;
    
    @Column(name = "od_receipt_price")
    private Integer odReceiptPrice;
    
    @Column(name = "od_cancel_price")
    private Integer odCancelPrice;
    
    @Column(name = "od_receipt_point")
    private Integer odReceiptPoint;
    
    @Column(name = "od_refund_price")
    private Integer odRefundPrice;
    
    @Column(name = "od_coupon")
    private Integer odCoupon;
    
    @Column(name = "od_misu")
    private Integer odMisu;
    
    // 상태 정보
    @Column(name = "od_status")
    private String odStatus;
    
    @Column(name = "od_settle_case")
    private String odSettleCase;
    
    @Column(name = "od_other_pay_type")
    private String odOtherPayType;
    
    // 배송 정보
    @Column(name = "od_delivery_company")
    private String odDeliveryCompany;
    
    @Column(name = "od_invoice")
    private String odInvoice;
    
    @Column(name = "od_invoice_time", nullable = true)
    private LocalDateTime odInvoiceTime;
    
    // 자동 확정 관련 (0623 jjy 추가)
    @Column(name = "delivery_completed", columnDefinition = "tinyint(1)", nullable = true)
    private Integer deliveryCompleted;
    
    @Column(name = "delivery_completed_at", nullable = true)
    private LocalDateTime deliveryCompletedAt;
    
    @Column(name = "admin_completed", columnDefinition = "tinyint(1)", nullable = true)
    private Integer adminCompleted;
    
    @Column(name = "status_changed_at", nullable = true)
    private LocalDateTime statusChangedAt;
    
    @Column(name = "auto_confirm_at", nullable = true)
    private LocalDateTime autoConfirmAt;
    
    // 기타
    @Column(name = "od_time", nullable = true)
    private LocalDateTime odTime;
    
    @Column(name = "od_receipt_time", nullable = true)
    private LocalDateTime odReceiptTime;
    
    @Column(name = "od_ip")
    private String odIp;
    
    @Column(name = "od_shop_memo", columnDefinition = "TEXT")
    private String odShopMemo;
    
    @Column(name = "od_mod_history", columnDefinition = "TEXT")
    private String odModHistory;
    
    @Column(name = "od_pg")
    private String odPg;
    
    @Column(name = "od_tno")
    private String odTno;
    
    @Column(name = "od_app_no")
    private String odAppNo;

    // Getters and Setters
    
    public Long getOdId() {
        return odId;
    }

    public void setOdId(Long odId) {
        this.odId = odId;
    }

    public String getMbId() {
        return mbId;
    }

    public void setMbId(String mbId) {
        this.mbId = mbId;
    }

    public String getOdName() {
        return odName;
    }

    public void setOdName(String odName) {
        this.odName = odName;
    }

    public String getOdEmail() {
        return odEmail;
    }

    public void setOdEmail(String odEmail) {
        this.odEmail = odEmail;
    }

    public String getOdTel() {
        return odTel;
    }

    public void setOdTel(String odTel) {
        this.odTel = odTel;
    }

    public String getOdHp() {
        return odHp;
    }

    public void setOdHp(String odHp) {
        this.odHp = odHp;
    }

    public String getOdZip1() {
        return odZip1;
    }

    public void setOdZip1(String odZip1) {
        this.odZip1 = odZip1;
    }

    public String getOdZip2() {
        return odZip2;
    }

    public void setOdZip2(String odZip2) {
        this.odZip2 = odZip2;
    }

    public String getOdAddr1() {
        return odAddr1;
    }

    public void setOdAddr1(String odAddr1) {
        this.odAddr1 = odAddr1;
    }

    public String getOdAddr2() {
        return odAddr2;
    }

    public void setOdAddr2(String odAddr2) {
        this.odAddr2 = odAddr2;
    }

    public String getOdAddr3() {
        return odAddr3;
    }

    public void setOdAddr3(String odAddr3) {
        this.odAddr3 = odAddr3;
    }

    public String getOdAddrJibeon() {
        return odAddrJibeon;
    }

    public void setOdAddrJibeon(String odAddrJibeon) {
        this.odAddrJibeon = odAddrJibeon;
    }

    public String getOdBName() {
        return odBName;
    }

    public void setOdBName(String odBName) {
        this.odBName = odBName;
    }

    public String getOdBTel() {
        return odBTel;
    }

    public void setOdBTel(String odBTel) {
        this.odBTel = odBTel;
    }

    public String getOdBHp() {
        return odBHp;
    }

    public void setOdBHp(String odBHp) {
        this.odBHp = odBHp;
    }

    public String getOdBZip1() {
        return odBZip1;
    }

    public void setOdBZip1(String odBZip1) {
        this.odBZip1 = odBZip1;
    }

    public String getOdBZip2() {
        return odBZip2;
    }

    public void setOdBZip2(String odBZip2) {
        this.odBZip2 = odBZip2;
    }

    public String getOdBAddr1() {
        return odBAddr1;
    }

    public void setOdBAddr1(String odBAddr1) {
        this.odBAddr1 = odBAddr1;
    }

    public String getOdBAddr2() {
        return odBAddr2;
    }

    public void setOdBAddr2(String odBAddr2) {
        this.odBAddr2 = odBAddr2;
    }

    public String getOdBAddr3() {
        return odBAddr3;
    }

    public void setOdBAddr3(String odBAddr3) {
        this.odBAddr3 = odBAddr3;
    }

    public String getOdBAddrJibeon() {
        return odBAddrJibeon;
    }

    public void setOdBAddrJibeon(String odBAddrJibeon) {
        this.odBAddrJibeon = odBAddrJibeon;
    }

    public String getOdMemo() {
        return odMemo;
    }

    public void setOdMemo(String odMemo) {
        this.odMemo = odMemo;
    }

    public Integer getOdCartCount() {
        return odCartCount;
    }

    public void setOdCartCount(Integer odCartCount) {
        this.odCartCount = odCartCount;
    }

    public Integer getOdCartPrice() {
        return odCartPrice;
    }

    public void setOdCartPrice(Integer odCartPrice) {
        this.odCartPrice = odCartPrice;
    }

    public Integer getOdCartCoupon() {
        return odCartCoupon;
    }

    public void setOdCartCoupon(Integer odCartCoupon) {
        this.odCartCoupon = odCartCoupon;
    }

    public Integer getOdSendCost() {
        return odSendCost;
    }

    public void setOdSendCost(Integer odSendCost) {
        this.odSendCost = odSendCost;
    }

    public Integer getOdSendCost2() {
        return odSendCost2;
    }

    public void setOdSendCost2(Integer odSendCost2) {
        this.odSendCost2 = odSendCost2;
    }

    public Integer getOdSendCost3() {
        return odSendCost3;
    }

    public void setOdSendCost3(Integer odSendCost3) {
        this.odSendCost3 = odSendCost3;
    }

    public Integer getOdSendCoupon() {
        return odSendCoupon;
    }

    public void setOdSendCoupon(Integer odSendCoupon) {
        this.odSendCoupon = odSendCoupon;
    }

    public Integer getOdReceiptPrice() {
        return odReceiptPrice;
    }

    public void setOdReceiptPrice(Integer odReceiptPrice) {
        this.odReceiptPrice = odReceiptPrice;
    }

    public Integer getOdCancelPrice() {
        return odCancelPrice;
    }

    public void setOdCancelPrice(Integer odCancelPrice) {
        this.odCancelPrice = odCancelPrice;
    }

    public Integer getOdReceiptPoint() {
        return odReceiptPoint;
    }

    public void setOdReceiptPoint(Integer odReceiptPoint) {
        this.odReceiptPoint = odReceiptPoint;
    }

    public Integer getOdRefundPrice() {
        return odRefundPrice;
    }

    public void setOdRefundPrice(Integer odRefundPrice) {
        this.odRefundPrice = odRefundPrice;
    }

    public Integer getOdCoupon() {
        return odCoupon;
    }

    public void setOdCoupon(Integer odCoupon) {
        this.odCoupon = odCoupon;
    }

    public Integer getOdMisu() {
        return odMisu;
    }

    public void setOdMisu(Integer odMisu) {
        this.odMisu = odMisu;
    }

    public String getOdStatus() {
        return odStatus;
    }

    public void setOdStatus(String odStatus) {
        this.odStatus = odStatus;
    }

    public String getOdSettleCase() {
        return odSettleCase;
    }

    public void setOdSettleCase(String odSettleCase) {
        this.odSettleCase = odSettleCase;
    }

    public String getOdOtherPayType() {
        return odOtherPayType;
    }

    public void setOdOtherPayType(String odOtherPayType) {
        this.odOtherPayType = odOtherPayType;
    }

    public String getOdDeliveryCompany() {
        return odDeliveryCompany;
    }

    public void setOdDeliveryCompany(String odDeliveryCompany) {
        this.odDeliveryCompany = odDeliveryCompany;
    }

    public String getOdInvoice() {
        return odInvoice;
    }

    public void setOdInvoice(String odInvoice) {
        this.odInvoice = odInvoice;
    }

    public LocalDateTime getOdInvoiceTime() {
        return odInvoiceTime;
    }

    public void setOdInvoiceTime(LocalDateTime odInvoiceTime) {
        this.odInvoiceTime = odInvoiceTime;
    }

    public Integer getDeliveryCompleted() {
        return deliveryCompleted;
    }

    public void setDeliveryCompleted(Integer deliveryCompleted) {
        this.deliveryCompleted = deliveryCompleted;
    }

    public LocalDateTime getDeliveryCompletedAt() {
        return deliveryCompletedAt;
    }

    public void setDeliveryCompletedAt(LocalDateTime deliveryCompletedAt) {
        this.deliveryCompletedAt = deliveryCompletedAt;
    }

    public Integer getAdminCompleted() {
        return adminCompleted;
    }

    public void setAdminCompleted(Integer adminCompleted) {
        this.adminCompleted = adminCompleted;
    }

    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }

    public LocalDateTime getAutoConfirmAt() {
        return autoConfirmAt;
    }

    public void setAutoConfirmAt(LocalDateTime autoConfirmAt) {
        this.autoConfirmAt = autoConfirmAt;
    }

    public LocalDateTime getOdTime() {
        return odTime;
    }

    public void setOdTime(LocalDateTime odTime) {
        this.odTime = odTime;
    }

    public LocalDateTime getOdReceiptTime() {
        return odReceiptTime;
    }

    public void setOdReceiptTime(LocalDateTime odReceiptTime) {
        this.odReceiptTime = odReceiptTime;
    }

    public String getOdIp() {
        return odIp;
    }

    public void setOdIp(String odIp) {
        this.odIp = odIp;
    }

    public String getOdShopMemo() {
        return odShopMemo;
    }

    public void setOdShopMemo(String odShopMemo) {
        this.odShopMemo = odShopMemo;
    }

    public String getOdModHistory() {
        return odModHistory;
    }

    public void setOdModHistory(String odModHistory) {
        this.odModHistory = odModHistory;
    }

    public String getOdPg() {
        return odPg;
    }

    public void setOdPg(String odPg) {
        this.odPg = odPg;
    }

    public String getOdTno() {
        return odTno;
    }

    public void setOdTno(String odTno) {
        this.odTno = odTno;
    }

    public String getOdAppNo() {
        return odAppNo;
    }

    public void setOdAppNo(String odAppNo) {
        this.odAppNo = odAppNo;
    }
}

