package com.bomiora.user.review.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 리뷰 추천/비추천 기록
 * 테이블: bomiora_shop_item_use_good (기존 PHP와 동일)
 */
@Entity
@Table(name = "bomiora_shop_item_use_good",
       uniqueConstraints = @UniqueConstraint(columnNames = {"it_id", "is_id", "mb_id"}))
public class ReviewHelpful {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bg_id")
    private Long id;
    
    @Column(name = "it_id", nullable = false, length = 20)
    private String itId; // 제품 ID
    
    @Column(name = "is_id", nullable = false)
    private Long reviewId; // 리뷰 ID
    
    @Column(name = "mb_id", nullable = false, length = 20)
    private String mbId; // 회원 ID
    
    @Column(name = "bg_flag", length = 10)
    private String bgFlag; // 'good' or 'bad'
    
    @Column(name = "bg_datetime")
    private LocalDateTime bgDatetime;
    
    // 기본 생성자
    public ReviewHelpful() {
        this.bgDatetime = LocalDateTime.now();
    }
    
    public ReviewHelpful(String itId, Long reviewId, String mbId, String bgFlag) {
        this.itId = itId;
        this.reviewId = reviewId;
        this.mbId = mbId;
        this.bgFlag = bgFlag;
        this.bgDatetime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getItId() { return itId; }
    public void setItId(String itId) { this.itId = itId; }
    
    public Long getReviewId() { return reviewId; }
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }
    
    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    
    public String getBgFlag() { return bgFlag; }
    public void setBgFlag(String bgFlag) { this.bgFlag = bgFlag; }
    
    public LocalDateTime getBgDatetime() { return bgDatetime; }
    public void setBgDatetime(LocalDateTime bgDatetime) { this.bgDatetime = bgDatetime; }
}

