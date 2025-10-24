package com.bomiora.health.blood_sugar.repository;

import com.bomiora.health.blood_sugar.entity.BloodSugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodSugarRepository extends JpaRepository<BloodSugar, Long> {
    
    /**
     * 사용자의 모든 혈당 기록 조회 (최신순)
     */
    List<BloodSugar> findByMbIdOrderByMeasuredAtDesc(String mbId);
    
    /**
     * 사용자의 최신 혈당 기록 조회
     */
    Optional<BloodSugar> findFirstByMbIdOrderByMeasuredAtDesc(String mbId);
    
    /**
     * 날짜 범위로 혈당 기록 조회
     */
    @Query("SELECT bs FROM BloodSugar bs WHERE bs.mbId = :mbId " +
           "AND bs.measuredAt BETWEEN :startDate AND :endDate " +
           "ORDER BY bs.measuredAt DESC")
    List<BloodSugar> findByMbIdAndMeasuredAtBetween(
        @Param("mbId") String mbId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    /**
     * 특정 날짜의 혈당 기록 조회
     */
    @Query("SELECT bs FROM BloodSugar bs WHERE bs.mbId = :mbId " +
           "AND DATE(bs.measuredAt) = DATE(:date) " +
           "ORDER BY bs.measuredAt DESC")
    List<BloodSugar> findByMbIdAndDate(
        @Param("mbId") String mbId,
        @Param("date") LocalDateTime date
    );
    
    /**
     * 측정 유형별 혈당 기록 조회
     */
    List<BloodSugar> findByMbIdAndMeasurementTypeOrderByMeasuredAtDesc(
        String mbId, String measurementType
    );
    
    /**
     * 사용자의 혈당 기록 개수 조회
     */
    long countByMbId(String mbId);
    
    /**
     * 특정 기간 내 사용자의 혈당 기록 개수 조회
     */
    @Query("SELECT COUNT(bs) FROM BloodSugar bs WHERE bs.mbId = :mbId " +
           "AND bs.measuredAt BETWEEN :startDate AND :endDate")
    long countByMbIdAndMeasuredAtBetween(
        @Param("mbId") String mbId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    /**
     * 사용자의 혈당 상태별 기록 조회
     */
    List<BloodSugar> findByMbIdAndStatusOrderByMeasuredAtDesc(
        String mbId, String status
    );
}
