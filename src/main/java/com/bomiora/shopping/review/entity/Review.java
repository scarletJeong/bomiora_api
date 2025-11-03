package com.bomiora.shopping.review.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_shop_item_use")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "is_id")
    private Integer id;
    
    @Column(name = "it_id", length = 20)
    private String productId;
    
    @Column(name = "mb_id", length = 255)
    private String userId;
    
    @Column(name = "is_name", length = 255)
    private String userName;
    
    @Column(name = "is_gubun", length = 1)
    private String gubun; // 'P' (전문), 'G' (일반)
    
    @Column(name = "is_score1")
    private Integer score1; // 효과
    
    @Column(name = "is_score2")
    private Integer score2; // 가성비
    
    @Column(name = "is_score3")
    private Integer score3; // 향/맛
    
    @Column(name = "is_score4")
    private Integer score4; // 편리함
    
    @Column(name = "is_subject", length = 255)
    private String subject;
    
    @Column(name = "is_content", columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "is_good")
    private Integer helpfulCount;
    
    @Column(name = "is_time")
    private LocalDateTime createdAt;
    
    @Column(name = "is_update_time")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_recommend", length = 1)
    private String recommend; // 'y', 'n'
    
    @Column(name = "is_rvkind", length = 20)
    private String reviewKind; // 'general', 'supporter'
    
    @Column(name = "is_img1", length = 255)
    private String image1;
    
    @Column(name = "is_img2", length = 255)
    private String image2;
    
    @Column(name = "is_img3", length = 255)
    private String image3;
    
    @Column(name = "is_img4", length = 255)
    private String image4;
    
    @Column(name = "is_img5", length = 255)
    private String image5;
    
    @Column(name = "is_img6", length = 255)
    private String image6;
    
    @Column(name = "is_img7", length = 255)
    private String image7;
    
    @Column(name = "is_img8", length = 255)
    private String image8;
    
    @Column(name = "is_img9", length = 255)
    private String image9;
    
    @Column(name = "is_img10", length = 255)
    private String image10;
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getGubun() {
        return gubun;
    }
    
    public void setGubun(String gubun) {
        this.gubun = gubun;
    }
    
    public Integer getScore1() {
        return score1;
    }
    
    public void setScore1(Integer score1) {
        this.score1 = score1;
    }
    
    public Integer getScore2() {
        return score2;
    }
    
    public void setScore2(Integer score2) {
        this.score2 = score2;
    }
    
    public Integer getScore3() {
        return score3;
    }
    
    public void setScore3(Integer score3) {
        this.score3 = score3;
    }
    
    public Integer getScore4() {
        return score4;
    }
    
    public void setScore4(Integer score4) {
        this.score4 = score4;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Integer getHelpfulCount() {
        return helpfulCount;
    }
    
    public void setHelpfulCount(Integer helpfulCount) {
        this.helpfulCount = helpfulCount;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getRecommend() {
        return recommend;
    }
    
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
    
    public String getReviewKind() {
        return reviewKind;
    }
    
    public void setReviewKind(String reviewKind) {
        this.reviewKind = reviewKind;
    }
    
    public String getImage1() {
        return image1;
    }
    
    public void setImage1(String image1) {
        this.image1 = image1;
    }
    
    public String getImage2() {
        return image2;
    }
    
    public void setImage2(String image2) {
        this.image2 = image2;
    }
    
    public String getImage3() {
        return image3;
    }
    
    public void setImage3(String image3) {
        this.image3 = image3;
    }
    
    public String getImage4() {
        return image4;
    }
    
    public void setImage4(String image4) {
        this.image4 = image4;
    }
    
    public String getImage5() {
        return image5;
    }
    
    public void setImage5(String image5) {
        this.image5 = image5;
    }
    
    public String getImage6() {
        return image6;
    }
    
    public void setImage6(String image6) {
        this.image6 = image6;
    }
    
    public String getImage7() {
        return image7;
    }
    
    public void setImage7(String image7) {
        this.image7 = image7;
    }
    
    public String getImage8() {
        return image8;
    }
    
    public void setImage8(String image8) {
        this.image8 = image8;
    }
    
    public String getImage9() {
        return image9;
    }
    
    public void setImage9(String image9) {
        this.image9 = image9;
    }
    
    public String getImage10() {
        return image10;
    }
    
    public void setImage10(String image10) {
        this.image10 = image10;
    }
    
    // 평균 점수 계산
    public Double getAverageScore() {
        int count = 0;
        int sum = 0;
        
        if (score1 != null && score1 > 0) {
            sum += score1;
            count++;
        }
        if (score2 != null && score2 > 0) {
            sum += score2;
            count++;
        }
        if (score3 != null && score3 > 0) {
            sum += score3;
            count++;
        }
        if (score4 != null && score4 > 0) {
            sum += score4;
            count++;
        }
        
        return count > 0 ? (double) sum / count : 0.0;
    }
    
    // 서포터 리뷰 여부
    public boolean isSupporterReview() {
        return "supporter".equalsIgnoreCase(reviewKind);
    }
    
    // 일반 리뷰 여부
    public boolean isGeneralReview() {
        return "general".equalsIgnoreCase(reviewKind) || reviewKind == null || reviewKind.isEmpty();
    }
}
