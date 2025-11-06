package com.bomiora.shopping.cart.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_shop_cart")
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ct_id")
    private Integer ctId;
    
    @Column(name = "od_id", nullable = false)
    private Long odId;
    
    @Column(name = "mb_id", nullable = false, length = 255)
    private String mbId;
    
    @Column(name = "it_id", nullable = false, length = 20)
    private String itId;
    
    @Column(name = "it_name", nullable = false, length = 255)
    private String itName;
    
    @Column(name = "it_subject", length = 255)
    private String itSubject;
    
    @Column(name = "it_sc_type", nullable = false)
    private Byte itScType;
    
    @Column(name = "it_sc_method", nullable = false)
    private Byte itScMethod;
    
    @Column(name = "it_sc_price", nullable = false)
    private Integer itScPrice;
    
    @Column(name = "it_sc_minimum", nullable = false)
    private Integer itScMinimum;
    
    @Column(name = "it_sc_qty", nullable = false)
    private Integer itScQty;
    
    @Column(name = "ct_status", nullable = false, length = 255)
    private String ctStatus;
    
    @Column(name = "ct_history", columnDefinition = "TEXT", nullable = false)
    private String ctHistory;
    
    @Column(name = "ct_price", nullable = false)
    private Integer ctPrice;
    
    @Column(name = "ct_point", nullable = false)
    private Integer ctPoint;
    
    @Column(name = "cp_price", nullable = false)
    private Integer cpPrice;
    
    @Column(name = "ct_point_use", nullable = false)
    private Byte ctPointUse;
    
    @Column(name = "ct_stock_use", nullable = false)
    private Byte ctStockUse;
    
    @Column(name = "ct_option", length = 255)
    private String ctOption;
    
    @Column(name = "ct_qty", nullable = false)
    private Integer ctQty;
    
    @Column(name = "ct_notax", nullable = false)
    private Byte ctNotax;
    
    @Column(name = "io_id", length = 255)
    private String ioId;
    
    @Column(name = "io_type", nullable = false)
    private Byte ioType;
    
    @Column(name = "io_price", nullable = false)
    private Integer ioPrice;
    
    @Column(name = "ct_time", nullable = false)
    private LocalDateTime ctTime;
    
    @Column(name = "ct_ip", length = 25)
    private String ctIp;
    
    @Column(name = "ct_send_cost", nullable = false)
    private Byte ctSendCost;
    
    @Column(name = "ct_direct", nullable = false)
    private Byte ctDirect;
    
    @Column(name = "ct_select", nullable = false)
    private Byte ctSelect;
    
    @Column(name = "inf_code", length = 255)
    private String infCode;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ct_output", nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'Y'")
    private OutputType ctOutput;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ct_kind", nullable = false, columnDefinition = "ENUM('general','prescription') DEFAULT 'general'")
    private CartKind ctKind;
    
    @Column(name = "ct_mb_inf", length = 20)
    private String ctMbInf;
    
    @Column(name = "ct_inf_price", nullable = false)
    private Integer ctInfPrice;
    
    @Column(name = "ct_select_time")
    private LocalDateTime ctSelectTime;
    
    @Column(name = "ct_settlement_status", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String ctSettlementStatus;
    
    @Column(name = "ct_settlement_status_time")
    private LocalDateTime ctSettlementStatusTime;
    
    @Column(name = "ct_settlement_update_time")
    private LocalDateTime ctSettlementUpdateTime;
    
    public enum OutputType {
        Y, N
    }
    
    public enum CartKind {
        general, prescription
    }
    
    // 기본 생성자
    public Cart() {}
    
    // Getters and Setters
    public Integer getCtId() { return ctId; }
    public void setCtId(Integer ctId) { this.ctId = ctId; }
    
    public Long getOdId() { return odId; }
    public void setOdId(Long odId) { this.odId = odId; }
    
    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    
    public String getItId() { return itId; }
    public void setItId(String itId) { this.itId = itId; }
    
    public String getItName() { return itName; }
    public void setItName(String itName) { this.itName = itName; }
    
    public String getItSubject() { return itSubject; }
    public void setItSubject(String itSubject) { this.itSubject = itSubject; }
    
    public Byte getItScType() { return itScType; }
    public void setItScType(Byte itScType) { this.itScType = itScType; }
    
    public Byte getItScMethod() { return itScMethod; }
    public void setItScMethod(Byte itScMethod) { this.itScMethod = itScMethod; }
    
    public Integer getItScPrice() { return itScPrice; }
    public void setItScPrice(Integer itScPrice) { this.itScPrice = itScPrice; }
    
    public Integer getItScMinimum() { return itScMinimum; }
    public void setItScMinimum(Integer itScMinimum) { this.itScMinimum = itScMinimum; }
    
    public Integer getItScQty() { return itScQty; }
    public void setItScQty(Integer itScQty) { this.itScQty = itScQty; }
    
    public String getCtStatus() { return ctStatus; }
    public void setCtStatus(String ctStatus) { this.ctStatus = ctStatus; }
    
    public String getCtHistory() { return ctHistory; }
    public void setCtHistory(String ctHistory) { this.ctHistory = ctHistory; }
    
    public Integer getCtPrice() { return ctPrice; }
    public void setCtPrice(Integer ctPrice) { this.ctPrice = ctPrice; }
    
    public Integer getCtPoint() { return ctPoint; }
    public void setCtPoint(Integer ctPoint) { this.ctPoint = ctPoint; }
    
    public Integer getCpPrice() { return cpPrice; }
    public void setCpPrice(Integer cpPrice) { this.cpPrice = cpPrice; }
    
    public Byte getCtPointUse() { return ctPointUse; }
    public void setCtPointUse(Byte ctPointUse) { this.ctPointUse = ctPointUse; }
    
    public Byte getCtStockUse() { return ctStockUse; }
    public void setCtStockUse(Byte ctStockUse) { this.ctStockUse = ctStockUse; }
    
    public String getCtOption() { return ctOption; }
    public void setCtOption(String ctOption) { this.ctOption = ctOption; }
    
    public Integer getCtQty() { return ctQty; }
    public void setCtQty(Integer ctQty) { this.ctQty = ctQty; }
    
    public Byte getCtNotax() { return ctNotax; }
    public void setCtNotax(Byte ctNotax) { this.ctNotax = ctNotax; }
    
    public String getIoId() { return ioId; }
    public void setIoId(String ioId) { this.ioId = ioId; }
    
    public Byte getIoType() { return ioType; }
    public void setIoType(Byte ioType) { this.ioType = ioType; }
    
    public Integer getIoPrice() { return ioPrice; }
    public void setIoPrice(Integer ioPrice) { this.ioPrice = ioPrice; }
    
    public LocalDateTime getCtTime() { return ctTime; }
    public void setCtTime(LocalDateTime ctTime) { this.ctTime = ctTime; }
    
    public String getCtIp() { return ctIp; }
    public void setCtIp(String ctIp) { this.ctIp = ctIp; }
    
    public Byte getCtSendCost() { return ctSendCost; }
    public void setCtSendCost(Byte ctSendCost) { this.ctSendCost = ctSendCost; }
    
    public Byte getCtDirect() { return ctDirect; }
    public void setCtDirect(Byte ctDirect) { this.ctDirect = ctDirect; }
    
    public Byte getCtSelect() { return ctSelect; }
    public void setCtSelect(Byte ctSelect) { this.ctSelect = ctSelect; }
    
    public String getInfCode() { return infCode; }
    public void setInfCode(String infCode) { this.infCode = infCode; }
    
    public OutputType getCtOutput() { return ctOutput; }
    public void setCtOutput(OutputType ctOutput) { this.ctOutput = ctOutput; }
    
    public CartKind getCtKind() { return ctKind; }
    public void setCtKind(CartKind ctKind) { this.ctKind = ctKind; }
    
    public String getCtMbInf() { return ctMbInf; }
    public void setCtMbInf(String ctMbInf) { this.ctMbInf = ctMbInf; }
    
    public Integer getCtInfPrice() { return ctInfPrice; }
    public void setCtInfPrice(Integer ctInfPrice) { this.ctInfPrice = ctInfPrice; }
    
    public LocalDateTime getCtSelectTime() { return ctSelectTime; }
    public void setCtSelectTime(LocalDateTime ctSelectTime) { this.ctSelectTime = ctSelectTime; }
    
    public String getCtSettlementStatus() { return ctSettlementStatus; }
    public void setCtSettlementStatus(String ctSettlementStatus) { this.ctSettlementStatus = ctSettlementStatus; }
    
    public LocalDateTime getCtSettlementStatusTime() { return ctSettlementStatusTime; }
    public void setCtSettlementStatusTime(LocalDateTime ctSettlementStatusTime) { this.ctSettlementStatusTime = ctSettlementStatusTime; }
    
    public LocalDateTime getCtSettlementUpdateTime() { return ctSettlementUpdateTime; }
    public void setCtSettlementUpdateTime(LocalDateTime ctSettlementUpdateTime) { this.ctSettlementUpdateTime = ctSettlementUpdateTime; }
}

