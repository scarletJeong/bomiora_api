package com.bomiora.health.steps.dto;

import java.time.LocalDate;

public class StepsRequestDTO {
    
    private Long userId;
    private LocalDate recordDate;
    
    // 기본 걸음수 정보
    private Integer totalSteps;
    private Double distanceKm;
    private Integer caloriesBurned;
    
    // 목표 설정
    private Integer dailyGoal;
    
    // 연동 정보
    private StepsRecordDTO.SourceType sourceType;
    private String sourceId;
    
    // 시간별 데이터
    private java.util.List<HourlyStepsDTO> hourlySteps;
    
    // 추가 메타데이터
    private Integer activeMinutes;
    private Integer flightsClimbed;
    private Integer avgHeartRate;
    private Integer maxHeartRate;
    
    // 기본 생성자
    public StepsRequestDTO() {}
    
    // Getter 메서드들
    public Long getUserId() { return userId; }
    public LocalDate getRecordDate() { return recordDate; }
    public Integer getTotalSteps() { return totalSteps; }
    public Double getDistanceKm() { return distanceKm; }
    public Integer getCaloriesBurned() { return caloriesBurned; }
    public Integer getDailyGoal() { return dailyGoal; }
    public StepsRecordDTO.SourceType getSourceType() { return sourceType; }
    public String getSourceId() { return sourceId; }
    public java.util.List<HourlyStepsDTO> getHourlySteps() { return hourlySteps; }
    public Integer getActiveMinutes() { return activeMinutes; }
    public Integer getFlightsClimbed() { return flightsClimbed; }
    public Integer getAvgHeartRate() { return avgHeartRate; }
    public Integer getMaxHeartRate() { return maxHeartRate; }
    
    // Setter 메서드들
    public void setUserId(Long userId) { this.userId = userId; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }
    public void setTotalSteps(Integer totalSteps) { this.totalSteps = totalSteps; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }
    public void setCaloriesBurned(Integer caloriesBurned) { this.caloriesBurned = caloriesBurned; }
    public void setDailyGoal(Integer dailyGoal) { this.dailyGoal = dailyGoal; }
    public void setSourceType(StepsRecordDTO.SourceType sourceType) { this.sourceType = sourceType; }
    public void setSourceId(String sourceId) { this.sourceId = sourceId; }
    public void setHourlySteps(java.util.List<HourlyStepsDTO> hourlySteps) { this.hourlySteps = hourlySteps; }
    public void setActiveMinutes(Integer activeMinutes) { this.activeMinutes = activeMinutes; }
    public void setFlightsClimbed(Integer flightsClimbed) { this.flightsClimbed = flightsClimbed; }
    public void setAvgHeartRate(Integer avgHeartRate) { this.avgHeartRate = avgHeartRate; }
    public void setMaxHeartRate(Integer maxHeartRate) { this.maxHeartRate = maxHeartRate; }
}

class StepsUpdateDTO {
    
    private Integer totalSteps;
    private Double distanceKm;
    private Integer caloriesBurned;
    private Integer dailyGoal;
    private java.util.List<HourlyStepsDTO> hourlySteps;
    private Integer activeMinutes;
    private Integer flightsClimbed;
    private Integer avgHeartRate;
    private Integer maxHeartRate;
    
    // 기본 생성자
    public StepsUpdateDTO() {}
    
    // Getter 메서드들
    public Integer getTotalSteps() { return totalSteps; }
    public Double getDistanceKm() { return distanceKm; }
    public Integer getCaloriesBurned() { return caloriesBurned; }
    public Integer getDailyGoal() { return dailyGoal; }
    public java.util.List<HourlyStepsDTO> getHourlySteps() { return hourlySteps; }
    public Integer getActiveMinutes() { return activeMinutes; }
    public Integer getFlightsClimbed() { return flightsClimbed; }
    public Integer getAvgHeartRate() { return avgHeartRate; }
    public Integer getMaxHeartRate() { return maxHeartRate; }
    
    // Setter 메서드들
    public void setTotalSteps(Integer totalSteps) { this.totalSteps = totalSteps; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }
    public void setCaloriesBurned(Integer caloriesBurned) { this.caloriesBurned = caloriesBurned; }
    public void setDailyGoal(Integer dailyGoal) { this.dailyGoal = dailyGoal; }
    public void setHourlySteps(java.util.List<HourlyStepsDTO> hourlySteps) { this.hourlySteps = hourlySteps; }
    public void setActiveMinutes(Integer activeMinutes) { this.activeMinutes = activeMinutes; }
    public void setFlightsClimbed(Integer flightsClimbed) { this.flightsClimbed = flightsClimbed; }
    public void setAvgHeartRate(Integer avgHeartRate) { this.avgHeartRate = avgHeartRate; }
    public void setMaxHeartRate(Integer maxHeartRate) { this.maxHeartRate = maxHeartRate; }
}