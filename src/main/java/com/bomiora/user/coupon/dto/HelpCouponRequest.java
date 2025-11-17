package com.bomiora.user.coupon.dto;

public class HelpCouponRequest {
    private String mbId;      // 회원 ID
    private String itId;      // 제품 ID
    private Integer isId;     // 리뷰 ID

    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    
    public String getItId() { return itId; }
    public void setItId(String itId) { this.itId = itId; }
    
    public Integer getIsId() { return isId; }
    public void setIsId(Integer isId) { this.isId = isId; }
}

