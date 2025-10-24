package com.bomiora.health.blood_sugar.dto;

import com.bomiora.health.blood_sugar.entity.BloodSugar;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class BloodSugarDTO {
    
    private Long id;
    
    @JsonProperty("mb_id")
    private String mbId;
    
    @JsonProperty("blood_sugar")
    private Integer bloodSugar;
    
    @JsonProperty("measurement_type")
    private String measurementType;
    
    private String status;
    
    @JsonProperty("measured_at")
    private LocalDateTime measuredAt;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    // 기본 생성자
    public BloodSugarDTO() {}
    
    // 전체 생성자
    public BloodSugarDTO(Long id, String mbId, Integer bloodSugar, String measurementType,
                        String status, LocalDateTime measuredAt,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.mbId = mbId;
        this.bloodSugar = bloodSugar;
        this.measurementType = measurementType;
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
    
    public Integer getBloodSugar() { return bloodSugar; }
    public void setBloodSugar(Integer bloodSugar) { this.bloodSugar = bloodSugar; }
    
    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }
    
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
    public static BloodSugarDTO fromEntity(BloodSugar entity) {
        return new BloodSugarDTO(
            entity.getId(),
            entity.getMbId(),
            entity.getBloodSugar(),
            entity.getMeasurementType(),
            entity.getStatus(),
            entity.getMeasuredAt(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    /**
     * DTO를 Entity로 변환
     */
    public BloodSugar toEntity() {
        BloodSugar entity = new BloodSugar();
        entity.setId(this.id);
        entity.setMbId(this.mbId);
        entity.setBloodSugar(this.bloodSugar);
        entity.setMeasurementType(this.measurementType);
        entity.setStatus(this.status);
        entity.setMeasuredAt(this.measuredAt);
        return entity;
    }
}
