package com.bomiora.health.steps.dto;

import java.time.LocalDateTime;

public class HourlyStepsDTO {
    
    private Long id;
    private Long stepsRecordId;
    private Integer hour; // 0-23
    private Integer steps;
    private Double distanceKm;
    private Integer caloriesBurned;
    private Integer activeMinutes;
    
    // 감사 필드
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 기본 생성자
    public HourlyStepsDTO() {}
    
    // 전체 생성자
    public HourlyStepsDTO(Long id, Long stepsRecordId, Integer hour, Integer steps, 
                          Double distanceKm, Integer caloriesBurned, Integer activeMinutes, 
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.stepsRecordId = stepsRecordId;
        this.hour = hour;
        this.steps = steps;
        this.distanceKm = distanceKm;
        this.caloriesBurned = caloriesBurned;
        this.activeMinutes = activeMinutes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getter 메서드들
    public Long getId() { return id; }
    public Long getStepsRecordId() { return stepsRecordId; }
    public Integer getHour() { return hour; }
    public Integer getSteps() { return steps; }
    public Double getDistanceKm() { return distanceKm; }
    public Integer getCaloriesBurned() { return caloriesBurned; }
    public Integer getActiveMinutes() { return activeMinutes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setStepsRecordId(Long stepsRecordId) { this.stepsRecordId = stepsRecordId; }
    public void setHour(Integer hour) { this.hour = hour; }
    public void setSteps(Integer steps) { this.steps = steps; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }
    public void setCaloriesBurned(Integer caloriesBurned) { this.caloriesBurned = caloriesBurned; }
    public void setActiveMinutes(Integer activeMinutes) { this.activeMinutes = activeMinutes; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
