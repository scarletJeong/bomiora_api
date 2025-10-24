package com.bomiora.health.menstrual_cycle.repository;

import com.bomiora.health.menstrual_cycle.entity.MenstrualCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenstrualCycleRepository extends JpaRepository<MenstrualCycle, Long> {
    
    /**
     * 사용자의 모든 생리주기 기록 조회 (최신순)
     */
    List<MenstrualCycle> findByMbIdOrderByCreatedAtDesc(String mbId);
    
    /**
     * 사용자의 최신 생리주기 기록 조회
     */
    Optional<MenstrualCycle> findFirstByMbIdOrderByCreatedAtDesc(String mbId);
    
    /**
     * 사용자의 생리주기 기록 개수 조회
     */
    long countByMbId(String mbId);
    
    /**
     * 특정 기간 내 사용자의 생리주기 기록 조회
     */
    @Query("SELECT mc FROM MenstrualCycle mc WHERE mc.mbId = :mbId " +
           "AND mc.lastPeriodStart BETWEEN :startDate AND :endDate " +
           "ORDER BY mc.lastPeriodStart DESC")
    List<MenstrualCycle> findByMbIdAndLastPeriodStartBetween(
        @Param("mbId") String mbId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    /**
     * 사용자의 평균 생리주기 길이 계산
     */
    @Query("SELECT AVG(mc.cycleLength) FROM MenstrualCycle mc WHERE mc.mbId = :mbId")
    Double findAverageCycleLengthByMbId(@Param("mbId") String mbId);
    
    /**
     * 사용자의 평균 생리 기간 길이 계산
     */
    @Query("SELECT AVG(mc.periodLength) FROM MenstrualCycle mc WHERE mc.mbId = :mbId")
    Double findAveragePeriodLengthByMbId(@Param("mbId") String mbId);
    
    /**
     * 사용자의 최근 6개월 생리주기 기록 조회
     */
    @Query("SELECT mc FROM MenstrualCycle mc WHERE mc.mbId = :mbId " +
           "AND mc.lastPeriodStart >= :sixMonthsAgo " +
           "ORDER BY mc.lastPeriodStart DESC")
    List<MenstrualCycle> findRecentSixMonthsByMbId(
        @Param("mbId") String mbId,
        @Param("sixMonthsAgo") LocalDate sixMonthsAgo
    );
}
