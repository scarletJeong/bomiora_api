package com.bomiora.user.delivery.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문 상세 DTO
 */
public class OrderDetailDTO {
    // 주문 기본 정보
    private Long odId;
    private String orderDate; // yyyy.MM.dd HH:mm
    private String displayStatus; // 사용자에게 표시되는 상태
    private String odStatus; // DB 원본 상태
    
    // 배송 정보
    private String recipientName; // 받는 사람
    private String recipientPhone; // 연락처
    private String recipientAddress; // 주소
    private String recipientAddressDetail; // 상세주소
    private String deliveryMessage; // 배송 요청사항
    private String deliveryCompany; // 택배사
    private String trackingNumber; // 운송장 번호
    
    // 주문 상품 목록
    private List<OrderItemDTO> products = new ArrayList<>();
    
    // 결제 정보
    private Integer productPrice; // 상품 금액
    private Integer deliveryFee; // 배송비
    private Integer discountAmount; // 할인 금액
    private Integer totalPrice; // 총 결제 금액
    private String paymentMethod; // 결제 방법
    private String paymentMethodDetail; // 결제 방법 상세 (카카오페이, 네이버페이 등)
    
    // 주문자 정보
    private String ordererName; // 주문자 이름
    private String ordererPhone; // 주문자 연락처
    private String ordererEmail; // 주문자 이메일
    
    // 취소 정보
    private String cancelReason; // 취소 사유
    private String cancelType; // 취소 유형 (고객직접, 시스템자동, 관리자)
    
    public OrderDetailDTO() {}
    
    // Getters and Setters
    public Long getOdId() {
        return odId;
    }
    
    public void setOdId(Long odId) {
        this.odId = odId;
    }
    
    public String getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getDisplayStatus() {
        return displayStatus;
    }
    
    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }
    
    public String getOdStatus() {
        return odStatus;
    }
    
    public void setOdStatus(String odStatus) {
        this.odStatus = odStatus;
    }
    
    public String getRecipientName() {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    
    public String getRecipientPhone() {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }
    
    public String getRecipientAddress() {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }
    
    public String getRecipientAddressDetail() {
        return recipientAddressDetail;
    }
    
    public void setRecipientAddressDetail(String recipientAddressDetail) {
        this.recipientAddressDetail = recipientAddressDetail;
    }
    
    public String getDeliveryMessage() {
        return deliveryMessage;
    }
    
    public void setDeliveryMessage(String deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }
    
    public String getDeliveryCompany() {
        return deliveryCompany;
    }
    
    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public List<OrderItemDTO> getProducts() {
        return products;
    }
    
    public void setProducts(List<OrderItemDTO> products) {
        this.products = products;
    }
    
    public Integer getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
    
    public Integer getDeliveryFee() {
        return deliveryFee;
    }
    
    public void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
    
    public Integer getDiscountAmount() {
        return discountAmount;
    }
    
    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    public Integer getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getOrdererName() {
        return ordererName;
    }
    
    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }
    
    public String getOrdererPhone() {
        return ordererPhone;
    }
    
    public void setOrdererPhone(String ordererPhone) {
        this.ordererPhone = ordererPhone;
    }
    
    public String getOrdererEmail() {
        return ordererEmail;
    }
    
    public void setOrdererEmail(String ordererEmail) {
        this.ordererEmail = ordererEmail;
    }
    
    public String getPaymentMethodDetail() {
        return paymentMethodDetail;
    }
    
    public void setPaymentMethodDetail(String paymentMethodDetail) {
        this.paymentMethodDetail = paymentMethodDetail;
    }
    
    public String getCancelReason() {
        return cancelReason;
    }
    
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
    
    public String getCancelType() {
        return cancelType;
    }
    
    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }
}

