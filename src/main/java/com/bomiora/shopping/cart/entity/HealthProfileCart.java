package com.bomiora.shopping.cart.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_shop_health_profiles_cart")
public class HealthProfileCart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hp_no")
    private Integer hpNo;
    
    @Column(name = "mb_id", nullable = false, length = 30)
    private String mbId;
    
    @Column(name = "it_id", nullable = false, length = 20)
    private String itId;
    
    @Column(name = "od_id", nullable = false)
    private Long odId;
    
    @Column(name = "inf_code", length = 255)
    private String infCode;
    
    @Column(name = "answer_1", length = 255)
    private String answer1;
    
    @Column(name = "answer_2", length = 255)
    private String answer2;
    
    @Column(name = "answer_3", length = 255)
    private String answer3;
    
    @Column(name = "answer_4", length = 255)
    private String answer4;
    
    @Column(name = "answer_5", length = 255)
    private String answer5;
    
    @Column(name = "answer_6", length = 255)
    private String answer6;
    
    @Column(name = "answer_7", length = 255)
    private String answer7;
    
    @Column(name = "answer_8", length = 255)
    private String answer8;
    
    @Column(name = "answer_9", length = 255)
    private String answer9;
    
    @Column(name = "answer_10", length = 255)
    private String answer10;
    
    @Column(name = "answer_11", length = 255)
    private String answer11;
    
    @Column(name = "answer_12", length = 255)
    private String answer12;
    
    @Column(name = "hp_status", length = 255)
    private String hpStatus;
    
    @Column(name = "hp_doc_name", length = 50)
    private String hpDocName;
    
    @Column(name = "hp_rsvt_date")
    private LocalDate hpRsvtDate;
    
    @Column(name = "hp_rsvt_stime", length = 10)
    private String hpRsvtStime;
    
    @Column(name = "hp_rsvt_etime", length = 10)
    private String hpRsvtEtime;
    
    @Column(name = "hp_rsvt_name", length = 50)
    private String hpRsvtName;
    
    @Column(name = "hp_rsvt_tel", length = 50)
    private String hpRsvtTel;
    
    @Column(name = "hp_wdatetime", nullable = false)
    private LocalDateTime hpWdatetime;
    
    @Column(name = "hp_mdatetime", nullable = false)
    private LocalDateTime hpMdatetime;
    
    @Column(name = "hp_ip", length = 255)
    private String hpIp;
    
    @Column(name = "hp_memo", columnDefinition = "TEXT")
    private String hpMemo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "hp_output", nullable = false, columnDefinition = "ENUM('Y','N') DEFAULT 'Y'")
    private OutputType hpOutput;
    
    @Column(name = "hp_1", length = 255)
    private String hp1;
    
    @Column(name = "hp_2", length = 255)
    private String hp2;
    
    @Column(name = "hp_3", length = 255)
    private String hp3;
    
    @Column(name = "hp_4", length = 255)
    private String hp4;
    
    @Column(name = "hp_5", length = 255)
    private String hp5;
    
    @Column(name = "hp_6", length = 255)
    private String hp6;
    
    @Column(name = "hp_7", length = 255)
    private String hp7;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "hp_8", nullable = false, columnDefinition = "ENUM('first','second') DEFAULT 'first'")
    private VisitType hp8;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "hp_9", nullable = false, columnDefinition = "ENUM('atmosphere','prescription') DEFAULT 'atmosphere'")
    private PrescriptionType hp9;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "hp_10", nullable = false, columnDefinition = "ENUM('ongoing','completion') DEFAULT 'ongoing'")
    private StatusType hp10;
    
    @Column(name = "answer_13", length = 255)
    private String answer13;
    
    @Column(name = "answer_13_period", length = 100)
    private String answer13Period;
    
    @Column(name = "answer_13_dosage", length = 100)
    private String answer13Dosage;
    
    @Column(name = "answer_13_medicine", length = 200)
    private String answer13Medicine;
    
    @Column(name = "answer_7_1", length = 100)
    private String answer71;
    
    @Column(name = "answer_13_sideeffect", length = 100)
    private String answer13Sideeffect;
    
    public enum OutputType {
        Y, N
    }
    
    public enum VisitType {
        first, second
    }
    
    public enum PrescriptionType {
        atmosphere, prescription
    }
    
    public enum StatusType {
        ongoing, completion
    }
    
    // 기본 생성자
    public HealthProfileCart() {}
    
    // Getters and Setters
    public Integer getHpNo() { return hpNo; }
    public void setHpNo(Integer hpNo) { this.hpNo = hpNo; }
    
    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    
    public String getItId() { return itId; }
    public void setItId(String itId) { this.itId = itId; }
    
    public Long getOdId() { return odId; }
    public void setOdId(Long odId) { this.odId = odId; }
    
    public String getInfCode() { return infCode; }
    public void setInfCode(String infCode) { this.infCode = infCode; }
    
    public String getAnswer1() { return answer1; }
    public void setAnswer1(String answer1) { this.answer1 = answer1; }
    
    public String getAnswer2() { return answer2; }
    public void setAnswer2(String answer2) { this.answer2 = answer2; }
    
    public String getAnswer3() { return answer3; }
    public void setAnswer3(String answer3) { this.answer3 = answer3; }
    
    public String getAnswer4() { return answer4; }
    public void setAnswer4(String answer4) { this.answer4 = answer4; }
    
    public String getAnswer5() { return answer5; }
    public void setAnswer5(String answer5) { this.answer5 = answer5; }
    
    public String getAnswer6() { return answer6; }
    public void setAnswer6(String answer6) { this.answer6 = answer6; }
    
    public String getAnswer7() { return answer7; }
    public void setAnswer7(String answer7) { this.answer7 = answer7; }
    
    public String getAnswer8() { return answer8; }
    public void setAnswer8(String answer8) { this.answer8 = answer8; }
    
    public String getAnswer9() { return answer9; }
    public void setAnswer9(String answer9) { this.answer9 = answer9; }
    
    public String getAnswer10() { return answer10; }
    public void setAnswer10(String answer10) { this.answer10 = answer10; }
    
    public String getAnswer11() { return answer11; }
    public void setAnswer11(String answer11) { this.answer11 = answer11; }
    
    public String getAnswer12() { return answer12; }
    public void setAnswer12(String answer12) { this.answer12 = answer12; }
    
    public String getHpStatus() { return hpStatus; }
    public void setHpStatus(String hpStatus) { this.hpStatus = hpStatus; }
    
    public String getHpDocName() { return hpDocName; }
    public void setHpDocName(String hpDocName) { this.hpDocName = hpDocName; }
    
    public LocalDate getHpRsvtDate() { return hpRsvtDate; }
    public void setHpRsvtDate(LocalDate hpRsvtDate) { this.hpRsvtDate = hpRsvtDate; }
    
    public String getHpRsvtStime() { return hpRsvtStime; }
    public void setHpRsvtStime(String hpRsvtStime) { this.hpRsvtStime = hpRsvtStime; }
    
    public String getHpRsvtEtime() { return hpRsvtEtime; }
    public void setHpRsvtEtime(String hpRsvtEtime) { this.hpRsvtEtime = hpRsvtEtime; }
    
    public String getHpRsvtName() { return hpRsvtName; }
    public void setHpRsvtName(String hpRsvtName) { this.hpRsvtName = hpRsvtName; }
    
    public String getHpRsvtTel() { return hpRsvtTel; }
    public void setHpRsvtTel(String hpRsvtTel) { this.hpRsvtTel = hpRsvtTel; }
    
    public LocalDateTime getHpWdatetime() { return hpWdatetime; }
    public void setHpWdatetime(LocalDateTime hpWdatetime) { this.hpWdatetime = hpWdatetime; }
    
    public LocalDateTime getHpMdatetime() { return hpMdatetime; }
    public void setHpMdatetime(LocalDateTime hpMdatetime) { this.hpMdatetime = hpMdatetime; }
    
    public String getHpIp() { return hpIp; }
    public void setHpIp(String hpIp) { this.hpIp = hpIp; }
    
    public String getHpMemo() { return hpMemo; }
    public void setHpMemo(String hpMemo) { this.hpMemo = hpMemo; }
    
    public OutputType getHpOutput() { return hpOutput; }
    public void setHpOutput(OutputType hpOutput) { this.hpOutput = hpOutput; }
    
    public String getHp1() { return hp1; }
    public void setHp1(String hp1) { this.hp1 = hp1; }
    
    public String getHp2() { return hp2; }
    public void setHp2(String hp2) { this.hp2 = hp2; }
    
    public String getHp3() { return hp3; }
    public void setHp3(String hp3) { this.hp3 = hp3; }
    
    public String getHp4() { return hp4; }
    public void setHp4(String hp4) { this.hp4 = hp4; }
    
    public String getHp5() { return hp5; }
    public void setHp5(String hp5) { this.hp5 = hp5; }
    
    public String getHp6() { return hp6; }
    public void setHp6(String hp6) { this.hp6 = hp6; }
    
    public String getHp7() { return hp7; }
    public void setHp7(String hp7) { this.hp7 = hp7; }
    
    public VisitType getHp8() { return hp8; }
    public void setHp8(VisitType hp8) { this.hp8 = hp8; }
    
    public PrescriptionType getHp9() { return hp9; }
    public void setHp9(PrescriptionType hp9) { this.hp9 = hp9; }
    
    public StatusType getHp10() { return hp10; }
    public void setHp10(StatusType hp10) { this.hp10 = hp10; }
    
    public String getAnswer13() { return answer13; }
    public void setAnswer13(String answer13) { this.answer13 = answer13; }
    
    public String getAnswer13Period() { return answer13Period; }
    public void setAnswer13Period(String answer13Period) { this.answer13Period = answer13Period; }
    
    public String getAnswer13Dosage() { return answer13Dosage; }
    public void setAnswer13Dosage(String answer13Dosage) { this.answer13Dosage = answer13Dosage; }
    
    public String getAnswer13Medicine() { return answer13Medicine; }
    public void setAnswer13Medicine(String answer13Medicine) { this.answer13Medicine = answer13Medicine; }
    
    public String getAnswer71() { return answer71; }
    public void setAnswer71(String answer71) { this.answer71 = answer71; }
    
    public String getAnswer13Sideeffect() { return answer13Sideeffect; }
    public void setAnswer13Sideeffect(String answer13Sideeffect) { this.answer13Sideeffect = answer13Sideeffect; }
}

