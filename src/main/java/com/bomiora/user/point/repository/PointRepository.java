package com.bomiora.user.point.repository;

import com.bomiora.user.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    
    /**
     * 사용자의 가장 최근 포인트 기록 조회 (최신 po_mb_point 값)
     * po_datetime 기준으로 내림차순 정렬하여 가장 최근 값 조회
     */
    @Query(value = "SELECT * FROM bomiora_point WHERE mb_id = :userId ORDER BY po_datetime DESC, po_id DESC LIMIT 1", nativeQuery = true)
    Optional<Point> findTopByUserIdOrderByDatetimeDescIdDesc(@Param("userId") String userId);
    
    /**
     * 사용자의 최신 포인트 값만 조회 (간단한 버전)
     */
    @Query(value = "SELECT po_mb_point FROM bomiora_point WHERE mb_id = :userId ORDER BY po_datetime DESC, po_id DESC LIMIT 1", nativeQuery = true)
    Optional<Integer> findLatestMbPointByUserId(@Param("userId") String userId);
}
