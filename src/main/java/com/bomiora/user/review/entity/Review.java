package com.bomiora.user.review.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 리뷰(사용후기) 엔티티
 * 테이블: bomiora_shop_item_use
 */
@Entity
@Table(name = "bomiora_shop_item_use")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "is_id")
    private Long isId;
    
    // 기본 정보
    @Column(name = "it_id", nullable = false)
    private String itId;
    
    @Column(name = "mb_id", nullable = false)
    private String mbId;
    
    @Column(name = "is_name")
    private String isName;
    
    @Column(name = "is_time")
    private LocalDateTime isTime;
    
    @Column(name = "is_confirm", columnDefinition = "tinyint(1) default 0")
    private Integer isConfirm; // 0: 미승인, 1: 승인
    
    // 평점 (각 5점 만점)
    @Column(name = "is_score1")
    private Integer isScore1; // 효과
    
    @Column(name = "is_score2")
    private Integer isScore2; // 가성비
    
    @Column(name = "is_score3")
    private Integer isScore3; // 맛/향
    
    @Column(name = "is_score4")
    private Integer isScore4; // 편리함
    
    // 리뷰 종류
    @Column(name = "is_rvkind")
    private String isRvkind; // 'general': 일반, 'supporter': 서포터
    
    // 추가 정보
    @Column(name = "is_recommend", length = 1)
    private String isRecommend; // 'y' / 'n'
    
    @Column(name = "is_good")
    private Integer isGood; // 도움이 돼요 카운트
    
    @Column(name = "is_positive_review_text", columnDefinition = "TEXT")
    private String isPositiveReviewText; // 좋았던 점
    
    @Column(name = "is_negative_review_text", columnDefinition = "TEXT")
    private String isNegativeReviewText; // 아쉬운 점
    
    @Column(name = "is_more_review_text", columnDefinition = "TEXT")
    private String isMoreReviewText; // 꿀팁
    
    // 리뷰 이미지 (최대 10개)
    @Column(name = "is_img1")
    private String isImg1;
    
    @Column(name = "is_img2")
    private String isImg2;
    
    @Column(name = "is_img3")
    private String isImg3;
    
    @Column(name = "is_img4")
    private String isImg4;
    
    @Column(name = "is_img5")
    private String isImg5;
    
    @Column(name = "is_img6")
    private String isImg6;
    
    @Column(name = "is_img7")
    private String isImg7;
    
    @Column(name = "is_img8")
    private String isImg8;
    
    @Column(name = "is_img9")
    private String isImg9;
    
    @Column(name = "is_img10")
    private String isImg10;
    
    // 사용자 정보
    @Column(name = "is_birthday")
    private LocalDate isBirthday;
    
    @Column(name = "is_weight")
    private Integer isWeight;
    
    @Column(name = "is_height")
    private Integer isHeight;
    
    @Column(name = "is_pay_mthod")
    private String isPayMthod; // 'solo': 내돈내산
    
    @Column(name = "is_outage_num")
    private Integer isOutageNum; // 감량 kg
    
    // 주문 정보
    @Column(name = "od_id")
    private Long odId;
    
    // Getters and Setters
    
    public Long getIsId() {
        return isId;
    }

    public void setIsId(Long isId) {
        this.isId = isId;
    }

    public String getItId() {
        return itId;
    }

    public void setItId(String itId) {
        this.itId = itId;
    }

    public String getMbId() {
        return mbId;
    }

    public void setMbId(String mbId) {
        this.mbId = mbId;
    }

    public String getIsName() {
        return isName;
    }

    public void setIsName(String isName) {
        this.isName = isName;
    }

    public LocalDateTime getIsTime() {
        return isTime;
    }

    public void setIsTime(LocalDateTime isTime) {
        this.isTime = isTime;
    }

    public Integer getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(Integer isConfirm) {
        this.isConfirm = isConfirm;
    }

    public Integer getIsScore1() {
        return isScore1;
    }

    public void setIsScore1(Integer isScore1) {
        this.isScore1 = isScore1;
    }

    public Integer getIsScore2() {
        return isScore2;
    }

    public void setIsScore2(Integer isScore2) {
        this.isScore2 = isScore2;
    }

    public Integer getIsScore3() {
        return isScore3;
    }

    public void setIsScore3(Integer isScore3) {
        this.isScore3 = isScore3;
    }

    public Integer getIsScore4() {
        return isScore4;
    }

    public void setIsScore4(Integer isScore4) {
        this.isScore4 = isScore4;
    }

    public String getIsRvkind() {
        return isRvkind;
    }

    public void setIsRvkind(String isRvkind) {
        this.isRvkind = isRvkind;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getIsGood() {
        return isGood;
    }

    public void setIsGood(Integer isGood) {
        this.isGood = isGood;
    }

    public String getIsPositiveReviewText() {
        return isPositiveReviewText;
    }

    public void setIsPositiveReviewText(String isPositiveReviewText) {
        this.isPositiveReviewText = isPositiveReviewText;
    }

    public String getIsNegativeReviewText() {
        return isNegativeReviewText;
    }

    public void setIsNegativeReviewText(String isNegativeReviewText) {
        this.isNegativeReviewText = isNegativeReviewText;
    }

    public String getIsMoreReviewText() {
        return isMoreReviewText;
    }

    public void setIsMoreReviewText(String isMoreReviewText) {
        this.isMoreReviewText = isMoreReviewText;
    }

    public String getIsImg1() {
        return isImg1;
    }

    public void setIsImg1(String isImg1) {
        this.isImg1 = isImg1;
    }

    public String getIsImg2() {
        return isImg2;
    }

    public void setIsImg2(String isImg2) {
        this.isImg2 = isImg2;
    }

    public String getIsImg3() {
        return isImg3;
    }

    public void setIsImg3(String isImg3) {
        this.isImg3 = isImg3;
    }

    public String getIsImg4() {
        return isImg4;
    }

    public void setIsImg4(String isImg4) {
        this.isImg4 = isImg4;
    }

    public String getIsImg5() {
        return isImg5;
    }

    public void setIsImg5(String isImg5) {
        this.isImg5 = isImg5;
    }

    public String getIsImg6() {
        return isImg6;
    }

    public void setIsImg6(String isImg6) {
        this.isImg6 = isImg6;
    }

    public String getIsImg7() {
        return isImg7;
    }

    public void setIsImg7(String isImg7) {
        this.isImg7 = isImg7;
    }

    public String getIsImg8() {
        return isImg8;
    }

    public void setIsImg8(String isImg8) {
        this.isImg8 = isImg8;
    }

    public String getIsImg9() {
        return isImg9;
    }

    public void setIsImg9(String isImg9) {
        this.isImg9 = isImg9;
    }

    public String getIsImg10() {
        return isImg10;
    }

    public void setIsImg10(String isImg10) {
        this.isImg10 = isImg10;
    }

    public LocalDate getIsBirthday() {
        return isBirthday;
    }

    public void setIsBirthday(LocalDate isBirthday) {
        this.isBirthday = isBirthday;
    }

    public Integer getIsWeight() {
        return isWeight;
    }

    public void setIsWeight(Integer isWeight) {
        this.isWeight = isWeight;
    }

    public Integer getIsHeight() {
        return isHeight;
    }

    public void setIsHeight(Integer isHeight) {
        this.isHeight = isHeight;
    }

    public String getIsPayMthod() {
        return isPayMthod;
    }

    public void setIsPayMthod(String isPayMthod) {
        this.isPayMthod = isPayMthod;
    }

    public Integer getIsOutageNum() {
        return isOutageNum;
    }

    public void setIsOutageNum(Integer isOutageNum) {
        this.isOutageNum = isOutageNum;
    }

    public Long getOdId() {
        return odId;
    }

    public void setOdId(Long odId) {
        this.odId = odId;
    }
}

