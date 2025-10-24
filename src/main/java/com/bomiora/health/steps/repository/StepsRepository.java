package com.bomiora.health.steps.repository;

import com.bomiora.health.steps.entity.StepsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StepsRepository extends JpaRepository<StepsRecord, Long> {
    
    // 사용자별 특정 날짜의 걸음수 기록 조회
    Optional<StepsRecord> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
    
    // 사용자별 날짜 범위의 걸음수 기록 조회
    List<StepsRecord> findByUserIdAndRecordDateBetweenOrderByRecordDateDesc(
        Long userId, LocalDate startDate, LocalDate endDate);
    
    // 사용자별 최신 걸음수 기록 조회
    Optional<StepsRecord> findTopByUserIdOrderByRecordDateDesc(Long userId);
    
    // 사용자별 주간 걸음수 기록 조회
    @Query("SELECT s FROM StepsRecord s WHERE s.userId = :userId " +
           "AND s.recordDate BETWEEN :startDate AND :endDate " +
           "ORDER BY s.recordDate ASC")
    List<StepsRecord> findWeeklySteps(@Param("userId") Long userId, 
                                    @Param("startDate") LocalDate startDate, 
                                    @Param("endDate") LocalDate endDate);
    
    // 사용자별 월간 걸음수 기록 조회
    @Query("SELECT s FROM StepsRecord s WHERE s.userId = :userId " +
           "AND YEAR(s.recordDate) = :year AND MONTH(s.recordDate) = :month " +
           "ORDER BY s.recordDate ASC")
    List<StepsRecord> findMonthlySteps(@Param("userId") Long userId, 
                                     @Param("year") int year, 
                                     @Param("month") int month);
    
    // 사용자별 연동 상태별 기록 조회
    List<StepsRecord> findByUserIdAndSyncStatusOrderByRecordDateDesc(
        Long userId, StepsRecord.SyncStatus syncStatus);
    
    // 사용자별 소스 타입별 기록 조회
    List<StepsRecord> findByUserIdAndSourceTypeOrderByRecordDateDesc(
        Long userId, StepsRecord.SourceType sourceType);
    
    // 동기화가 필요한 기록 조회 (연동 앱용)
    @Query("SELECT s FROM StepsRecord s WHERE s.userId = :userId " +
           "AND s.syncStatus = 'NOT_SYNCED' " +
           "AND s.sourceType != 'MANUAL' " +
           "ORDER BY s.recordDate DESC")
    List<StepsRecord> findUnsyncedRecords(@Param("userId") Long userId);
    
    // 사용자별 걸음수 통계 조회
    @Query("SELECT " +
           "AVG(s.totalSteps) as avgSteps, " +
           "MAX(s.totalSteps) as maxSteps, " +
           "MIN(s.totalSteps) as minSteps, " +
           "SUM(s.totalSteps) as totalSteps " +
           "FROM StepsRecord s WHERE s.userId = :userId " +
           "AND s.recordDate BETWEEN :startDate AND :endDate")
    Object[] findStepsStatistics(@Param("userId") Long userId, 
                                @Param("startDate") LocalDate startDate, 
                                @Param("endDate") LocalDate endDate);
    
    // 사용자별 목표 달성률 조회
    @Query("SELECT COUNT(s) FROM StepsRecord s WHERE s.userId = :userId " +
           "AND s.recordDate BETWEEN :startDate AND :endDate " +
           "AND s.totalSteps >= s.dailyGoal")
    Long countGoalAchieved(@Param("userId") Long userId, 
                          @Param("startDate") LocalDate startDate, 
                          @Param("endDate") LocalDate endDate);
    
    // 사용자별 연속 목표 달성 일수 조회
    @Query("SELECT s FROM StepsRecord s WHERE s.userId = :userId " +
           "AND s.recordDate <= :endDate " +
           "ORDER BY s.recordDate DESC")
    List<StepsRecord> findConsecutiveGoalDays(@Param("userId") Long userId, 
                                            @Param("endDate") LocalDate endDate);
    
    // 특정 날짜 이전의 기록이 존재하는지 확인
    boolean existsByUserIdAndRecordDateBefore(Long userId, LocalDate recordDate);
    
    // 사용자별 기록 개수 조회
    long countByUserId(Long userId);
    
    // 사용자별 특정 기간의 기록 개수 조회
    long countByUserIdAndRecordDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
