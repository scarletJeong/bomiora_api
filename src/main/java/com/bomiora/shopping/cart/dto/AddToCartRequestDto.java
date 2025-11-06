package com.bomiora.shopping.cart.dto;

public class AddToCartRequestDto {
    private String mbId;
    private String itId;
    private Integer quantity;
    private Integer price;
    private String optionId;
    private String optionText;
    private Integer optionPrice;
    
    public AddToCartRequestDto() {}
    
    public String getMbId() {
        return mbId;
    }
    
    public void setMbId(String mbId) {
        this.mbId = mbId;
    }
    
    public String getItId() {
        return itId;
    }
    
    public void setItId(String itId) {
        this.itId = itId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Integer getPrice() {
        return price;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public String getOptionId() {
        return optionId;
    }
    
    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
    
    public String getOptionText() {
        return optionText;
    }
    
    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
    
    public Integer getOptionPrice() {
        return optionPrice;
    }
    
    public void setOptionPrice(Integer optionPrice) {
        this.optionPrice = optionPrice;
    }
}

