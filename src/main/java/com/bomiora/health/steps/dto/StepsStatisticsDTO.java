package com.bomiora.health.steps.dto;

import java.time.LocalDate;
import java.util.List;

public class StepsStatisticsDTO {
    
    // 오늘의 걸음수
    private Integer todaySteps;
    private Double todayDistance;
    private Integer todayCalories;
    
    // 전날 대비 증감
    private Integer stepsDifference;
    private Double distanceDifference;
    private Integer caloriesDifference;
    
    // 주간 통계
    private Integer weeklyAverageSteps;
    private Integer weeklyTotalSteps;
    private Double weeklyAverageDistance;
    private Integer weeklyTotalCalories;
    
    // 월간 통계
    private Integer monthlyAverageSteps;
    private Integer monthlyTotalSteps;
    private Double monthlyAverageDistance;
    private Integer monthlyTotalCalories;
    
    // 목표 달성률
    private Double goalAchievementRate;
    private Integer consecutiveGoalDays;
    
    // 최고 기록
    private Integer maxStepsInDay;
    private LocalDate maxStepsDate;
    private Double maxDistanceInDay;
    private LocalDate maxDistanceDate;
    
    // 주간 데이터
    private List<StepsRecordDTO> weeklyData;
    
    // 월간 데이터
    private List<StepsRecordDTO> monthlyData;
    
    // 시간대별 평균 걸음수
    private List<HourlyAverageDTO> hourlyAverages;
    
    // 기본 생성자
    public StepsStatisticsDTO() {}
    
    // Getter 메서드들
    public Integer getTodaySteps() { return todaySteps; }
    public Double getTodayDistance() { return todayDistance; }
    public Integer getTodayCalories() { return todayCalories; }
    public Integer getStepsDifference() { return stepsDifference; }
    public Double getDistanceDifference() { return distanceDifference; }
    public Integer getCaloriesDifference() { return caloriesDifference; }
    public Integer getWeeklyAverageSteps() { return weeklyAverageSteps; }
    public Integer getWeeklyTotalSteps() { return weeklyTotalSteps; }
    public Double getWeeklyAverageDistance() { return weeklyAverageDistance; }
    public Integer getWeeklyTotalCalories() { return weeklyTotalCalories; }
    public Integer getMonthlyAverageSteps() { return monthlyAverageSteps; }
    public Integer getMonthlyTotalSteps() { return monthlyTotalSteps; }
    public Double getMonthlyAverageDistance() { return monthlyAverageDistance; }
    public Integer getMonthlyTotalCalories() { return monthlyTotalCalories; }
    public Double getGoalAchievementRate() { return goalAchievementRate; }
    public Integer getConsecutiveGoalDays() { return consecutiveGoalDays; }
    public Integer getMaxStepsInDay() { return maxStepsInDay; }
    public LocalDate getMaxStepsDate() { return maxStepsDate; }
    public Double getMaxDistanceInDay() { return maxDistanceInDay; }
    public LocalDate getMaxDistanceDate() { return maxDistanceDate; }
    public List<StepsRecordDTO> getWeeklyData() { return weeklyData; }
    public List<StepsRecordDTO> getMonthlyData() { return monthlyData; }
    public List<HourlyAverageDTO> getHourlyAverages() { return hourlyAverages; }
    
    // Setter 메서드들
    public void setTodaySteps(Integer todaySteps) { this.todaySteps = todaySteps; }
    public void setTodayDistance(Double todayDistance) { this.todayDistance = todayDistance; }
    public void setTodayCalories(Integer todayCalories) { this.todayCalories = todayCalories; }
    public void setStepsDifference(Integer stepsDifference) { this.stepsDifference = stepsDifference; }
    public void setDistanceDifference(Double distanceDifference) { this.distanceDifference = distanceDifference; }
    public void setCaloriesDifference(Integer caloriesDifference) { this.caloriesDifference = caloriesDifference; }
    public void setWeeklyAverageSteps(Integer weeklyAverageSteps) { this.weeklyAverageSteps = weeklyAverageSteps; }
    public void setWeeklyTotalSteps(Integer weeklyTotalSteps) { this.weeklyTotalSteps = weeklyTotalSteps; }
    public void setWeeklyAverageDistance(Double weeklyAverageDistance) { this.weeklyAverageDistance = weeklyAverageDistance; }
    public void setWeeklyTotalCalories(Integer weeklyTotalCalories) { this.weeklyTotalCalories = weeklyTotalCalories; }
    public void setMonthlyAverageSteps(Integer monthlyAverageSteps) { this.monthlyAverageSteps = monthlyAverageSteps; }
    public void setMonthlyTotalSteps(Integer monthlyTotalSteps) { this.monthlyTotalSteps = monthlyTotalSteps; }
    public void setMonthlyAverageDistance(Double monthlyAverageDistance) { this.monthlyAverageDistance = monthlyAverageDistance; }
    public void setMonthlyTotalCalories(Integer monthlyTotalCalories) { this.monthlyTotalCalories = monthlyTotalCalories; }
    public void setGoalAchievementRate(Double goalAchievementRate) { this.goalAchievementRate = goalAchievementRate; }
    public void setConsecutiveGoalDays(Integer consecutiveGoalDays) { this.consecutiveGoalDays = consecutiveGoalDays; }
    public void setMaxStepsInDay(Integer maxStepsInDay) { this.maxStepsInDay = maxStepsInDay; }
    public void setMaxStepsDate(LocalDate maxStepsDate) { this.maxStepsDate = maxStepsDate; }
    public void setMaxDistanceInDay(Double maxDistanceInDay) { this.maxDistanceInDay = maxDistanceInDay; }
    public void setMaxDistanceDate(LocalDate maxDistanceDate) { this.maxDistanceDate = maxDistanceDate; }
    public void setWeeklyData(List<StepsRecordDTO> weeklyData) { this.weeklyData = weeklyData; }
    public void setMonthlyData(List<StepsRecordDTO> monthlyData) { this.monthlyData = monthlyData; }
    public void setHourlyAverages(List<HourlyAverageDTO> hourlyAverages) { this.hourlyAverages = hourlyAverages; }
}

class HourlyAverageDTO {
    private Integer hour;
    private Double averageSteps;
    private Double averageDistance;
    private Double averageCalories;
    
    // 기본 생성자
    public HourlyAverageDTO() {}
    
    // 전체 생성자
    public HourlyAverageDTO(Integer hour, Double averageSteps, Double averageDistance, Double averageCalories) {
        this.hour = hour;
        this.averageSteps = averageSteps;
        this.averageDistance = averageDistance;
        this.averageCalories = averageCalories;
    }
    
    // Getter 메서드들
    public Integer getHour() { return hour; }
    public Double getAverageSteps() { return averageSteps; }
    public Double getAverageDistance() { return averageDistance; }
    public Double getAverageCalories() { return averageCalories; }
    
    // Setter 메서드들
    public void setHour(Integer hour) { this.hour = hour; }
    public void setAverageSteps(Double averageSteps) { this.averageSteps = averageSteps; }
    public void setAverageDistance(Double averageDistance) { this.averageDistance = averageDistance; }
    public void setAverageCalories(Double averageCalories) { this.averageCalories = averageCalories; }
}