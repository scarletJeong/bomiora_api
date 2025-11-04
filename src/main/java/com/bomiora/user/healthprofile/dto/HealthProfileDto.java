package com.bomiora.user.healthprofile.dto;

import java.time.LocalDateTime;

public class HealthProfileDto {
    
    private Long pfNo;
    private String mbId;
    private String answer1; // 생년월일
    private String answer2; // 성별
    private String answer3; // 목표감량체중
    private String answer4; // 키
    private String answer5; // 몸무게
    private String answer6; // 다이어트예상기간
    private String answer7; // 하루끼니
    private String answer8; // 식습관
    private String answer9; // 자주먹는음식
    private String answer10; // 운동습관
    private String answer11; // 질병
    private String answer12; // 복용중인 약
    private String answer13; // 기존 다이어트 복용약 여부
    private String answer13Period; // 다이어트약 복용기간
    private String answer13Dosage; // 다이어트약 복용횟수
    private String answer13Medicine; // 복용한 다이어트약명
    private String answer71; // 식사시간
    private String answer13Sideeffect; // 부작용(불편했던점)
    private LocalDateTime pfWdatetime;
    private LocalDateTime pfMdatetime;
    private String pfIp;
    private String pfMemo;
    
    // 기본 생성자
    public HealthProfileDto() {}
    
    // 모든 필드를 포함한 생성자
    public HealthProfileDto(Long pfNo, String mbId, String answer1, String answer2, String answer3, 
                           String answer4, String answer5, String answer6, String answer7, 
                           String answer8, String answer9, String answer10, String answer11, 
                           String answer12, String answer13, String answer13Period, 
                           String answer13Dosage, String answer13Medicine, String answer71, 
                           String answer13Sideeffect, LocalDateTime pfWdatetime, 
                           LocalDateTime pfMdatetime, String pfIp, String pfMemo) {
        this.pfNo = pfNo;
        this.mbId = mbId;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
        this.answer6 = answer6;
        this.answer7 = answer7;
        this.answer8 = answer8;
        this.answer9 = answer9;
        this.answer10 = answer10;
        this.answer11 = answer11;
        this.answer12 = answer12;
        this.answer13 = answer13;
        this.answer13Period = answer13Period;
        this.answer13Dosage = answer13Dosage;
        this.answer13Medicine = answer13Medicine;
        this.answer71 = answer71;
        this.answer13Sideeffect = answer13Sideeffect;
        this.pfWdatetime = pfWdatetime;
        this.pfMdatetime = pfMdatetime;
        this.pfIp = pfIp;
        this.pfMemo = pfMemo;
    }
    
    // Getter 메서드들
    public Long getPfNo() { return pfNo; }
    public String getMbId() { return mbId; }
    public String getAnswer1() { return answer1; }
    public String getAnswer2() { return answer2; }
    public String getAnswer3() { return answer3; }
    public String getAnswer4() { return answer4; }
    public String getAnswer5() { return answer5; }
    public String getAnswer6() { return answer6; }
    public String getAnswer7() { return answer7; }
    public String getAnswer8() { return answer8; }
    public String getAnswer9() { return answer9; }
    public String getAnswer10() { return answer10; }
    public String getAnswer11() { return answer11; }
    public String getAnswer12() { return answer12; }
    public String getAnswer13() { return answer13; }
    public String getAnswer13Period() { return answer13Period; }
    public String getAnswer13Dosage() { return answer13Dosage; }
    public String getAnswer13Medicine() { return answer13Medicine; }
    public String getAnswer71() { return answer71; }
    public String getAnswer13Sideeffect() { return answer13Sideeffect; }
    public LocalDateTime getPfWdatetime() { return pfWdatetime; }
    public LocalDateTime getPfMdatetime() { return pfMdatetime; }
    public String getPfIp() { return pfIp; }
    public String getPfMemo() { return pfMemo; }
    
    // Setter 메서드들
    public void setPfNo(Long pfNo) { this.pfNo = pfNo; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    public void setAnswer1(String answer1) { this.answer1 = answer1; }
    public void setAnswer2(String answer2) { this.answer2 = answer2; }
    public void setAnswer3(String answer3) { this.answer3 = answer3; }
    public void setAnswer4(String answer4) { this.answer4 = answer4; }
    public void setAnswer5(String answer5) { this.answer5 = answer5; }
    public void setAnswer6(String answer6) { this.answer6 = answer6; }
    public void setAnswer7(String answer7) { this.answer7 = answer7; }
    public void setAnswer8(String answer8) { this.answer8 = answer8; }
    public void setAnswer9(String answer9) { this.answer9 = answer9; }
    public void setAnswer10(String answer10) { this.answer10 = answer10; }
    public void setAnswer11(String answer11) { this.answer11 = answer11; }
    public void setAnswer12(String answer12) { this.answer12 = answer12; }
    public void setAnswer13(String answer13) { this.answer13 = answer13; }
    public void setAnswer13Period(String answer13Period) { this.answer13Period = answer13Period; }
    public void setAnswer13Dosage(String answer13Dosage) { this.answer13Dosage = answer13Dosage; }
    public void setAnswer13Medicine(String answer13Medicine) { this.answer13Medicine = answer13Medicine; }
    public void setAnswer71(String answer71) { this.answer71 = answer71; }
    public void setAnswer13Sideeffect(String answer13Sideeffect) { this.answer13Sideeffect = answer13Sideeffect; }
    public void setPfWdatetime(LocalDateTime pfWdatetime) { this.pfWdatetime = pfWdatetime; }
    public void setPfMdatetime(LocalDateTime pfMdatetime) { this.pfMdatetime = pfMdatetime; }
    public void setPfIp(String pfIp) { this.pfIp = pfIp; }
    public void setPfMemo(String pfMemo) { this.pfMemo = pfMemo; }
}

