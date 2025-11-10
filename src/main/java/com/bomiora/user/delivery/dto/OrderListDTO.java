package com.bomiora.user.delivery.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문 목록 DTO
 */
public class OrderListDTO {
    private Long odId;
    private String orderDate; // yyyy.MM.dd
    private String orderDateTime; // yyyy.MM.dd HH:mm
    private String displayStatus; // 사용자에게 표시되는 상태
    private String odStatus; // DB 원본 상태
    private Integer totalPrice; // 총 결제 금액
    private Integer odCartCount; // 상품 개수
    private List<OrderItemDTO> items = new ArrayList<>(); // 주문 상품 목록
    
    // 대표 상품 정보 (첫 번째 상품)
    private String firstProductName;
    private String firstProductOption;
    private Integer firstProductQty;
    private Integer firstProductPrice;
    
    public OrderListDTO() {}
    
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
    
    public String getOrderDateTime() {
        return orderDateTime;
    }
    
    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
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
    
    public Integer getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public Integer getOdCartCount() {
        return odCartCount;
    }
    
    public void setOdCartCount(Integer odCartCount) {
        this.odCartCount = odCartCount;
    }
    
    public List<OrderItemDTO> getItems() {
        return items;
    }
    
    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
        // 첫 번째 상품 정보 설정
        if (items != null && !items.isEmpty()) {
            OrderItemDTO firstItem = items.get(0);
            this.firstProductName = firstItem.getItName();
            this.firstProductOption = firstItem.getCtOption();
            this.firstProductQty = firstItem.getCtQty();
            this.firstProductPrice = firstItem.getTotalPrice();
        }
    }
    
    public String getFirstProductName() {
        return firstProductName;
    }
    
    public void setFirstProductName(String firstProductName) {
        this.firstProductName = firstProductName;
    }
    
    public String getFirstProductOption() {
        return firstProductOption;
    }
    
    public void setFirstProductOption(String firstProductOption) {
        this.firstProductOption = firstProductOption;
    }
    
    public Integer getFirstProductQty() {
        return firstProductQty;
    }
    
    public void setFirstProductQty(Integer firstProductQty) {
        this.firstProductQty = firstProductQty;
    }
    
    public Integer getFirstProductPrice() {
        return firstProductPrice;
    }
    
    public void setFirstProductPrice(Integer firstProductPrice) {
        this.firstProductPrice = firstProductPrice;
    }
}

