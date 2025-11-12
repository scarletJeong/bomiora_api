package com.bomiora.user.review.dto;

/**
 * 리뷰 통계 DTO
 */
public class ReviewStatsDTO {
    
    private Long totalCount; // 전체 리뷰 개수
    private Double averageScore; // 평균 평점
    private Long generalCount; // 일반 리뷰 개수
    private Long supporterCount; // 서포터 리뷰 개수
    
    // 생성자
    public ReviewStatsDTO() {}
    
    public ReviewStatsDTO(Long totalCount, Double averageScore, Long generalCount, Long supporterCount) {
        this.totalCount = totalCount;
        this.averageScore = averageScore;
        this.generalCount = generalCount;
        this.supporterCount = supporterCount;
    }
    
    // Getters and Setters
    
    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Long getGeneralCount() {
        return generalCount;
    }

    public void setGeneralCount(Long generalCount) {
        this.generalCount = generalCount;
    }

    public Long getSupporterCount() {
        return supporterCount;
    }

    public void setSupporterCount(Long supporterCount) {
        this.supporterCount = supporterCount;
    }
}

