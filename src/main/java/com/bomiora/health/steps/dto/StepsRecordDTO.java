package com.bomiora.health.steps.dto;

import com.bomiora.health.steps.entity.StepsRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StepsRecordDTO {
    
    private Long id;
    private Long userId;
    private LocalDate recordDate;
    
    // 기본 걸음수 정보
    private Integer totalSteps;
    private Double distanceKm;
    private Integer caloriesBurned;
    
    // 목표 설정
    private Integer dailyGoal;
    
    // 연동 정보
    private SourceType sourceType;
    private String sourceId;
    
    // 시간별 데이터
    private List<HourlyStepsDTO> hourlySteps;
    
    // 추가 메타데이터
    private Integer activeMinutes;
    private Integer flightsClimbed;
    private Integer avgHeartRate;
    private Integer maxHeartRate;
    
    // 연동 상태
    private SyncStatus syncStatus;
    private LocalDateTime lastSyncTime;
    private String syncErrorMessage;
    
    // 감사 필드
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 통계 정보 (조회 시 추가)
    private StepsStatisticsDTO statistics;
    
    // 목표 달성 여부
    private Boolean goalAchieved;
    
    // 전날 대비 증감
    private Integer stepsDifference;
    private Double distanceDifference;
    private Integer caloriesDifference;
    
    // 기본 생성자
    public StepsRecordDTO() {}
    
    // 전체 생성자
    public StepsRecordDTO(Long id, Long userId, LocalDate recordDate, Integer totalSteps, 
                         Double distanceKm, Integer caloriesBurned, Integer dailyGoal, 
                         SourceType sourceType, String sourceId, 
                         List<HourlyStepsDTO> hourlySteps, Integer activeMinutes, 
                         Integer flightsClimbed, Integer avgHeartRate, Integer maxHeartRate, 
                         SyncStatus syncStatus, LocalDateTime lastSyncTime, 
                         String syncErrorMessage, LocalDateTime createdAt, LocalDateTime updatedAt, 
                         StepsStatisticsDTO statistics, Boolean goalAchieved, 
                         Integer stepsDifference, Double distanceDifference, Integer caloriesDifference) {
        this.id = id;
        this.userId = userId;
        this.recordDate = recordDate;
        this.totalSteps = totalSteps;
        this.distanceKm = distanceKm;
        this.caloriesBurned = caloriesBurned;
        this.dailyGoal = dailyGoal;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.hourlySteps = hourlySteps;
        this.activeMinutes = activeMinutes;
        this.flightsClimbed = flightsClimbed;
        this.avgHeartRate = avgHeartRate;
        this.maxHeartRate = maxHeartRate;
        this.syncStatus = syncStatus;
        this.lastSyncTime = lastSyncTime;
        this.syncErrorMessage = syncErrorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.statistics = statistics;
        this.goalAchieved = goalAchieved;
        this.stepsDifference = stepsDifference;
        this.distanceDifference = distanceDifference;
        this.caloriesDifference = caloriesDifference;
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
    public List<HourlyStepsDTO> getHourlySteps() { return hourlySteps; }
    public Integer getActiveMinutes() { return activeMinutes; }
    public Integer getFlightsClimbed() { return flightsClimbed; }
    public Integer getAvgHeartRate() { return avgHeartRate; }
    public Integer getMaxHeartRate() { return maxHeartRate; }
    public SyncStatus getSyncStatus() { return syncStatus; }
    public LocalDateTime getLastSyncTime() { return lastSyncTime; }
    public String getSyncErrorMessage() { return syncErrorMessage; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public StepsStatisticsDTO getStatistics() { return statistics; }
    public Boolean getGoalAchieved() { return goalAchieved; }
    public Integer getStepsDifference() { return stepsDifference; }
    public Double getDistanceDifference() { return distanceDifference; }
    public Integer getCaloriesDifference() { return caloriesDifference; }
    
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
    public void setHourlySteps(List<HourlyStepsDTO> hourlySteps) { this.hourlySteps = hourlySteps; }
    public void setActiveMinutes(Integer activeMinutes) { this.activeMinutes = activeMinutes; }
    public void setFlightsClimbed(Integer flightsClimbed) { this.flightsClimbed = flightsClimbed; }
    public void setAvgHeartRate(Integer avgHeartRate) { this.avgHeartRate = avgHeartRate; }
    public void setMaxHeartRate(Integer maxHeartRate) { this.maxHeartRate = maxHeartRate; }
    public void setSyncStatus(SyncStatus syncStatus) { this.syncStatus = syncStatus; }
    public void setLastSyncTime(LocalDateTime lastSyncTime) { this.lastSyncTime = lastSyncTime; }
    public void setSyncErrorMessage(String syncErrorMessage) { this.syncErrorMessage = syncErrorMessage; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setStatistics(StepsStatisticsDTO statistics) { this.statistics = statistics; }
    public void setGoalAchieved(Boolean goalAchieved) { this.goalAchieved = goalAchieved; }
    public void setStepsDifference(Integer stepsDifference) { this.stepsDifference = stepsDifference; }
    public void setDistanceDifference(Double distanceDifference) { this.distanceDifference = distanceDifference; }
    public void setCaloriesDifference(Integer caloriesDifference) { this.caloriesDifference = caloriesDifference; }
    
    // 연동 소스 타입 (StepsRecord와 동일)
    public enum SourceType {
        MANUAL,     // 수동 입력
        APPLE_HEALTH,   // 애플 헬스
        SAMSUNG_HEALTH, // 삼성 헬스
        GOOGLE_FIT,     // 구글 핏
        OTHER           // 기타
    }
    
    // 동기화 상태 (StepsRecord와 동일)
    public enum SyncStatus {
        NOT_SYNCED,     // 동기화 안됨
        SYNCED,         // 동기화 완료
        SYNC_FAILED,    // 동기화 실패
        SYNCING         // 동기화 중
    }
}
