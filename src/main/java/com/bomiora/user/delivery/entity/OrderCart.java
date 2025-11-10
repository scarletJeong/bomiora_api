package com.bomiora.user.delivery.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bomiora_shop_cart")
public class OrderCart {
    
    @Id
    @Column(name = "ct_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ctId;
    
    @Column(name = "od_id")
    private Long odId;
    
    @Column(name = "mb_id")
    private String mbId;
    
    @Column(name = "it_id")
    private String itId;
    
    @Column(name = "it_name")
    private String itName;
    
    @Column(name = "it_subject")
    private String itSubject;
    
    @Column(name = "ct_option")
    private String ctOption;
    
    @Column(name = "ct_qty")
    private Integer ctQty;
    
    @Column(name = "ct_price")
    private Integer ctPrice;
    
    @Column(name = "io_price")
    private Integer ioPrice;
    
    @Column(name = "ct_point")
    private Integer ctPoint;
    
    @Column(name = "cp_price")
    private Integer cpPrice;
    
    @Column(name = "ct_status")
    private String ctStatus;
    
    @Column(name = "ct_send_cost")
    private Integer ctSendCost;
    
    @Column(name = "it_sc_type")
    private String itScType;
    
    @Column(name = "io_type")
    private Integer ioType;

    // Getters and Setters
    
    public Long getCtId() {
        return ctId;
    }

    public void setCtId(Long ctId) {
        this.ctId = ctId;
    }

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

    public Integer getCtPoint() {
        return ctPoint;
    }

    public void setCtPoint(Integer ctPoint) {
        this.ctPoint = ctPoint;
    }

    public Integer getCpPrice() {
        return cpPrice;
    }

    public void setCpPrice(Integer cpPrice) {
        this.cpPrice = cpPrice;
    }

    public String getCtStatus() {
        return ctStatus;
    }

    public void setCtStatus(String ctStatus) {
        this.ctStatus = ctStatus;
    }

    public Integer getCtSendCost() {
        return ctSendCost;
    }

    public void setCtSendCost(Integer ctSendCost) {
        this.ctSendCost = ctSendCost;
    }

    public String getItScType() {
        return itScType;
    }

    public void setItScType(String itScType) {
        this.itScType = itScType;
    }

    public Integer getIoType() {
        return ioType;
    }

    public void setIoType(Integer ioType) {
        this.ioType = ioType;
    }
}

