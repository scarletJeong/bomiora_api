package com.bomiora.user.point.service;

import com.bomiora.user.point.entity.Point;
import com.bomiora.user.point.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointService {
    
    @Autowired
    private PointRepository pointRepository;
    
    /**
     * 사용자의 현재 보유 포인트 조회
     * bomiora_point 테이블에서 mb_id에 해당하는 가장 최근의 po_mb_point 값을 반환
     * 
     * @param userId 사용자 ID (mb_id)
     * @return 현재 보유 포인트, 없으면 0
     */
    public Integer getUserPoint(String userId) {
        try {
            System.out.println("💎 포인트 조회 시작 - userId: " + userId);
            
            // 직접 po_mb_point 값만 조회 (가장 효율적)
            Optional<Integer> latestMbPoint = pointRepository.findLatestMbPointByUserId(userId);
            
            if (latestMbPoint.isPresent()) {
                Integer point = latestMbPoint.get();
                System.out.println("✅ 포인트 조회 완료: " + point);
                return point != null ? point : 0;
            }
            
            System.out.println("⚠️ 포인트 기록 없음 - userId: " + userId);
            return 0;
        } catch (Exception e) {
            System.out.println("❌ 포인트 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 사용자의 포인트 내역 조회
     * 
     * @param userId 사용자 ID (mb_id)
     * @return 포인트 내역 리스트 (최신순)
     */
    public List<Point> getPointHistory(String userId) {
        try {
            System.out.println("📋 포인트 내역 조회 시작 - userId: " + userId);
            
            List<Point> history = pointRepository.findByUserIdOrderByDatetimeDesc(userId);
            
            System.out.println("✅ 포인트 내역 조회 완료: " + history.size() + "개");
            return history;
        } catch (Exception e) {
            System.out.println("❌ 포인트 내역 조회 오류: " + e.getMessage());
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
}
