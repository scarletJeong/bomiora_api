package com.bomiora.health.weight.repository;

import com.bomiora.health.weight.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {

    // 특정 회원의 모든 체중 기록 조회 (최신순)
    List<Weight> findByMbIdOrderByMeasuredAtDesc(String mbId);

    // 특정 회원의 최신 체중 기록 조회
    Optional<Weight> findFirstByMbIdOrderByMeasuredAtDesc(String mbId);

    // 특정 날짜 범위의 체중 기록 조회
    @Query("SELECT w FROM Weight w WHERE w.mbId = :mbId " +
           "AND w.measuredAt >= :startDate AND w.measuredAt <= :endDate " +
           "ORDER BY w.measuredAt DESC")
    List<Weight> findByMbIdAndDateRange(
        @Param("mbId") String mbId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    // 특정 회원의 체중 기록 개수
    long countByMbId(String mbId);
}
