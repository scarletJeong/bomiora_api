package com.bomiora.user.coupon.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_shop_coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cp_no")
    private Integer no;
    
    @Column(name = "cp_id", length = 100, unique = true)
    private String id; // cp_id
    
    @Column(name = "cp_subject", length = 255)
    private String subject; // cp_subject
    
    @Column(name = "cp_method")
    private Integer method; // cp_method (할인 방법: 0=정액할인, 1=정률할인)
    
    @Column(name = "cp_target", length = 255)
    private String target; // cp_target (대상)
    
    @Column(name = "mb_id", length = 255)
    private String userId; // mb_id
    
    @Column(name = "cz_id")
    private Integer zoneId; // cz_id
    
    @Column(name = "cp_start")
    private LocalDate startDate; // cp_start
    
    @Column(name = "cp_end")
    private LocalDate endDate; // cp_end
    
    @Column(name = "cp_price")
    private Integer price; // cp_price (할인 금액)
    
    @Column(name = "cp_type")
    private Integer type; // cp_type
    
    @Column(name = "cp_trunc")
    private Integer trunc; // cp_trunc (할인율, 정률일 때)
    
    @Column(name = "cp_minimum")
    private Integer minimum; // cp_minimum (최소 주문 금액)
    
    @Column(name = "cp_maximum")
    private Integer maximum; // cp_maximum (최대 할인 금액)
    
    @Column(name = "od_id")
    private Long orderId; // od_id (사용한 주문 ID)
    
    @Column(name = "cp_datetime")
    private LocalDateTime datetime; // cp_datetime
    
    @Column(name = "mb_inf_id", length = 20)
    private String influencerId; // mb_inf_id (인플루언서아이디)
    
    @Column(name = "is_id")
    private Integer reviewId; // is_id (리뷰아이디)
    
    // 기본 생성자
    public Coupon() {}
    
    // Getters and Setters
    public Integer getNo() { return no; }
    public void setNo(Integer no) { this.no = no; }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public Integer getMethod() { return method; }
    public void setMethod(Integer method) { this.method = method; }
    
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public Integer getZoneId() { return zoneId; }
    public void setZoneId(Integer zoneId) { this.zoneId = zoneId; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
    
    public Integer getTrunc() { return trunc; }
    public void setTrunc(Integer trunc) { this.trunc = trunc; }
    
    public Integer getMinimum() { return minimum; }
    public void setMinimum(Integer minimum) { this.minimum = minimum; }
    
    public Integer getMaximum() { return maximum; }
    public void setMaximum(Integer maximum) { this.maximum = maximum; }
    
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    
    public LocalDateTime getDatetime() { return datetime; }
    public void setDatetime(LocalDateTime datetime) { this.datetime = datetime; }
    
    public String getInfluencerId() { return influencerId; }
    public void setInfluencerId(String influencerId) { this.influencerId = influencerId; }
    
    public Integer getReviewId() { return reviewId; }
    public void setReviewId(Integer reviewId) { this.reviewId = reviewId; }
    
    /**
     * 사용 가능한 쿠폰인지 확인
     */
    public boolean isAvailable() {
        LocalDate now = LocalDate.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate) && (orderId == null || orderId == 0);
    }
    
    /**
     * 사용한 쿠폰인지 확인
     */
    public boolean isUsed() {
        return orderId != null && orderId > 0;
    }
    
    /**
     * 만료된 쿠폰인지 확인
     */
    public boolean isExpired() {
        LocalDate now = LocalDate.now();
        return now.isAfter(endDate) && !isUsed();
    }
}

