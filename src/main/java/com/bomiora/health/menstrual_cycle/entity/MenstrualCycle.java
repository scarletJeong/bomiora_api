package com.bomiora.health.menstrual_cycle.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bm_menstrual_cycle")
public class MenstrualCycle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "mb_id", nullable = false, length = 50)
    private String mbId;
    
    @Column(name = "last_period_start", nullable = false)
    private LocalDate lastPeriodStart; // 마지막 생리 시작일
    
    @Column(name = "cycle_length", nullable = false)
    private Integer cycleLength; // 생리주기 길이 (일)
    
    @Column(name = "period_length", nullable = false)
    private Integer periodLength; // 생리 기간 길이 (일)
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 입력 일시
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 일시
    
    // 기본 생성자
    public MenstrualCycle() {}
    
    // 전체 생성자
    public MenstrualCycle(Long id, String mbId, LocalDate lastPeriodStart, 
                         Integer cycleLength, Integer periodLength,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.mbId = mbId;
        this.lastPeriodStart = lastPeriodStart;
        this.cycleLength = cycleLength;
        this.periodLength = periodLength;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    
    public LocalDate getLastPeriodStart() { return lastPeriodStart; }
    public void setLastPeriodStart(LocalDate lastPeriodStart) { this.lastPeriodStart = lastPeriodStart; }
    
    public Integer getCycleLength() { return cycleLength; }
    public void setCycleLength(Integer cycleLength) { this.cycleLength = cycleLength; }
    
    public Integer getPeriodLength() { return periodLength; }
    public void setPeriodLength(Integer periodLength) { this.periodLength = periodLength; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
