package com.bomiora.user.questionnaire.service;

import com.bomiora.user.questionnaire.dto.HealthProfileDto;
import com.bomiora.user.questionnaire.dto.HealthProfileRequestDto;
import com.bomiora.user.questionnaire.entity.HealthProfile;
import com.bomiora.user.questionnaire.repository.HealthProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class HealthProfileService {
    
    private final HealthProfileRepository healthProfileRepository;
    
    // 생성자 주입
    public HealthProfileService(HealthProfileRepository healthProfileRepository) {
        this.healthProfileRepository = healthProfileRepository;
    }
    
    /**
     * 사용자 문진표 조회
     * @param mbId 사용자 ID
     * @return 문진표 정보
     */
    public Optional<HealthProfileDto> getHealthProfile(String mbId) {
        System.out.println("문진표 조회 요청 - 사용자 ID: " + mbId);
        
        Optional<HealthProfile> healthProfile = healthProfileRepository.findByMbId(mbId);
        
        if (healthProfile.isPresent()) {
            HealthProfileDto dto = convertToDto(healthProfile.get());
            System.out.println("문진표 조회 성공 - 사용자 ID: " + mbId + ", 문진표 번호: " + dto.getPfNo());
            return Optional.of(dto);
        } else {
            System.out.println("문진표 없음 - 사용자 ID: " + mbId);
            return Optional.empty();
        }
    }
    
    /**
     * 문진표 저장
     * @param requestDto 문진표 요청 데이터
     * @return 저장된 문진표 정보
     */
    @Transactional
    public HealthProfileDto saveHealthProfile(HealthProfileRequestDto requestDto) {
        System.out.println("문진표 저장 요청 - 사용자 ID: " + requestDto.getMbId());
        
        // 기존 문진표가 있는지 확인
        Optional<HealthProfile> existingProfile = healthProfileRepository.findByMbId(requestDto.getMbId());
        
        HealthProfile healthProfile;
        if (existingProfile.isPresent()) {
            // 기존 문진표 업데이트
            healthProfile = existingProfile.get();
            updateHealthProfileFields(healthProfile, requestDto);
            System.out.println("기존 문진표 업데이트 - 사용자 ID: " + requestDto.getMbId() + 
                    ", 문진표 번호: " + healthProfile.getPfNo());
        } else {
            // 새 문진표 생성
            healthProfile = createNewHealthProfile(requestDto);
            System.out.println("새 문진표 생성 - 사용자 ID: " + requestDto.getMbId());
        }
        
        HealthProfile savedProfile = healthProfileRepository.save(healthProfile);
        HealthProfileDto result = convertToDto(savedProfile);
        
        System.out.println("문진표 저장 완료 - 사용자 ID: " + requestDto.getMbId() + 
                ", 문진표 번호: " + result.getPfNo());
        
        return result;
    }
    
    /**
     * 문진표 수정
     * @param pfNo 문진표 번호
     * @param mbId 사용자 ID
     * @param requestDto 수정할 문진표 데이터
     * @return 수정된 문진표 정보
     */
    @Transactional
    public HealthProfileDto updateHealthProfile(Long pfNo, String mbId, HealthProfileRequestDto requestDto) {
        System.out.println("문진표 수정 요청 - 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId);
        
        Optional<HealthProfile> existingProfile = healthProfileRepository.findByPfNoAndMbId(pfNo, mbId);
        
        if (existingProfile.isEmpty()) {
            throw new IllegalArgumentException("문진표를 찾을 수 없습니다. 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId);
        }
        
        HealthProfile healthProfile = existingProfile.get();
        updateHealthProfileFields(healthProfile, requestDto);
        
        HealthProfile savedProfile = healthProfileRepository.save(healthProfile);
        HealthProfileDto result = convertToDto(savedProfile);
        
        System.out.println("문진표 수정 완료 - 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId);
        
        return result;
    }
    
    /**
     * 문진표 삭제
     * @param pfNo 문진표 번호
     * @param mbId 사용자 ID
     */
    @Transactional
    public void deleteHealthProfile(Long pfNo, String mbId) {
        System.out.println("문진표 삭제 요청 - 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId);
        
        Optional<HealthProfile> existingProfile = healthProfileRepository.findByPfNoAndMbId(pfNo, mbId);
        
        if (existingProfile.isEmpty()) {
            throw new IllegalArgumentException("문진표를 찾을 수 없습니다. 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId);
        }
        
        healthProfileRepository.delete(existingProfile.get());
        
        System.out.println("문진표 삭제 완료 - 문진표 번호: " + pfNo + ", 사용자 ID: " + mbId);
    }
    
    /**
     * 문진표 존재 여부 확인
     * @param mbId 사용자 ID
     * @return 존재 여부
     */
    public boolean hasHealthProfile(String mbId) {
        return healthProfileRepository.existsByMbId(mbId);
    }
    
    /**
     * 새 문진표 생성
     */
    private HealthProfile createNewHealthProfile(HealthProfileRequestDto requestDto) {
        HealthProfile healthProfile = new HealthProfile();
        healthProfile.setMbId(requestDto.getMbId());
        healthProfile.setAnswer1(requestDto.getAnswer1());
        healthProfile.setAnswer2(requestDto.getAnswer2());
        healthProfile.setAnswer3(requestDto.getAnswer3());
        healthProfile.setAnswer4(requestDto.getAnswer4());
        healthProfile.setAnswer5(requestDto.getAnswer5());
        healthProfile.setAnswer6(requestDto.getAnswer6());
        healthProfile.setAnswer7(requestDto.getAnswer7());
        healthProfile.setAnswer8(requestDto.getAnswer8());
        healthProfile.setAnswer9(requestDto.getAnswer9());
        healthProfile.setAnswer10(requestDto.getAnswer10());
        healthProfile.setAnswer11(requestDto.getAnswer11());
        healthProfile.setAnswer12(requestDto.getAnswer12());
        healthProfile.setAnswer13(requestDto.getAnswer13());
        healthProfile.setAnswer13Period(requestDto.getAnswer13Period());
        healthProfile.setAnswer13Dosage(requestDto.getAnswer13Dosage());
        healthProfile.setAnswer13Medicine(requestDto.getAnswer13Medicine());
        healthProfile.setAnswer71(requestDto.getAnswer71());
        healthProfile.setAnswer13Sideeffect(requestDto.getAnswer13Sideeffect());
        healthProfile.setPfIp(getClientIp()); // 실제 구현에서는 HttpServletRequest에서 가져와야 함
        healthProfile.setPfMemo(requestDto.getPfMemo());
        return healthProfile;
    }
    
    /**
     * 기존 문진표 필드 업데이트
     */
    private void updateHealthProfileFields(HealthProfile healthProfile, HealthProfileRequestDto requestDto) {
        healthProfile.setAnswer1(requestDto.getAnswer1());
        healthProfile.setAnswer2(requestDto.getAnswer2());
        healthProfile.setAnswer3(requestDto.getAnswer3());
        healthProfile.setAnswer4(requestDto.getAnswer4());
        healthProfile.setAnswer5(requestDto.getAnswer5());
        healthProfile.setAnswer6(requestDto.getAnswer6());
        healthProfile.setAnswer7(requestDto.getAnswer7());
        healthProfile.setAnswer8(requestDto.getAnswer8());
        healthProfile.setAnswer9(requestDto.getAnswer9());
        healthProfile.setAnswer10(requestDto.getAnswer10());
        healthProfile.setAnswer11(requestDto.getAnswer11());
        healthProfile.setAnswer12(requestDto.getAnswer12());
        healthProfile.setAnswer13(requestDto.getAnswer13());
        healthProfile.setAnswer13Period(requestDto.getAnswer13Period());
        healthProfile.setAnswer13Dosage(requestDto.getAnswer13Dosage());
        healthProfile.setAnswer13Medicine(requestDto.getAnswer13Medicine());
        healthProfile.setAnswer71(requestDto.getAnswer71());
        healthProfile.setAnswer13Sideeffect(requestDto.getAnswer13Sideeffect());
        healthProfile.setPfMemo(requestDto.getPfMemo());
    }
    
    /**
     * Entity를 DTO로 변환
     */
    private HealthProfileDto convertToDto(HealthProfile healthProfile) {
        HealthProfileDto dto = new HealthProfileDto();
        dto.setPfNo(healthProfile.getPfNo());
        dto.setMbId(healthProfile.getMbId());
        dto.setAnswer1(healthProfile.getAnswer1());
        dto.setAnswer2(healthProfile.getAnswer2());
        dto.setAnswer3(healthProfile.getAnswer3());
        dto.setAnswer4(healthProfile.getAnswer4());
        dto.setAnswer5(healthProfile.getAnswer5());
        dto.setAnswer6(healthProfile.getAnswer6());
        dto.setAnswer7(healthProfile.getAnswer7());
        dto.setAnswer8(healthProfile.getAnswer8());
        dto.setAnswer9(healthProfile.getAnswer9());
        dto.setAnswer10(healthProfile.getAnswer10());
        dto.setAnswer11(healthProfile.getAnswer11());
        dto.setAnswer12(healthProfile.getAnswer12());
        dto.setAnswer13(healthProfile.getAnswer13());
        dto.setAnswer13Period(healthProfile.getAnswer13Period());
        dto.setAnswer13Dosage(healthProfile.getAnswer13Dosage());
        dto.setAnswer13Medicine(healthProfile.getAnswer13Medicine());
        dto.setAnswer71(healthProfile.getAnswer71());
        dto.setAnswer13Sideeffect(healthProfile.getAnswer13Sideeffect());
        dto.setPfWdatetime(healthProfile.getPfWdatetime());
        dto.setPfMdatetime(healthProfile.getPfMdatetime());
        dto.setPfIp(healthProfile.getPfIp());
        dto.setPfMemo(healthProfile.getPfMemo());
        return dto;
    }
    
    /**
     * 클라이언트 IP 주소 가져오기 (임시 구현)
     */
    private String getClientIp() {
        // 실제 구현에서는 HttpServletRequest에서 가져와야 함
        return "127.0.0.1";
    }
}
