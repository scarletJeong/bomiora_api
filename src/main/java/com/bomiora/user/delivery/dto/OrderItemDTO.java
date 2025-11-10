package com.bomiora.user.delivery.dto;

/**
 * 주문 상품 DTO
 */
public class OrderItemDTO {
    private Long ctId;
    private String itId;
    private String itName;
    private String itSubject;
    private String ctOption;
    private Integer ctQty;
    private Integer ctPrice;
    private Integer ioPrice;
    private Integer totalPrice; // (ctPrice + ioPrice) * ctQty
    private String ctStatus;
    private String imageUrl; // 상품 이미지 URL (item_new.it_img1)
    
    public OrderItemDTO() {}
    
    public OrderItemDTO(Long ctId, String itId, String itName, String itSubject, 
                       String ctOption, Integer ctQty, Integer ctPrice, Integer ioPrice, 
                       String ctStatus) {
        this.ctId = ctId;
        this.itId = itId;
        this.itName = itName;
        this.itSubject = itSubject;
        this.ctOption = ctOption;
        this.ctQty = ctQty;
        this.ctPrice = ctPrice;
        this.ioPrice = ioPrice;
        this.totalPrice = (ctPrice + ioPrice) * ctQty;
        this.ctStatus = ctStatus;
        this.imageUrl = ""; // 기본값은 빈 문자열
    }
    
    // Getters and Setters
    public Long getCtId() {
        return ctId;
    }
    
    public void setCtId(Long ctId) {
        this.ctId = ctId;
    }
    
    public String getItId() {
        return itId;
    }
    
    public void setItId(String itId) {
        this.itId = itId;
    }
    
    public String getItName() {
        return itName;
    }
    
    public void setItName(String itName) {
        this.itName = itName;
    }
    
    public String getItSubject() {
        return itSubject;
    }
    
    public void setItSubject(String itSubject) {
        this.itSubject = itSubject;
    }
    
    public String getCtOption() {
        return ctOption;
    }
    
    public void setCtOption(String ctOption) {
        this.ctOption = ctOption;
    }
    
    public Integer getCtQty() {
        return ctQty;
    }
    
    public void setCtQty(Integer ctQty) {
        this.ctQty = ctQty;
    }
    
    public Integer getCtPrice() {
        return ctPrice;
    }
    
    public void setCtPrice(Integer ctPrice) {
        this.ctPrice = ctPrice;
    }
    
    public Integer getIoPrice() {
        return ioPrice;
    }
    
    public void setIoPrice(Integer ioPrice) {
        this.ioPrice = ioPrice;
    }
    
    public Integer getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getCtStatus() {
        return ctStatus;
    }
    
    public void setCtStatus(String ctStatus) {
        this.ctStatus = ctStatus;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

