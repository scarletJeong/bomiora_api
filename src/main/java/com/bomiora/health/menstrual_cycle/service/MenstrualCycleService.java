package com.bomiora.health.menstrual_cycle.service;

import com.bomiora.health.menstrual_cycle.dto.MenstrualCycleDTO;
import com.bomiora.health.menstrual_cycle.entity.MenstrualCycle;
import com.bomiora.health.menstrual_cycle.repository.MenstrualCycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MenstrualCycleService {
    
    @Autowired
    private MenstrualCycleRepository menstrualCycleRepository;
    
    /**
     * 생리주기 기록 추가
     */
    @Transactional
    public MenstrualCycleDTO addRecord(MenstrualCycleDTO dto) {
        System.out.println("생리주기 기록 추가 - 사용자: " + dto.getMbId() + 
                          ", 마지막 생리 시작일: " + dto.getLastPeriodStart() + 
                          ", 생리주기 길이: " + dto.getCycleLength() + 
                          ", 생리 기간: " + dto.getPeriodLength());
        
        MenstrualCycle entity = dto.toEntity();
        MenstrualCycle savedEntity = menstrualCycleRepository.save(entity);
        
        System.out.println("생리주기 기록 추가 완료 - ID: " + savedEntity.getId());
        return MenstrualCycleDTO.fromEntity(savedEntity);
    }
    
    /**
     * 생리주기 기록 수정
     */
    @Transactional
    public MenstrualCycleDTO updateRecord(Long id, MenstrualCycleDTO dto) {
        System.out.println("생리주기 기록 수정 - ID: " + id);
        
        MenstrualCycle entity = menstrualCycleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("생리주기 기록을 찾을 수 없습니다. ID: " + id));
        
        // 수정 가능한 필드만 업데이트
        entity.setLastPeriodStart(dto.getLastPeriodStart());
        entity.setCycleLength(dto.getCycleLength());
        entity.setPeriodLength(dto.getPeriodLength());
        
        MenstrualCycle updatedEntity = menstrualCycleRepository.save(entity);
        
        System.out.println("생리주기 기록 수정 완료 - ID: " + updatedEntity.getId());
        return MenstrualCycleDTO.fromEntity(updatedEntity);
    }
    
    /**
     * 생리주기 기록 삭제
     */
    @Transactional
    public void deleteRecord(Long id) {
        System.out.println("생리주기 기록 삭제 - ID: " + id);
        
        if (!menstrualCycleRepository.existsById(id)) {
            throw new RuntimeException("생리주기 기록을 찾을 수 없습니다. ID: " + id);
        }
        
        menstrualCycleRepository.deleteById(id);
        System.out.println("생리주기 기록 삭제 완료 - ID: " + id);
    }
    
    /**
     * 사용자의 모든 생리주기 기록 조회
     */
    public List<MenstrualCycleDTO> getRecords(String mbId) {
        System.out.println("생리주기 기록 조회 - 사용자: " + mbId);
        
        List<MenstrualCycle> records = menstrualCycleRepository.findByMbIdOrderByCreatedAtDesc(mbId);
        
        System.out.println("생리주기 기록 조회 완료 - 사용자: " + mbId + ", 개수: " + records.size());
        return records.stream()
                .map(MenstrualCycleDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 최신 생리주기 기록 조회
     */
    public MenstrualCycleDTO getLatestRecord(String mbId) {
        System.out.println("최신 생리주기 기록 조회 - 사용자: " + mbId);
        
        return menstrualCycleRepository.findFirstByMbIdOrderByCreatedAtDesc(mbId)
                .map(MenstrualCycleDTO::fromEntity)
                .orElse(null);
    }
    
    /**
     * 날짜 범위로 생리주기 기록 조회
     */
    public List<MenstrualCycleDTO> getRecordsByDateRange(String mbId, LocalDate startDate, LocalDate endDate) {
        System.out.println("날짜 범위 생리주기 기록 조회 - 사용자: " + mbId + 
                          ", 시작: " + startDate + ", 종료: " + endDate);
        
        List<MenstrualCycle> records = menstrualCycleRepository.findByMbIdAndLastPeriodStartBetween(mbId, startDate, endDate);
        
        System.out.println("날짜 범위 생리주기 기록 조회 완료 - 개수: " + records.size());
        return records.stream()
                .map(MenstrualCycleDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 사용자의 평균 생리주기 길이 조회
     */
    public Double getAverageCycleLength(String mbId) {
        System.out.println("평균 생리주기 길이 조회 - 사용자: " + mbId);
        
        Double average = menstrualCycleRepository.findAverageCycleLengthByMbId(mbId);
        
        System.out.println("평균 생리주기 길이 조회 완료 - 사용자: " + mbId + ", 평균: " + average);
        return average;
    }
    
    /**
     * 사용자의 평균 생리 기간 길이 조회
     */
    public Double getAveragePeriodLength(String mbId) {
        System.out.println("평균 생리 기간 길이 조회 - 사용자: " + mbId);
        
        Double average = menstrualCycleRepository.findAveragePeriodLengthByMbId(mbId);
        
        System.out.println("평균 생리 기간 길이 조회 완료 - 사용자: " + mbId + ", 평균: " + average);
        return average;
    }
    
    /**
     * 사용자의 최근 6개월 생리주기 기록 조회
     */
    public List<MenstrualCycleDTO> getRecentSixMonthsRecords(String mbId) {
        System.out.println("최근 6개월 생리주기 기록 조회 - 사용자: " + mbId);
        
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        List<MenstrualCycle> records = menstrualCycleRepository.findRecentSixMonthsByMbId(mbId, sixMonthsAgo);
        
        System.out.println("최근 6개월 생리주기 기록 조회 완료 - 개수: " + records.size());
        return records.stream()
                .map(MenstrualCycleDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 사용자의 생리주기 기록 개수 조회
     */
    public long getRecordCount(String mbId) {
        long count = menstrualCycleRepository.countByMbId(mbId);
        System.out.println("생리주기 기록 개수 조회 - 사용자: " + mbId + ", 개수: " + count);
        return count;
    }
}
