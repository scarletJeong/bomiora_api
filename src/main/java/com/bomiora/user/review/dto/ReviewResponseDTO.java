package com.bomiora.user.review.dto;

import com.bomiora.user.review.entity.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 리뷰 응답 DTO
 */
public class ReviewResponseDTO {
    
    private Long isId;
    private String itId;
    private String itName; // 제품명
    private String mbId;
    private String isName;
    private LocalDateTime isTime;
    private Integer isConfirm;
    
    // 평점
    private Integer isScore1;
    private Integer isScore2;
    private Integer isScore3;
    private Integer isScore4;
    private Double averageScore; // 평균 평점
    
    // 리뷰 종류
    private String isRvkind;
    
    // 추천 여부
    private String isRecommend;
    private Integer isGood;
    private Integer czDownload; // 도움쿠폰 다운로드 카운트
    
    // 리뷰 내용
    private String isPositiveReviewText;
    private String isNegativeReviewText;
    private String isMoreReviewText;
    
    // 이미지들
    private List<String> images;
    
    // 사용자 정보
    private LocalDate isBirthday;
    private Integer isWeight;
    private Integer isHeight;
    private String isPayMthod;
    private Integer isOutageNum;
    
    private Long odId;
    
    // 생성자
    public ReviewResponseDTO() {
        this.images = new ArrayList<>();
    }
    
    public ReviewResponseDTO(Review review) {
        this.isId = review.getIsId();
        this.itId = review.getItId();
        this.itName = review.getItName();
        this.mbId = review.getMbId();
        this.isName = review.getIsName();
        this.isTime = review.getIsTime();
        this.isConfirm = review.getIsConfirm();
        
        this.isScore1 = review.getIsScore1();
        this.isScore2 = review.getIsScore2();
        this.isScore3 = review.getIsScore3();
        this.isScore4 = review.getIsScore4();
        
        // 평균 계산
        if (isScore1 != null && isScore2 != null && isScore3 != null && isScore4 != null) {
            this.averageScore = (isScore1 + isScore2 + isScore3 + isScore4) / 4.0;
        }
        
        this.isRvkind = review.getIsRvkind();
        this.isRecommend = review.getIsRecommend();
        this.isGood = review.getIsGood();
        this.czDownload = review.getCzDownload();
        
        this.isPositiveReviewText = review.getIsPositiveReviewText();
        this.isNegativeReviewText = review.getIsNegativeReviewText();
        this.isMoreReviewText = review.getIsMoreReviewText();
        
        // 이미지 리스트 생성 (상대 경로 그대로, Flutter에서 변환)
        this.images = new ArrayList<>();
        if (review.getIsImg1() != null && !review.getIsImg1().isEmpty()) {
            images.add(review.getIsImg1());
            System.out.println("🖼️ [리뷰 이미지] is_img1: " + review.getIsImg1());
        }
        if (review.getIsImg2() != null && !review.getIsImg2().isEmpty()) images.add(review.getIsImg2());
        if (review.getIsImg3() != null && !review.getIsImg3().isEmpty()) images.add(review.getIsImg3());
        if (review.getIsImg4() != null && !review.getIsImg4().isEmpty()) images.add(review.getIsImg4());
        if (review.getIsImg5() != null && !review.getIsImg5().isEmpty()) images.add(review.getIsImg5());
        if (review.getIsImg6() != null && !review.getIsImg6().isEmpty()) images.add(review.getIsImg6());
        if (review.getIsImg7() != null && !review.getIsImg7().isEmpty()) images.add(review.getIsImg7());
        if (review.getIsImg8() != null && !review.getIsImg8().isEmpty()) images.add(review.getIsImg8());
        if (review.getIsImg9() != null && !review.getIsImg9().isEmpty()) images.add(review.getIsImg9());
        if (review.getIsImg10() != null && !review.getIsImg10().isEmpty()) images.add(review.getIsImg10());
        
        this.isBirthday = review.getIsBirthday();
        this.isWeight = review.getIsWeight();
        this.isHeight = review.getIsHeight();
        this.isPayMthod = review.getIsPayMthod();
        this.isOutageNum = review.getIsOutageNum();
        this.odId = review.getOdId();
    }
    
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

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
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

    public Integer getCzDownload() {
        return czDownload;
    }

    public void setCzDownload(Integer czDownload) {
        this.czDownload = czDownload;
    }

    public String getItName() {
        return itName;
    }

    public void setItName(String itName) {
        this.itName = itName;
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

    public Long getOdId() {
        return odId;
    }

    public void setOdId(Long odId) {
        this.odId = odId;
    }
}

