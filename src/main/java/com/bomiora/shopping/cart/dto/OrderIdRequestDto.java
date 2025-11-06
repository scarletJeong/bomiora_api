package com.bomiora.shopping.cart.dto;

public class OrderIdRequestDto {
    private String mbId;
    private String itId;
    
    public OrderIdRequestDto() {}
    
    public OrderIdRequestDto(String mbId, String itId) {
        this.mbId = mbId;
        this.itId = itId;
    }
    
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
}

