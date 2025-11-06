package com.bomiora.shopping.cart.service;

import com.bomiora.shopping.cart.entity.HealthProfileCart;
import com.bomiora.shopping.cart.repository.HealthProfileCartRepository;
import com.bomiora.user.healthprofile.dto.HealthProfileRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class HealthProfileCartService {
    
    private final HealthProfileCartRepository healthProfileCartRepository;
    
    public HealthProfileCartService(HealthProfileCartRepository healthProfileCartRepository) {
        this.healthProfileCartRepository = healthProfileCartRepository;
    }
    
    /**
     * HealthProfileCart 저장 (bomiora_shop_health_profiles_cart에 insert)
     * @param requestDto 건강프로필 요청 데이터
     * @param odId 주문 ID
     * @param reservationDate 예약 일자
     * @param reservationTime 예약 시간 (시작 시간)
     * @param reservationEndTime 예약 종료 시간
     * @param reservationName 예약자 성함
     * @param reservationTel 예약자 연락처
     * @param doctorName 담당 한의사 이름
     * @return 저장된 HealthProfileCart
     */
    @Transactional
    public HealthProfileCart saveHealthProfileCart(
            HealthProfileRequestDto requestDto,
            Long odId,
            LocalDate reservationDate,
            String reservationTime,
            String reservationEndTime,
            String reservationName,
            String reservationTel,
            String doctorName) {
        
        System.out.println("HealthProfileCart 저장 시작 - mbId: " + requestDto.getMbId() + ", odId: " + odId);
        
        HealthProfileCart healthProfileCart = new HealthProfileCart();
        
        // 기본 정보
        healthProfileCart.setMbId(requestDto.getMbId());
        healthProfileCart.setItId(requestDto.getItId() != null ? requestDto.getItId() : "");
        healthProfileCart.setOdId(odId);
        healthProfileCart.setInfCode("");
        
        // 건강프로필 답변 정보
        healthProfileCart.setAnswer1(requestDto.getAnswer1());
        healthProfileCart.setAnswer2(requestDto.getAnswer2());
        healthProfileCart.setAnswer3(requestDto.getAnswer3());
        healthProfileCart.setAnswer4(requestDto.getAnswer4());
        healthProfileCart.setAnswer5(requestDto.getAnswer5());
        healthProfileCart.setAnswer6(requestDto.getAnswer6());
        healthProfileCart.setAnswer7(requestDto.getAnswer7());
        healthProfileCart.setAnswer8(requestDto.getAnswer8());
        healthProfileCart.setAnswer9(requestDto.getAnswer9());
        healthProfileCart.setAnswer10(requestDto.getAnswer10());
        healthProfileCart.setAnswer11(requestDto.getAnswer11());
        healthProfileCart.setAnswer12(requestDto.getAnswer12());
        healthProfileCart.setAnswer13(requestDto.getAnswer13());
        healthProfileCart.setAnswer13Period(requestDto.getAnswer13Period());
        healthProfileCart.setAnswer13Dosage(requestDto.getAnswer13Dosage());
        healthProfileCart.setAnswer13Medicine(requestDto.getAnswer13Medicine());
        healthProfileCart.setAnswer71(requestDto.getAnswer71());
        healthProfileCart.setAnswer13Sideeffect(requestDto.getAnswer13Sideeffect());
        
        // 예약 정보
        // hp_status = '쇼핑' (처방전 작성 시)
        healthProfileCart.setHpStatus("쇼핑");
        healthProfileCart.setHpDocName(doctorName != null ? doctorName : "");
        healthProfileCart.setHpRsvtDate(reservationDate);
        healthProfileCart.setHpRsvtStime(reservationTime != null ? reservationTime : "");
        healthProfileCart.setHpRsvtEtime(reservationEndTime != null ? reservationEndTime : "");
        healthProfileCart.setHpRsvtName(reservationName != null ? reservationName : "");
        healthProfileCart.setHpRsvtTel(reservationTel != null ? reservationTel : "");
        
        // 타임스탬프
        LocalDateTime now = LocalDateTime.now();
        healthProfileCart.setHpWdatetime(now);
        healthProfileCart.setHpMdatetime(now);
        healthProfileCart.setHpIp("127.0.0.1"); // 실제로는 요청에서 가져와야 함
        healthProfileCart.setHpMemo("");
        
        // 기본값 설정
        healthProfileCart.setHpOutput(HealthProfileCart.OutputType.Y);
        healthProfileCart.setHp8(HealthProfileCart.VisitType.first); // 초진
        healthProfileCart.setHp9(HealthProfileCart.PrescriptionType.prescription); // 처방
        healthProfileCart.setHp10(HealthProfileCart.StatusType.ongoing); // 진행중
        
        // hp_1 ~ hp_7 필드 설정 (null 방지)
        healthProfileCart.setHp1("");
        healthProfileCart.setHp2("");
        healthProfileCart.setHp3("");
        healthProfileCart.setHp4("");
        healthProfileCart.setHp5("");
        healthProfileCart.setHp6("");
        healthProfileCart.setHp7("");
        
        HealthProfileCart saved = healthProfileCartRepository.save(healthProfileCart);
        
        System.out.println("HealthProfileCart 저장 완료 - hpNo: " + saved.getHpNo() + ", odId: " + odId);
        
        return saved;
    }
    
    /**
     * HealthProfileCart의 od_id 업데이트 (처방전 상품 장바구니 담기 시)
     * PHP: update ... set od_id = ... where mb_id = ... and it_id = ... and hp_status = '쇼핑'
     * @param mbId 사용자 ID
     * @param itId 상품 ID
     * @param odId 주문 ID
     * @return 업데이트 성공 여부
     */
    @Transactional
    public boolean updateOdId(String mbId, String itId, Long odId) {
        System.out.println("HealthProfileCart od_id 업데이트 시작 - mbId: " + mbId + ", itId: " + itId + ", odId: " + odId);
        
        try {
            // 기존 HealthProfileCart 조회 (처방전 작성 시 이미 저장된 것)
            // 여러 결과가 있을 수 있으므로 리스트로 받아서 가장 최근 것(첫 번째)만 사용
            List<HealthProfileCart> existingCarts = healthProfileCartRepository.findByMbIdAndItIdAndHpStatusOrderByHpWdatetimeDesc(
                mbId, itId, "쇼핑"
            );
        
        if (!existingCarts.isEmpty()) {
            // 가장 최근 것(첫 번째 항목) 사용
            HealthProfileCart existingCart = existingCarts.get(0);
            // od_id 업데이트
            existingCart.setOdId(odId);
            existingCart.setHpMdatetime(LocalDateTime.now());
            
            // enum 필드들이 null이거나 잘못된 경우를 대비하여 명시적으로 설정
            if (existingCart.getHpOutput() == null) {
                existingCart.setHpOutput(HealthProfileCart.OutputType.Y);
            }
            if (existingCart.getHp8() == null) {
                existingCart.setHp8(HealthProfileCart.VisitType.first);
            }
            if (existingCart.getHp9() == null) {
                existingCart.setHp9(HealthProfileCart.PrescriptionType.prescription);
            }
            if (existingCart.getHp10() == null) {
                existingCart.setHp10(HealthProfileCart.StatusType.ongoing);
            }
            
            healthProfileCartRepository.save(existingCart);
            
            System.out.println("HealthProfileCart od_id 업데이트 완료 - hpNo: " + existingCart.getHpNo() + ", odId: " + odId);
            
            // 여러 개가 있었던 경우 경고 로그 출력
            if (existingCarts.size() > 1) {
                System.out.println("⚠️ [경고] 동일한 조건의 HealthProfileCart가 " + existingCarts.size() + "개 발견되었습니다. 가장 최근 것만 업데이트했습니다.");
            }
            
            return true;
        } else {
            System.out.println("HealthProfileCart를 찾을 수 없음 - mbId: " + mbId + ", itId: " + itId);
            return false;
        }
        } catch (Exception e) {
            System.err.println("처방전 상품: HealthProfileCart od_id 업데이트 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

