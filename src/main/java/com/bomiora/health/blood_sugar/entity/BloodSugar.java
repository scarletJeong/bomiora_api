package com.bomiora.health.blood_sugar.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bm_blood_sugar")
public class BloodSugar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "mb_id", nullable = false, length = 50)
    private String mbId;
    
    @Column(name = "blood_sugar", nullable = false)
    private Integer bloodSugar; // 혈당 수치 (mg/dL)
    
    @Column(name = "measurement_type", nullable = false, length = 20)
    private String measurementType; // 측정 유형 (공복, 식전, 식후, 취침전, 평상시)
    
    @Column(length = 20)
    private String status; // 혈당 상태 (정상, 당뇨 전단계, 당뇨, 저혈당)
    
    @Column(name = "measured_at", nullable = false)
    private LocalDateTime measuredAt; // 측정 일시
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 입력 일시
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 일시
    
    /**
     * 혈당 상태 자동 계산
     */
    @PrePersist
    @PreUpdate
    public void calculateStatus() {
        if (this.status == null || this.status.isEmpty()) {
            this.status = determineStatus(this.bloodSugar, this.measurementType);
        }
    }
    
    /**
     * 혈당 상태 판정 로직 (ADA 기준)
     */
    private String determineStatus(Integer bloodSugar, String measurementType) {
        switch (measurementType) {
            case "공복":
                if (bloodSugar < 70) {
                    return "저혈당";
                } else if (bloodSugar < 100) {
                    return "정상";
                } else if (bloodSugar < 126) {
                    return "당뇨 전단계";
                } else {
                    return "당뇨";
                }
            case "식후":
                if (bloodSugar < 140) {
                    return "정상";
                } else if (bloodSugar < 200) {
                    return "당뇨 전단계";
                } else {
                    return "당뇨";
                }
            case "식전":
                if (bloodSugar < 100) {
                    return "정상";
                } else if (bloodSugar < 126) {
                    return "당뇨 전단계";
                } else {
                    return "당뇨";
                }
            case "취침전":
                if (bloodSugar < 100) {
                    return "정상";
                } else if (bloodSugar < 140) {
                    return "당뇨 전단계";
                } else {
                    return "당뇨";
                }
            case "평상시":
                if (bloodSugar < 100) {
                    return "정상";
                } else if (bloodSugar < 126) {
                    return "당뇨 전단계";
                } else {
                    return "당뇨";
                }
            default:
                return "정상";
        }
    }
    
    // 기본 생성자
    public BloodSugar() {}
    
    // 전체 생성자
    public BloodSugar(Long id, String mbId, Integer bloodSugar, String measurementType, 
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
}
