package com.bomiora.shopping.wish.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_shop_wish")
public class Wish {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wi_id")
    private Integer wiId;
    
    @Column(name = "mb_id", nullable = false, length = 255)
    private String mbId;
    
    @Column(name = "it_id", nullable = false, length = 20)
    private String itId;
    
    @Column(name = "inf_code", length = 255)
    private String infCode;
    
    @Column(name = "wi_time", nullable = false)
    private LocalDateTime wiTime;
    
    @Column(name = "wi_ip", length = 25)
    private String wiIp;
    
    // 기본 생성자
    public Wish() {}
    
    // Getters and Setters
    public Integer getWiId() {
        return wiId;
    }
    
    public void setWiId(Integer wiId) {
        this.wiId = wiId;
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
    
    public String getInfCode() {
        return infCode;
    }
    
    public void setInfCode(String infCode) {
        this.infCode = infCode;
    }
    
    public LocalDateTime getWiTime() {
        return wiTime;
    }
    
    public void setWiTime(LocalDateTime wiTime) {
        this.wiTime = wiTime;
    }
    
    public String getWiIp() {
        return wiIp;
    }
    
    public void setWiIp(String wiIp) {
        this.wiIp = wiIp;
    }
}

