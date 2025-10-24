package com.bomiora.health.menstrual_cycle.dto;

import com.bomiora.health.menstrual_cycle.entity.MenstrualCycle;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MenstrualCycleDTO {
    
    private Long id;
    
    @JsonProperty("mb_id")
    private String mbId;
    
    @JsonProperty("last_period_start")
    private LocalDate lastPeriodStart;
    
    @JsonProperty("cycle_length")
    private Integer cycleLength;
    
    @JsonProperty("period_length")
    private Integer periodLength;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    // 기본 생성자
    public MenstrualCycleDTO() {}
    
    // 전체 생성자
    public MenstrualCycleDTO(Long id, String mbId, LocalDate lastPeriodStart,
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
    
    /**
     * Entity를 DTO로 변환
     */
    public static MenstrualCycleDTO fromEntity(MenstrualCycle entity) {
        return new MenstrualCycleDTO(
            entity.getId(),
            entity.getMbId(),
            entity.getLastPeriodStart(),
            entity.getCycleLength(),
            entity.getPeriodLength(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    /**
     * DTO를 Entity로 변환
     */
    public MenstrualCycle toEntity() {
        MenstrualCycle entity = new MenstrualCycle();
        entity.setId(this.id);
        entity.setMbId(this.mbId);
        entity.setLastPeriodStart(this.lastPeriodStart);
        entity.setCycleLength(this.cycleLength);
        entity.setPeriodLength(this.periodLength);
        return entity;
    }
}
