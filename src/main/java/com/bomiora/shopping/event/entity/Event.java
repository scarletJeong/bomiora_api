package com.bomiora.shopping.event.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_write_event")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wr_id")
    private Integer wrId;
    
    @Column(name = "wr_num")
    private Integer wrNum;
    
    @Column(name = "ca_name")
    private String caName;
    
    @Column(name = "wr_subject")
    private String wrSubject;
    
    @Column(name = "wr_content", columnDefinition = "TEXT")
    private String wrContent;
    
    @Column(name = "wr_link1", columnDefinition = "TEXT")
    private String wrLink1;
    
    @Column(name = "wr_datetime")
    private LocalDateTime wrDatetime;
    
    @Column(name = "wr_last")
    private String wrLast;
    
    @Column(name = "wr_hit")
    private Integer wrHit;
    
    @Column(name = "wr_1")
    private String wr1;
    
    @Column(name = "wr_2")
    private String wr2;
    
    public Event() {}
    
    public Integer getWrId() { return wrId; }
    public void setWrId(Integer wrId) { this.wrId = wrId; }
    
    public Integer getWrNum() { return wrNum; }
    public void setWrNum(Integer wrNum) { this.wrNum = wrNum; }
    
    public String getCaName() { return caName; }
    public void setCaName(String caName) { this.caName = caName; }
    
    public String getWrSubject() { return wrSubject; }
    public void setWrSubject(String wrSubject) { this.wrSubject = wrSubject; }
    
    public String getWrContent() { return wrContent; }
    public void setWrContent(String wrContent) { this.wrContent = wrContent; }
    
    public String getWrLink1() { return wrLink1; }
    public void setWrLink1(String wrLink1) { this.wrLink1 = wrLink1; }
    
    public LocalDateTime getWrDatetime() { return wrDatetime; }
    public void setWrDatetime(LocalDateTime wrDatetime) { this.wrDatetime = wrDatetime; }
    
    public String getWrLast() { return wrLast; }
    public void setWrLast(String wrLast) { this.wrLast = wrLast; }
    
    public Integer getWrHit() { return wrHit; }
    public void setWrHit(Integer wrHit) { this.wrHit = wrHit; }
    
    public String getWr1() { return wr1; }
    public void setWr1(String wr1) { this.wr1 = wr1; }
    
    public String getWr2() { return wr2; }
    public void setWr2(String wr2) { this.wr2 = wr2; }
    
    /**
     * 진행중인 이벤트인지 확인
     * 조건: ca_name이 "진행중인 이벤트"이고, wr_1 <= 현재날짜 <= wr_2
     */
    public boolean isActive() {
        try {
            if (!"진행중인 이벤트".equals(caName)) {
                return false;
            }
            
            if (wr1 == null || wr2 == null) {
                return false;
            }
            
            java.time.LocalDate now = java.time.LocalDate.now();
            java.time.LocalDate startDate = java.time.LocalDate.parse(wr1);
            java.time.LocalDate endDate = java.time.LocalDate.parse(wr2);
            
            return (now.isEqual(startDate) || now.isAfter(startDate)) && 
                   (now.isEqual(endDate) || now.isBefore(endDate));
        } catch (Exception e) {
            return false;
        }
    }
}

