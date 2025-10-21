package com.bomiora.health.blood_pressure.dto;

import com.bomiora.health.blood_pressure.entity.BloodPressure;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class BloodPressureDTO {
    
    private Long id;
    
    @JsonProperty("mb_id")
    private String mbId;
    
    private Integer systolic;
    private Integer diastolic;
    private Integer pulse;
    private String status;
    
    @JsonProperty("measured_at")
    private LocalDateTime measuredAt;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    // 기본 생성자
    public BloodPressureDTO() {}
    
    // 전체 생성자
    public BloodPressureDTO(Long id, String mbId, Integer systolic, Integer diastolic,
                           Integer pulse, String status, LocalDateTime measuredAt,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.mbId = mbId;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.status = status;
        this.measuredAt = measuredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    
    public Integer getSystolic() { return systolic; }
    public void setSystolic(Integer systolic) { this.systolic = systolic; }
    
    public Integer getDiastolic() { return diastolic; }
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }
    
    public Integer getPulse() { return pulse; }
    public void setPulse(Integer pulse) { this.pulse = pulse; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getMeasuredAt() { return measuredAt; }
    public void setMeasuredAt(LocalDateTime measuredAt) { this.measuredAt = measuredAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    /**
     * Entity를 DTO로 변환
     */
    public static BloodPressureDTO fromEntity(BloodPressure entity) {
        return new BloodPressureDTO(
            entity.getId(),
            entity.getMbId(),
            entity.getSystolic(),
            entity.getDiastolic(),
            entity.getPulse(),
            entity.getStatus(),
            entity.getMeasuredAt(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    /**
     * DTO를 Entity로 변환
     */
    public BloodPressure toEntity() {
        BloodPressure entity = new BloodPressure();
        entity.setId(this.id);
        entity.setMbId(this.mbId);
        entity.setSystolic(this.systolic);
        entity.setDiastolic(this.diastolic);
        entity.setPulse(this.pulse);
        entity.setStatus(this.status);
        entity.setMeasuredAt(this.measuredAt);
        return entity;
    }
}

