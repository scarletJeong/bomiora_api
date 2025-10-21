package com.bomiora.health.blood_pressure.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bm_blood_pressure")
public class BloodPressure {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "mb_id", nullable = false, length = 50)
    private String mbId;
    
    @Column(nullable = false)
    private Integer systolic; // 수축기 혈압
    
    @Column(nullable = false)
    private Integer diastolic; // 이완기 혈압
    
    @Column(nullable = false)
    private Integer pulse; // 맥박/심박수
    
    @Column(length = 20)
    private String status; // 혈압 상태
    
    @Column(name = "measured_at", nullable = false)
    private LocalDateTime measuredAt; // 측정 일시
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 입력 일시
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 일시
    
    /**
     * 혈압 상태 자동 계산
     */
    @PrePersist
    @PreUpdate
    public void calculateStatus() {
        if (this.status == null || this.status.isEmpty()) {
            this.status = determineStatus(this.systolic, this.diastolic);
        }
    }
    
    /**
     * 혈압 상태 판정 로직 (AHA 및 대한고혈압학회 기준)
     */
    private String determineStatus(Integer systolic, Integer diastolic) {
        // 저혈압: 수축기 < 90 OR 이완기 < 60
        if (systolic < 90 || diastolic < 60) {
            return "저혈압";
        }
        // 고혈압 위기: 수축기 ≥ 180 OR 이완기 ≥ 120 (응급)
        else if (systolic >= 180 || diastolic >= 120) {
            return "고혈압 위기";
        }
        // 2기 고혈압: 수축기 ≥ 140 OR 이완기 ≥ 90
        else if (systolic >= 140 || diastolic >= 90) {
            return "2기 고혈압";
        }
        // 1기 고혈압: 수축기 130-139 OR 이완기 80-89
        else if ((systolic >= 130 && systolic < 140) || (diastolic >= 80 && diastolic < 90)) {
            return "1기 고혈압";
        }
        // 고혈압 전단계: 수축기 120-129 AND 이완기 < 80
        else if (systolic >= 120 && systolic < 130 && diastolic < 80) {
            return "고혈압 전단계";
        }
        // 정상: 수축기 < 120 AND 이완기 < 80
        else if (systolic < 120 && diastolic < 80) {
            return "정상";
        }
        else {
            return "정상";
        }
    }
    
    // 기본 생성자
    public BloodPressure() {}
    
    // 전체 생성자
    public BloodPressure(Long id, String mbId, Integer systolic, Integer diastolic, 
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
}

