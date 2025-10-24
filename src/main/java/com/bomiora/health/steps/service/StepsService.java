package com.bomiora.health.steps.service;

import com.bomiora.health.steps.dto.*;
import com.bomiora.health.steps.entity.HourlySteps;
import com.bomiora.health.steps.entity.StepsRecord;
import com.bomiora.health.steps.repository.HourlyStepsRepository;
import com.bomiora.health.steps.repository.StepsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StepsService {
    
    private static final Logger log = LoggerFactory.getLogger(StepsService.class);
    
    private final StepsRepository stepsRepository;
    private final HourlyStepsRepository hourlyStepsRepository;
    
    public StepsService(StepsRepository stepsRepository, HourlyStepsRepository hourlyStepsRepository) {
        this.stepsRepository = stepsRepository;
        this.hourlyStepsRepository = hourlyStepsRepository;
    }
    
    // 걸음수 기록 생성
    @Transactional
    public StepsRecordDTO createStepsRecord(StepsRequestDTO requestDTO) {
        log.info("걸음수 기록 생성 시작 - 사용자 ID: {}, 날짜: {}", requestDTO.getUserId(), requestDTO.getRecordDate());
        
        // 기존 기록이 있는지 확인
        Optional<StepsRecord> existingRecord = stepsRepository.findByUserIdAndRecordDate(
            requestDTO.getUserId(), requestDTO.getRecordDate());
        
        if (existingRecord.isPresent()) {
            throw new IllegalArgumentException("해당 날짜의 걸음수 기록이 이미 존재합니다.");
        }
        
        // StepsRecord 생성
        StepsRecord stepsRecord = new StepsRecord();
        stepsRecord.setUserId(requestDTO.getUserId());
        stepsRecord.setRecordDate(requestDTO.getRecordDate());
        stepsRecord.setTotalSteps(requestDTO.getTotalSteps());
        stepsRecord.setDistanceKm(requestDTO.getDistanceKm());
        stepsRecord.setCaloriesBurned(requestDTO.getCaloriesBurned());
        stepsRecord.setDailyGoal(requestDTO.getDailyGoal());
        stepsRecord.setSourceType(requestDTO.getSourceType() != null ? 
            convertSourceType(requestDTO.getSourceType()) : StepsRecord.SourceType.MANUAL);
        stepsRecord.setSourceId(requestDTO.getSourceId());
        stepsRecord.setActiveMinutes(requestDTO.getActiveMinutes());
        stepsRecord.setFlightsClimbed(requestDTO.getFlightsClimbed());
        stepsRecord.setAvgHeartRate(requestDTO.getAvgHeartRate());
        stepsRecord.setMaxHeartRate(requestDTO.getMaxHeartRate());
        stepsRecord.setSyncStatus(StepsRecord.SyncStatus.NOT_SYNCED);
        
        StepsRecord savedRecord = stepsRepository.save(stepsRecord);
        
        // 시간별 데이터 저장
        if (requestDTO.getHourlySteps() != null && !requestDTO.getHourlySteps().isEmpty()) {
            saveHourlySteps(savedRecord.getId(), requestDTO.getHourlySteps());
        }
        
        log.info("걸음수 기록 생성 완료 - ID: {}", savedRecord.getId());
        return convertToDTO(savedRecord);
    }
    
    // 걸음수 기록 수정
    @Transactional
    public StepsRecordDTO updateStepsRecord(Long recordId, StepsRequestDTO requestDTO) {
        log.info("걸음수 기록 수정 시작 - ID: {}", recordId);
        
        StepsRecord stepsRecord = stepsRepository.findById(recordId)
            .orElseThrow(() -> new IllegalArgumentException("걸음수 기록을 찾을 수 없습니다."));
        
        // 기본 정보 업데이트
        stepsRecord.setTotalSteps(requestDTO.getTotalSteps());
        stepsRecord.setDistanceKm(requestDTO.getDistanceKm());
        stepsRecord.setCaloriesBurned(requestDTO.getCaloriesBurned());
        stepsRecord.setDailyGoal(requestDTO.getDailyGoal());
        stepsRecord.setActiveMinutes(requestDTO.getActiveMinutes());
        stepsRecord.setFlightsClimbed(requestDTO.getFlightsClimbed());
        stepsRecord.setAvgHeartRate(requestDTO.getAvgHeartRate());
        stepsRecord.setMaxHeartRate(requestDTO.getMaxHeartRate());
        
        StepsRecord savedRecord = stepsRepository.save(stepsRecord);
        
        // 시간별 데이터 업데이트
        if (requestDTO.getHourlySteps() != null && !requestDTO.getHourlySteps().isEmpty()) {
            hourlyStepsRepository.deleteByStepsRecordId(recordId);
            saveHourlySteps(recordId, requestDTO.getHourlySteps());
        }
        
        log.info("걸음수 기록 수정 완료 - ID: {}", recordId);
        return convertToDTO(savedRecord);
    }
    
    // 걸음수 기록 삭제
    @Transactional
    public void deleteStepsRecord(Long recordId) {
        log.info("걸음수 기록 삭제 시작 - ID: {}", recordId);
        
        StepsRecord stepsRecord = stepsRepository.findById(recordId)
            .orElseThrow(() -> new IllegalArgumentException("걸음수 기록을 찾을 수 없습니다."));
        
        // 시간별 데이터 먼저 삭제
        hourlyStepsRepository.deleteByStepsRecordId(recordId);
        
        // 메인 기록 삭제
        stepsRepository.delete(stepsRecord);
        
        log.info("걸음수 기록 삭제 완료 - ID: {}", recordId);
    }
    
    // 특정 날짜의 걸음수 기록 조회
    public StepsRecordDTO getStepsRecordByDate(Long userId, LocalDate recordDate) {
        log.info("걸음수 기록 조회 - 사용자 ID: {}, 날짜: {}", userId, recordDate);
        
        Optional<StepsRecord> stepsRecord = stepsRepository.findByUserIdAndRecordDate(userId, recordDate);
        
        if (stepsRecord.isEmpty()) {
            return null;
        }
        
        StepsRecordDTO dto = convertToDTO(stepsRecord.get());
        
        // 전날 대비 증감 계산
        calculateDailyComparison(userId, recordDate, dto);
        
        return dto;
    }
    
    // 오늘의 걸음수 기록 조회
    public StepsRecordDTO getTodayStepsRecord(Long userId) {
        return getStepsRecordByDate(userId, LocalDate.now());
    }
    
    // 주간 걸음수 기록 조회
    public List<StepsRecordDTO> getWeeklyStepsRecords(Long userId, LocalDate startDate) {
        log.info("주간 걸음수 기록 조회 - 사용자 ID: {}, 시작일: {}", userId, startDate);
        
        LocalDate endDate = startDate.plusDays(6);
        List<StepsRecord> records = stepsRepository.findWeeklySteps(userId, startDate, endDate);
        
        return records.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // 월간 걸음수 기록 조회
    public List<StepsRecordDTO> getMonthlyStepsRecords(Long userId, int year, int month) {
        log.info("월간 걸음수 기록 조회 - 사용자 ID: {}, 년도: {}, 월: {}", userId, year, month);
        
        List<StepsRecord> records = stepsRepository.findMonthlySteps(userId, year, month);
        
        return records.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // 걸음수 통계 조회
    public StepsStatisticsDTO getStepsStatistics(Long userId) {
        log.info("걸음수 통계 조회 - 사용자 ID: {}", userId);
        
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6);
        LocalDate monthStart = today.withDayOfMonth(1);
        
        // 오늘의 기록
        StepsRecordDTO todayRecord = getTodayStepsRecord(userId);
        
        // 주간 데이터
        List<StepsRecordDTO> weeklyData = getWeeklyStepsRecords(userId, weekStart);
        
        // 월간 데이터
        List<StepsRecordDTO> monthlyData = getMonthlyStepsRecords(userId, today.getYear(), today.getMonthValue());
        
        // 통계 계산
        StepsStatisticsDTO statistics = new StepsStatisticsDTO();
        statistics.setTodaySteps(todayRecord != null ? todayRecord.getTotalSteps() : 0);
        statistics.setTodayDistance(todayRecord != null ? todayRecord.getDistanceKm() : 0.0);
        statistics.setTodayCalories(todayRecord != null ? todayRecord.getCaloriesBurned() : 0);
        statistics.setWeeklyData(weeklyData);
        statistics.setMonthlyData(monthlyData);
        
        // 주간 통계 계산
        calculateWeeklyStatistics(weeklyData, statistics);
        
        // 월간 통계 계산
        calculateMonthlyStatistics(monthlyData, statistics);
        
        // 전날 대비 증감
        if (todayRecord != null) {
            statistics.setStepsDifference(todayRecord.getStepsDifference());
            statistics.setDistanceDifference(todayRecord.getDistanceDifference());
            statistics.setCaloriesDifference(todayRecord.getCaloriesDifference());
        }
        
        return statistics;
    }
    
    // 시간별 데이터 저장
    @Transactional
    private void saveHourlySteps(Long stepsRecordId, List<HourlyStepsDTO> hourlyStepsDTOs) {
        List<HourlySteps> hourlySteps = hourlyStepsDTOs.stream()
            .map(dto -> {
                HourlySteps hourlyStep = new HourlySteps();
                hourlyStep.setStepsRecordId(stepsRecordId);
                hourlyStep.setHour(dto.getHour());
                hourlyStep.setSteps(dto.getSteps());
                hourlyStep.setDistanceKm(dto.getDistanceKm());
                hourlyStep.setCaloriesBurned(dto.getCaloriesBurned());
                hourlyStep.setActiveMinutes(dto.getActiveMinutes());
                return hourlyStep;
            })
            .collect(Collectors.toList());
        
        hourlyStepsRepository.saveAll(hourlySteps);
    }
    
    // 전날 대비 증감 계산
    private void calculateDailyComparison(Long userId, LocalDate recordDate, StepsRecordDTO dto) {
        LocalDate previousDate = recordDate.minusDays(1);
        Optional<StepsRecord> previousRecord = stepsRepository.findByUserIdAndRecordDate(userId, previousDate);
        
        if (previousRecord.isPresent()) {
            StepsRecord prev = previousRecord.get();
            dto.setStepsDifference(dto.getTotalSteps() - prev.getTotalSteps());
            dto.setDistanceDifference(dto.getDistanceKm() - prev.getDistanceKm());
            dto.setCaloriesDifference(dto.getCaloriesBurned() - prev.getCaloriesBurned());
        } else {
            dto.setStepsDifference(0);
            dto.setDistanceDifference(0.0);
            dto.setCaloriesDifference(0);
        }
        
        // 목표 달성 여부
        dto.setGoalAchieved(dto.getDailyGoal() != null && dto.getTotalSteps() >= dto.getDailyGoal());
    }
    
    // 주간 통계 계산
    private void calculateWeeklyStatistics(List<StepsRecordDTO> weeklyData, StepsStatisticsDTO statistics) {
        if (weeklyData.isEmpty()) {
            statistics.setWeeklyAverageSteps(0);
            statistics.setWeeklyTotalSteps(0);
            statistics.setWeeklyAverageDistance(0.0);
            statistics.setWeeklyTotalCalories(0);
            return;
        }
        
        int totalSteps = weeklyData.stream().mapToInt(StepsRecordDTO::getTotalSteps).sum();
        double totalDistance = weeklyData.stream().mapToDouble(StepsRecordDTO::getDistanceKm).sum();
        int totalCalories = weeklyData.stream().mapToInt(StepsRecordDTO::getCaloriesBurned).sum();
        
        statistics.setWeeklyAverageSteps(totalSteps / weeklyData.size());
        statistics.setWeeklyTotalSteps(totalSteps);
        statistics.setWeeklyAverageDistance(totalDistance / weeklyData.size());
        statistics.setWeeklyTotalCalories(totalCalories);
    }
    
    // 월간 통계 계산
    private void calculateMonthlyStatistics(List<StepsRecordDTO> monthlyData, StepsStatisticsDTO statistics) {
        if (monthlyData.isEmpty()) {
            statistics.setMonthlyAverageSteps(0);
            statistics.setMonthlyTotalSteps(0);
            statistics.setMonthlyAverageDistance(0.0);
            statistics.setMonthlyTotalCalories(0);
            return;
        }
        
        int totalSteps = monthlyData.stream().mapToInt(StepsRecordDTO::getTotalSteps).sum();
        double totalDistance = monthlyData.stream().mapToDouble(StepsRecordDTO::getDistanceKm).sum();
        int totalCalories = monthlyData.stream().mapToInt(StepsRecordDTO::getCaloriesBurned).sum();
        
        statistics.setMonthlyAverageSteps(totalSteps / monthlyData.size());
        statistics.setMonthlyTotalSteps(totalSteps);
        statistics.setMonthlyAverageDistance(totalDistance / monthlyData.size());
        statistics.setMonthlyTotalCalories(totalCalories);
    }
    
    // Entity를 DTO로 변환
    private StepsRecordDTO convertToDTO(StepsRecord stepsRecord) {
        StepsRecordDTO dto = new StepsRecordDTO();
        dto.setId(stepsRecord.getId());
        dto.setUserId(stepsRecord.getUserId());
        dto.setRecordDate(stepsRecord.getRecordDate());
        dto.setTotalSteps(stepsRecord.getTotalSteps());
        dto.setDistanceKm(stepsRecord.getDistanceKm());
        dto.setCaloriesBurned(stepsRecord.getCaloriesBurned());
        dto.setDailyGoal(stepsRecord.getDailyGoal());
        dto.setSourceType(convertSourceTypeToDTO(stepsRecord.getSourceType()));
        dto.setSourceId(stepsRecord.getSourceId());
        dto.setActiveMinutes(stepsRecord.getActiveMinutes());
        dto.setFlightsClimbed(stepsRecord.getFlightsClimbed());
        dto.setAvgHeartRate(stepsRecord.getAvgHeartRate());
        dto.setMaxHeartRate(stepsRecord.getMaxHeartRate());
        dto.setSyncStatus(convertSyncStatusToDTO(stepsRecord.getSyncStatus()));
        dto.setLastSyncTime(stepsRecord.getLastSyncTime());
        dto.setSyncErrorMessage(stepsRecord.getSyncErrorMessage());
        dto.setCreatedAt(stepsRecord.getCreatedAt());
        dto.setUpdatedAt(stepsRecord.getUpdatedAt());
        
        // 시간별 데이터 추가
        List<HourlySteps> hourlySteps = hourlyStepsRepository.findByStepsRecordIdOrderByHourAsc(stepsRecord.getId());
        List<HourlyStepsDTO> hourlyStepsDTOs = hourlySteps.stream()
            .map(this::convertHourlyStepsToDTO)
            .collect(Collectors.toList());
        dto.setHourlySteps(hourlyStepsDTOs);
        
        return dto;
    }
    
    // HourlySteps를 DTO로 변환
    private HourlyStepsDTO convertHourlyStepsToDTO(HourlySteps hourlySteps) {
        HourlyStepsDTO dto = new HourlyStepsDTO();
        dto.setId(hourlySteps.getId());
        dto.setStepsRecordId(hourlySteps.getStepsRecordId());
        dto.setHour(hourlySteps.getHour());
        dto.setSteps(hourlySteps.getSteps());
        dto.setDistanceKm(hourlySteps.getDistanceKm());
        dto.setCaloriesBurned(hourlySteps.getCaloriesBurned());
        dto.setActiveMinutes(hourlySteps.getActiveMinutes());
        dto.setCreatedAt(hourlySteps.getCreatedAt());
        dto.setUpdatedAt(hourlySteps.getUpdatedAt());
        return dto;
    }
    
    // SourceType 변환 메서드들
    private StepsRecord.SourceType convertSourceType(StepsRecordDTO.SourceType dtoSourceType) {
        switch (dtoSourceType) {
            case MANUAL: return StepsRecord.SourceType.MANUAL;
            case APPLE_HEALTH: return StepsRecord.SourceType.APPLE_HEALTH;
            case SAMSUNG_HEALTH: return StepsRecord.SourceType.SAMSUNG_HEALTH;
            case GOOGLE_FIT: return StepsRecord.SourceType.GOOGLE_FIT;
            case OTHER: return StepsRecord.SourceType.OTHER;
            default: return StepsRecord.SourceType.MANUAL;
        }
    }
    
    private StepsRecordDTO.SourceType convertSourceTypeToDTO(StepsRecord.SourceType entitySourceType) {
        switch (entitySourceType) {
            case MANUAL: return StepsRecordDTO.SourceType.MANUAL;
            case APPLE_HEALTH: return StepsRecordDTO.SourceType.APPLE_HEALTH;
            case SAMSUNG_HEALTH: return StepsRecordDTO.SourceType.SAMSUNG_HEALTH;
            case GOOGLE_FIT: return StepsRecordDTO.SourceType.GOOGLE_FIT;
            case OTHER: return StepsRecordDTO.SourceType.OTHER;
            default: return StepsRecordDTO.SourceType.MANUAL;
        }
    }
    
    private StepsRecord.SyncStatus convertSyncStatus(StepsRecordDTO.SyncStatus dtoSyncStatus) {
        switch (dtoSyncStatus) {
            case NOT_SYNCED: return StepsRecord.SyncStatus.NOT_SYNCED;
            case SYNCED: return StepsRecord.SyncStatus.SYNCED;
            case SYNC_FAILED: return StepsRecord.SyncStatus.SYNC_FAILED;
            case SYNCING: return StepsRecord.SyncStatus.SYNCING;
            default: return StepsRecord.SyncStatus.NOT_SYNCED;
        }
    }
    
    private StepsRecordDTO.SyncStatus convertSyncStatusToDTO(StepsRecord.SyncStatus entitySyncStatus) {
        switch (entitySyncStatus) {
            case NOT_SYNCED: return StepsRecordDTO.SyncStatus.NOT_SYNCED;
            case SYNCED: return StepsRecordDTO.SyncStatus.SYNCED;
            case SYNC_FAILED: return StepsRecordDTO.SyncStatus.SYNC_FAILED;
            case SYNCING: return StepsRecordDTO.SyncStatus.SYNCING;
            default: return StepsRecordDTO.SyncStatus.NOT_SYNCED;
        }
    }
}
