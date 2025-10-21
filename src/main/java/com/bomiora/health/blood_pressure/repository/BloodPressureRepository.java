package com.bomiora.health.blood_pressure.repository;

import com.bomiora.health.blood_pressure.entity.BloodPressure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressure, Long> {
    
    /**
     * 사용자의 모든 혈압 기록 조회 (최신순)
     */
    List<BloodPressure> findByMbIdOrderByMeasuredAtDesc(String mbId);
    
    /**
     * 사용자의 최신 혈압 기록 조회
     */
    Optional<BloodPressure> findFirstByMbIdOrderByMeasuredAtDesc(String mbId);
    
    /**
     * 날짜 범위로 혈압 기록 조회
     */
    @Query("SELECT bp FROM BloodPressure bp WHERE bp.mbId = :mbId " +
           "AND bp.measuredAt BETWEEN :startDate AND :endDate " +
           "ORDER BY bp.measuredAt DESC")
    List<BloodPressure> findByMbIdAndMeasuredAtBetween(
        @Param("mbId") String mbId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    /**
     * 특정 날짜의 혈압 기록 조회
     */
    @Query("SELECT bp FROM BloodPressure bp WHERE bp.mbId = :mbId " +
           "AND DATE(bp.measuredAt) = DATE(:date) " +
           "ORDER BY bp.measuredAt DESC")
    List<BloodPressure> findByMbIdAndDate(
        @Param("mbId") String mbId,
        @Param("date") LocalDateTime date
    );
    
    /**
     * 사용자의 혈압 기록 개수 조회
     */
    long countByMbId(String mbId);
}

