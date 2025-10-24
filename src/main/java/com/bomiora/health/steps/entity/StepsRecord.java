package com.bomiora.health.steps.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "steps_records")
public class StepsRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;
    
    // 기본 걸음수 정보
    @Column(name = "total_steps", nullable = false)
    private Integer totalSteps;
    
    @Column(name = "distance_km")
    private Double distanceKm;
    
    @Column(name = "calories_burned")
    private Integer caloriesBurned;
    
    // 목표 설정
    @Column(name = "daily_goal")
    private Integer dailyGoal;
    
    // 연동 정보 (애플/갤럭시 구분)
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private SourceType sourceType;
    
    @Column(name = "source_id")
    private String sourceId; // 외부 앱의 기록 ID
    
    // 시간별 데이터 (JSON으로 저장)
    @Column(name = "hourly_data", columnDefinition = "TEXT")
    private String hourlyData;
    
    // 추가 메타데이터
    @Column(name = "active_minutes")
    private Integer activeMinutes;
    
    @Column(name = "flights_climbed")
    private Integer flightsClimbed;
    
    @Column(name = "avg_heart_rate")
    private Integer avgHeartRate;
    
    @Column(name = "max_heart_rate")
    private Integer maxHeartRate;
    
    // 연동 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status")
    private SyncStatus syncStatus;
    
    @Column(name = "last_sync_time")
    private LocalDateTime lastSyncTime;
    
    @Column(name = "sync_error_message", columnDefinition = "TEXT")
    private String syncErrorMessage;
    
    // 감사 필드
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 기본 생성자
    public StepsRecord() {}
    
    // 전체 생성자
    public StepsRecord(Long id, Long userId, LocalDate recordDate, Integer totalSteps, 
                      Double distanceKm, Integer caloriesBurned, Integer dailyGoal, 
                      SourceType sourceType, String sourceId, String hourlyData, 
                      Integer activeMinutes, Integer flightsClimbed, Integer avgHeartRate, 
                      Integer maxHeartRate, SyncStatus syncStatus, LocalDateTime lastSyncTime, 
                      String syncErrorMessage, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.recordDate = recordDate;
        this.totalSteps = totalSteps;
        this.distanceKm = distanceKm;
        this.caloriesBurned = caloriesBurned;
        this.dailyGoal = dailyGoal;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.hourlyData = hourlyData;
        this.activeMinutes = activeMinutes;
        this.flightsClimbed = flightsClimbed;
        this.avgHeartRate = avgHeartRate;
        this.maxHeartRate = maxHeartRate;
        this.syncStatus = syncStatus;
        this.lastSyncTime = lastSyncTime;
        this.syncErrorMessage = syncErrorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getter 메서드들
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public LocalDate getRecordDate() { return recordDate; }
    public Integer getTotalSteps() { return totalSteps; }
    public Double getDistanceKm() { return distanceKm; }
    public Integer getCaloriesBurned() { return caloriesBurned; }
    public Integer getDailyGoal() { return dailyGoal; }
    public SourceType getSourceType() { return sourceType; }
    public String getSourceId() { return sourceId; }
    public String getHourlyData() { return hourlyData; }
    public Integer getActiveMinutes() { return activeMinutes; }
    public Integer getFlightsClimbed() { return flightsClimbed; }
    public Integer getAvgHeartRate() { return avgHeartRate; }
    public Integer getMaxHeartRate() { return maxHeartRate; }
    public SyncStatus getSyncStatus() { return syncStatus; }
    public LocalDateTime getLastSyncTime() { return lastSyncTime; }
    public String getSyncErrorMessage() { return syncErrorMessage; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }
    public void setTotalSteps(Integer totalSteps) { this.totalSteps = totalSteps; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }
    public void setCaloriesBurned(Integer caloriesBurned) { this.caloriesBurned = caloriesBurned; }
    public void setDailyGoal(Integer dailyGoal) { this.dailyGoal = dailyGoal; }
    public void setSourceType(SourceType sourceType) { this.sourceType = sourceType; }
    public void setSourceId(String sourceId) { this.sourceId = sourceId; }
    public void setHourlyData(String hourlyData) { this.hourlyData = hourlyData; }
    public void setActiveMinutes(Integer activeMinutes) { this.activeMinutes = activeMinutes; }
    public void setFlightsClimbed(Integer flightsClimbed) { this.flightsClimbed = flightsClimbed; }
    public void setAvgHeartRate(Integer avgHeartRate) { this.avgHeartRate = avgHeartRate; }
    public void setMaxHeartRate(Integer maxHeartRate) { this.maxHeartRate = maxHeartRate; }
    public void setSyncStatus(SyncStatus syncStatus) { this.syncStatus = syncStatus; }
    public void setLastSyncTime(LocalDateTime lastSyncTime) { this.lastSyncTime = lastSyncTime; }
    public void setSyncErrorMessage(String syncErrorMessage) { this.syncErrorMessage = syncErrorMessage; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // 연동 소스 타입
    public enum SourceType {
        MANUAL,     // 수동 입력
        APPLE_HEALTH,   // 애플 헬스
        SAMSUNG_HEALTH, // 삼성 헬스
        GOOGLE_FIT,     // 구글 핏
        OTHER           // 기타
    }
    
    // 동기화 상태
    public enum SyncStatus {
        NOT_SYNCED,     // 동기화 안됨
        SYNCED,         // 동기화 완료
        SYNC_FAILED,    // 동기화 실패
        SYNCING         // 동기화 중
    }
}
