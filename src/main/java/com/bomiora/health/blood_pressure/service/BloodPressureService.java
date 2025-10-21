package com.bomiora.health.blood_pressure.service;

import com.bomiora.health.blood_pressure.dto.BloodPressureDTO;
import com.bomiora.health.blood_pressure.entity.BloodPressure;
import com.bomiora.health.blood_pressure.repository.BloodPressureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BloodPressureService {
    
    @Autowired
    private BloodPressureRepository bloodPressureRepository;
    
    /**
     * 혈압 기록 추가
     */
    @Transactional
    public BloodPressureDTO addRecord(BloodPressureDTO dto) {
        System.out.println("혈압 기록 추가 - 사용자: " + dto.getMbId() + 
                          ", 수축기: " + dto.getSystolic() + 
                          ", 이완기: " + dto.getDiastolic() + 
                          ", 심박수: " + dto.getPulse());
        
        BloodPressure entity = dto.toEntity();
        BloodPressure savedEntity = bloodPressureRepository.save(entity);
        
        System.out.println("혈압 기록 추가 완료 - ID: " + savedEntity.getId() + 
                          ", 상태: " + savedEntity.getStatus());
        return BloodPressureDTO.fromEntity(savedEntity);
    }
    
    /**
     * 혈압 기록 수정
     */
    @Transactional
    public BloodPressureDTO updateRecord(Long id, BloodPressureDTO dto) {
        System.out.println("혈압 기록 수정 - ID: " + id);
        
        BloodPressure entity = bloodPressureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("혈압 기록을 찾을 수 없습니다. ID: " + id));
        
        // 수정 가능한 필드만 업데이트
        entity.setSystolic(dto.getSystolic());
        entity.setDiastolic(dto.getDiastolic());
        entity.setPulse(dto.getPulse());
        entity.setMeasuredAt(dto.getMeasuredAt());
        entity.setStatus(null); // null로 설정하면 @PreUpdate에서 자동 계산
        
        BloodPressure updatedEntity = bloodPressureRepository.save(entity);
        
        System.out.println("혈압 기록 수정 완료 - ID: " + updatedEntity.getId() + 
                          ", 상태: " + updatedEntity.getStatus());
        return BloodPressureDTO.fromEntity(updatedEntity);
    }
    
    /**
     * 혈압 기록 삭제
     */
    @Transactional
    public void deleteRecord(Long id) {
        System.out.println("혈압 기록 삭제 - ID: " + id);
        
        if (!bloodPressureRepository.existsById(id)) {
            throw new RuntimeException("혈압 기록을 찾을 수 없습니다. ID: " + id);
        }
        
        bloodPressureRepository.deleteById(id);
        System.out.println("혈압 기록 삭제 완료 - ID: " + id);
    }
    
    /**
     * 사용자의 모든 혈압 기록 조회
     */
    public List<BloodPressureDTO> getRecords(String mbId) {
        System.out.println("혈압 기록 조회 - 사용자: " + mbId);
        
        List<BloodPressure> records = bloodPressureRepository.findByMbIdOrderByMeasuredAtDesc(mbId);
        
        System.out.println("혈압 기록 조회 완료 - 사용자: " + mbId + ", 개수: " + records.size());
        return records.stream()
                .map(BloodPressureDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 최신 혈압 기록 조회
     */
    public BloodPressureDTO getLatestRecord(String mbId) {
        System.out.println("최신 혈압 기록 조회 - 사용자: " + mbId);
        
        return bloodPressureRepository.findFirstByMbIdOrderByMeasuredAtDesc(mbId)
                .map(BloodPressureDTO::fromEntity)
                .orElse(null);
    }
    
    /**
     * 날짜 범위로 혈압 기록 조회
     */
    public List<BloodPressureDTO> getRecordsByDateRange(String mbId, LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("날짜 범위 혈압 기록 조회 - 사용자: " + mbId + 
                          ", 시작: " + startDate + ", 종료: " + endDate);
        
        List<BloodPressure> records = bloodPressureRepository.findByMbIdAndMeasuredAtBetween(mbId, startDate, endDate);
        
        System.out.println("날짜 범위 혈압 기록 조회 완료 - 개수: " + records.size());
        return records.stream()
                .map(BloodPressureDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 날짜의 혈압 기록 조회
     */
    public List<BloodPressureDTO> getRecordsByDate(String mbId, LocalDateTime date) {
        System.out.println("특정 날짜 혈압 기록 조회 - 사용자: " + mbId + ", 날짜: " + date);
        
        List<BloodPressure> records = bloodPressureRepository.findByMbIdAndDate(mbId, date);
        
        System.out.println("특정 날짜 혈압 기록 조회 완료 - 개수: " + records.size());
        return records.stream()
                .map(BloodPressureDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 사용자의 혈압 기록 개수 조회
     */
    public long getRecordCount(String mbId) {
        long count = bloodPressureRepository.countByMbId(mbId);
        System.out.println("혈압 기록 개수 조회 - 사용자: " + mbId + ", 개수: " + count);
        return count;
    }
}

