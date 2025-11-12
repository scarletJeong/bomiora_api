package com.bomiora.user.review.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * 리뷰 작성 요청 DTO
 */
public class ReviewRequestDTO {
    
    private String mbId;
    private Long odId;
    private String itId;
    private String isName;
    
    // 평점 (각 5점 만점)
    private Integer isScore1; // 효과
    private Integer isScore2; // 가성비
    private Integer isScore3; // 맛/향
    private Integer isScore4; // 편리함
    
    // 리뷰 종류
    private String isRvkind; // 'general' or 'supporter'
    
    // 추천 여부
    private String isRecommend; // 'y' or 'n'
    
    // 리뷰 내용
    private String isPositiveReviewText; // 좋았던 점
    private String isNegativeReviewText; // 아쉬운 점
    private String isMoreReviewText; // 꿀팁
    
    // 리뷰 이미지들
    private List<String> images;
    
    // 사용자 정보
    private LocalDate isBirthday;
    private Integer isWeight;
    private Integer isHeight;
    private String isPayMthod; // 'solo': 내돈내산
    private Integer isOutageNum; // 감량 kg
    
    // Getters and Setters
    
    public String getMbId() {
        return mbId;
    }

    public void setMbId(String mbId) {
        this.mbId = mbId;
    }

    public Long getOdId() {
        return odId;
    }

    public void setOdId(Long odId) {
        this.odId = odId;
    }

    public String getItId() {
        return itId;
    }

    public void setItId(String itId) {
        this.itId = itId;
    }

    public String getIsName() {
        return isName;
    }

    public void setIsName(String isName) {
        this.isName = isName;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
}

