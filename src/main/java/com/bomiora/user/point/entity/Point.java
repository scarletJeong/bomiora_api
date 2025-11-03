package com.bomiora.user.point.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "po_id")
    private Integer id;
    
    @Column(name = "mb_id", length = 30)
    private String userId;
    
    @Column(name = "po_datetime")
    private LocalDateTime datetime;
    
    @Column(name = "po_content", length = 255)
    private String content;
    
    @Column(name = "po_point")
    private Integer point;
    
    @Column(name = "po_use_point")
    private Integer usePoint;
    
    @Column(name = "po_expired")
    private Integer expired;
    
    @Column(name = "po_expire_date")
    private LocalDateTime expireDate;
    
    @Column(name = "po_use_date")
    private LocalDateTime useDate;
    
    @Column(name = "po_mb_point")
    private Integer mbPoint; // 최종 포인트
    
    @Column(name = "po_rel_table", length = 20)
    private String relTable;
    
    @Column(name = "po_rel_id", length = 20)
    private String relId;
    
    @Column(name = "po_rel_action", length = 100)
    private String relAction;
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getDatetime() {
        return datetime;
    }
    
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Integer getPoint() {
        return point;
    }
    
    public void setPoint(Integer point) {
        this.point = point;
    }
    
    public Integer getUsePoint() {
        return usePoint;
    }
    
    public void setUsePoint(Integer usePoint) {
        this.usePoint = usePoint;
    }
    
    public Integer getExpired() {
        return expired;
    }
    
    public void setExpired(Integer expired) {
        this.expired = expired;
    }
    
    public LocalDateTime getExpireDate() {
        return expireDate;
    }
    
    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
    
    public LocalDateTime getUseDate() {
        return useDate;
    }
    
    public void setUseDate(LocalDateTime useDate) {
        this.useDate = useDate;
    }
    
    public Integer getMbPoint() {
        return mbPoint;
    }
    
    public void setMbPoint(Integer mbPoint) {
        this.mbPoint = mbPoint;
    }
    
    public String getRelTable() {
        return relTable;
    }
    
    public void setRelTable(String relTable) {
        this.relTable = relTable;
    }
    
    public String getRelId() {
        return relId;
    }
    
    public void setRelId(String relId) {
        this.relId = relId;
    }
    
    public String getRelAction() {
        return relAction;
    }
    
    public void setRelAction(String relAction) {
        this.relAction = relAction;
    }
}
