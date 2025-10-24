package com.bomiora.health.steps.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "hourly_steps")
public class HourlySteps {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "steps_record_id", nullable = false)
    private Long stepsRecordId;
    
    @Column(name = "hour", nullable = false)
    private Integer hour; // 0-23
    
    @Column(name = "steps", nullable = false)
    private Integer steps;
    
    @Column(name = "distance_km")
    private Double distanceKm;
    
    @Column(name = "calories_burned")
    private Integer caloriesBurned;
    
    @Column(name = "active_minutes")
    private Integer activeMinutes;
    
    // 감사 필드
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // StepsRecord와의 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "steps_record_id", insertable = false, updatable = false)
    private StepsRecord stepsRecord;
    
    // 기본 생성자
    public HourlySteps() {}
    
    // 전체 생성자
    public HourlySteps(Long id, Long stepsRecordId, Integer hour, Integer steps, 
                      Double distanceKm, Integer caloriesBurned, Integer activeMinutes, 
                      LocalDateTime createdAt, LocalDateTime updatedAt, StepsRecord stepsRecord) {
        this.id = id;
        this.stepsRecordId = stepsRecordId;
        this.hour = hour;
        this.steps = steps;
        this.distanceKm = distanceKm;
        this.caloriesBurned = caloriesBurned;
        this.activeMinutes = activeMinutes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.stepsRecord = stepsRecord;
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
    public StepsRecord getStepsRecord() { return stepsRecord; }
    
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
    public void setStepsRecord(StepsRecord stepsRecord) { this.stepsRecord = stepsRecord; }
}
