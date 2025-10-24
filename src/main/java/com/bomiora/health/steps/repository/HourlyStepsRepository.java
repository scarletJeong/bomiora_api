package com.bomiora.health.steps.repository;

import com.bomiora.health.steps.entity.HourlySteps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HourlyStepsRepository extends JpaRepository<HourlySteps, Long> {
    
    // 특정 걸음수 기록의 시간별 데이터 조회
    List<HourlySteps> findByStepsRecordIdOrderByHourAsc(Long stepsRecordId);
    
    // 특정 걸음수 기록의 특정 시간 데이터 조회
    HourlySteps findByStepsRecordIdAndHour(Long stepsRecordId, Integer hour);
    
    // 특정 걸음수 기록의 시간별 데이터 삭제
    void deleteByStepsRecordId(Long stepsRecordId);
    
    // 사용자별 특정 날짜의 시간별 데이터 조회
    @Query("SELECT h FROM HourlySteps h " +
           "JOIN h.stepsRecord s " +
           "WHERE s.userId = :userId AND s.recordDate = :recordDate " +
           "ORDER BY h.hour ASC")
    List<HourlySteps> findByUserIdAndDate(@Param("userId") Long userId, 
                                        @Param("recordDate") java.time.LocalDate recordDate);
    
    // 특정 시간대의 걸음수 합계 조회
    @Query("SELECT SUM(h.steps) FROM HourlySteps h " +
           "JOIN h.stepsRecord s " +
           "WHERE s.userId = :userId AND h.hour BETWEEN :startHour AND :endHour")
    Integer sumStepsByHourRange(@Param("userId") Long userId, 
                              @Param("startHour") Integer startHour, 
                              @Param("endHour") Integer endHour);
    
    // 사용자별 시간대별 평균 걸음수 조회
    @Query("SELECT h.hour, AVG(h.steps) FROM HourlySteps h " +
           "JOIN h.stepsRecord s " +
           "WHERE s.userId = :userId " +
           "GROUP BY h.hour " +
           "ORDER BY h.hour ASC")
    List<Object[]> findAverageStepsByHour(@Param("userId") Long userId);
}
