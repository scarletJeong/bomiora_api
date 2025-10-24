package com.bomiora.health.blood_sugar.service;

import com.bomiora.health.blood_sugar.dto.BloodSugarDTO;
import com.bomiora.health.blood_sugar.entity.BloodSugar;
import com.bomiora.health.blood_sugar.repository.BloodSugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BloodSugarService {
    
    @Autowired
    private BloodSugarRepository bloodSugarRepository;
    
    /**
     * 혈당 기록 추가
     */
    @Transactional
    public BloodSugarDTO addRecord(BloodSugarDTO dto) {
        System.out.println("혈당 기록 추가 - 사용자: " + dto.getMbId() + 
                          ", 혈당: " + dto.getBloodSugar() + 
                          ", 측정유형: " + dto.getMeasurementType());
        
        BloodSugar entity = dto.toEntity();
        BloodSugar savedEntity = bloodSugarRepository.save(entity);
        
        System.out.println("혈당 기록 추가 완료 - ID: " + savedEntity.getId() + 
                          ", 상태: " + savedEntity.getStatus());
        return BloodSugarDTO.fromEntity(savedEntity);
    }
    
    /**
     * 혈당 기록 수정
     */
    @Transactional
    public BloodSugarDTO updateRecord(Long id, BloodSugarDTO dto) {
        System.out.println("혈당 기록 수정 - ID: " + id);
        
        BloodSugar entity = bloodSugarRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("혈당 기록을 찾을 수 없습니다. ID: " + id));
        
        // 수정 가능한 필드만 업데이트
        entity.setBloodSugar(dto.getBloodSugar());
        entity.setMeasurementType(dto.getMeasurementType());
        entity.setMeasuredAt(dto.getMeasuredAt());
        entity.setStatus(null); // null로 설정하면 @PreUpdate에서 자동 계산
        
        BloodSugar updatedEntity = bloodSugarRepository.save(entity);
        
        System.out.println("혈당 기록 수정 완료 - ID: " + updatedEntity.getId() + 
                          ", 상태: " + updatedEntity.getStatus());
        return BloodSugarDTO.fromEntity(updatedEntity);
    }
    
    /**
     * 혈당 기록 삭제
     */
    @Transactional
    public void deleteRecord(Long id) {
        System.out.println("혈당 기록 삭제 - ID: " + id);
        
        if (!bloodSugarRepository.existsById(id)) {
            throw new RuntimeException("혈당 기록을 찾을 수 없습니다. ID: " + id);
        }
        
        bloodSugarRepository.deleteById(id);
        System.out.println("혈당 기록 삭제 완료 - ID: " + id);
    }
    
    /**
     * 사용자의 모든 혈당 기록 조회
     */
    public List<BloodSugarDTO> getRecords(String mbId) {
        System.out.println("혈당 기록 조회 - 사용자: " + mbId);
        
        List<BloodSugar> records = bloodSugarRepository.findByMbIdOrderByMeasuredAtDesc(mbId);
        
        System.out.println("혈당 기록 조회 완료 - 사용자: " + mbId + ", 개수: " + records.size());
        return records.stream()
                .map(BloodSugarDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 최신 혈당 기록 조회
     */
    public BloodSugarDTO getLatestRecord(String mbId) {
        System.out.println("최신 혈당 기록 조회 - 사용자: " + mbId);
        
        return bloodSugarRepository.findFirstByMbIdOrderByMeasuredAtDesc(mbId)
                .map(BloodSugarDTO::fromEntity)
                .orElse(null);
    }
    
    /**
     * 날짜 범위로 혈당 기록 조회
     */
    public List<BloodSugarDTO> getRecordsByDateRange(String mbId, LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("날짜 범위 혈당 기록 조회 - 사용자: " + mbId + 
                          ", 시작: " + startDate + ", 종료: " + endDate);
        
        List<BloodSugar> records = bloodSugarRepository.findByMbIdAndMeasuredAtBetween(mbId, startDate, endDate);
        
        System.out.println("날짜 범위 혈당 기록 조회 완료 - 개수: " + records.size());
        return records.stream()
                .map(BloodSugarDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 날짜의 혈당 기록 조회
     */
    public List<BloodSugarDTO> getRecordsByDate(String mbId, LocalDateTime date) {
        System.out.println("특정 날짜 혈당 기록 조회 - 사용자: " + mbId + ", 날짜: " + date);
        
        List<BloodSugar> records = bloodSugarRepository.findByMbIdAndDate(mbId, date);
        
        System.out.println("특정 날짜 혈당 기록 조회 완료 - 개수: " + records.size());
        return records.stream()
                .map(BloodSugarDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 측정 유형별 혈당 기록 조회
     */
    public List<BloodSugarDTO> getRecordsByMeasurementType(String mbId, String measurementType) {
        System.out.println("측정 유형별 혈당 기록 조회 - 사용자: " + mbId + ", 측정유형: " + measurementType);
        
        List<BloodSugar> records = bloodSugarRepository.findByMbIdAndMeasurementTypeOrderByMeasuredAtDesc(mbId, measurementType);
        
        System.out.println("측정 유형별 혈당 기록 조회 완료 - 개수: " + records.size());
        return records.stream()
                .map(BloodSugarDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 혈당 상태별 기록 조회
     */
    public List<BloodSugarDTO> getRecordsByStatus(String mbId, String status) {
        System.out.println("혈당 상태별 기록 조회 - 사용자: " + mbId + ", 상태: " + status);
        
        List<BloodSugar> records = bloodSugarRepository.findByMbIdAndStatusOrderByMeasuredAtDesc(mbId, status);
        
        System.out.println("혈당 상태별 기록 조회 완료 - 개수: " + records.size());
        return records.stream()
                .map(BloodSugarDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 사용자의 혈당 기록 개수 조회
     */
    public long getRecordCount(String mbId) {
        long count = bloodSugarRepository.countByMbId(mbId);
        System.out.println("혈당 기록 개수 조회 - 사용자: " + mbId + ", 개수: " + count);
        return count;
    }
    
    /**
     * 특정 기간 내 사용자의 혈당 기록 개수 조회
     */
    public long getRecordCountByDateRange(String mbId, LocalDateTime startDate, LocalDateTime endDate) {
        long count = bloodSugarRepository.countByMbIdAndMeasuredAtBetween(mbId, startDate, endDate);
        System.out.println("기간별 혈당 기록 개수 조회 - 사용자: " + mbId + ", 개수: " + count);
        return count;
    }
}
