package com.bomiora.shopping.product.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_shop_item_new")
public class Product {
    
    @Id
    @Column(name = "it_id", nullable = false, length = 20)
    private String id;
    
    @Column(name = "ca_id", nullable = false, length = 10)
    private String categoryId;
    
    @Column(name = "it_name", nullable = false, length = 255)
    private String name;
    
    @Column(name = "it_explan", columnDefinition = "mediumtext")
    private String description;
    
    @Column(name = "it_price", nullable = false)
    private Integer price;
    
    @Column(name = "it_cust_price", nullable = false)
    private Integer originalPrice;
    
    @Column(name = "it_img1", length = 255)
    private String imageUrl;
    
    @Column(name = "it_flutter_image_url", length = 500)
    private String flutterImageUrl; // Flutter 앱용 정규화된 이미지 URL
    
    @Column(name = "it_kind", nullable = false, length = 20)
    private String productKind; // enum: 'general', 'prescription'
    
    @Column(name = "it_type3", nullable = false)
    private Integer isNewFlag; // 신상품 (0 or 1)
    
    @Column(name = "it_type4", nullable = false)
    private Integer isBestFlag; // 인기(메인) (0 or 1)
    
    @Column(name = "it_stock_qty", nullable = false)
    private Integer stock;  // 재고수량
    
    @Column(name = "it_use_avg", precision = 2, scale = 1)
    private BigDecimal rating; // 평균 평점 (decimal(2,1))
    
    @Column(name = "it_use_cnt", nullable = false)
    private Integer reviewCount; // 리뷰 개수
    
    @Column(name = "it_time", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "it_update_time", nullable = false)
    private LocalDateTime updatedAt;
    
    // 상세 정보 필드들
    @Column(name = "it_basic", columnDefinition = "TEXT")
    private String basicDescription; // 간단 설명
    
    @Column(name = "it_prescription", length = 255)
    private String prescription; // 처방단위
    
    @Column(name = "it_takeway", columnDefinition = "TEXT")
    private String takeway; // 복용방법
    
    @Column(name = "it_package", length = 255)
    private String packageInfo; // 패키지구성
    
    @Column(name = "it_point")
    private Integer point; // 적립 포인트
    
    @Column(name = "it_point_type")
    private Integer pointType; // 포인트 타입 (1: 고정, 2: 비율)
    
    @Column(name = "it_option_subject", length = 255)
    private String optionSubject; // 옵션 선택 표시명
    
    @Column(name = "it_org_id", length = 20)
    private String itOrgId; // 원본 제품 ID (파생 제품인 경우)
    
    @Column(name = "it_use", nullable = false)
    private Integer useFlag; // 사용 여부 (0 or 1)
    
    // 배송비 관련 필드
    @Column(name = "it_sc_type", nullable = false)
    private Byte itScType; // 배송비 유형 (0: 쇼핑몰 기본설정, 1: 무료배송, 2: 조건부 무료배송, 3: 유료배송, 4: 수량별 부과)
    
    @Column(name = "it_sc_method", nullable = false)
    private Byte itScMethod; // 배송비 결제 방법 (0: 선불, 1: 착불, 2: 사용자선택)
    
    @Column(name = "it_sc_price", nullable = false)
    private Integer itScPrice; // 기본 배송비 금액
    
    @Column(name = "it_sc_minimum", nullable = false)
    private Integer itScMinimum; // 무료배송 최소 금액 (조건부 무료배송용)
    
    @Column(name = "it_sc_qty", nullable = false)
    private Integer itScQty; // 수량별 부과 단위 (수량별 부과용)
    
    @Column(name = "it_supply_point")
    private Integer supplyPoint; // 추가옵션용 고정 포인트
    
    // 기본 생성자
    public Product() {}
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    
    public Integer getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(Integer originalPrice) { this.originalPrice = originalPrice; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getFlutterImageUrl() { return flutterImageUrl; }
    public void setFlutterImageUrl(String flutterImageUrl) { this.flutterImageUrl = flutterImageUrl; }
    
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    
    public String getProductKind() { return productKind; }
    public void setProductKind(String productKind) { this.productKind = productKind; }
    
    public Integer getIsNewFlag() { return isNewFlag; }
    public void setIsNewFlag(Integer isNewFlag) { this.isNewFlag = isNewFlag; }
    
    public Integer getIsBestFlag() { return isBestFlag; }
    public void setIsBestFlag(Integer isBestFlag) { this.isBestFlag = isBestFlag; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }
    
    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public String getBasicDescription() { return basicDescription; }
    public void setBasicDescription(String basicDescription) { this.basicDescription = basicDescription; }
    
    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }
    
    public String getTakeway() { return takeway; }
    public void setTakeway(String takeway) { this.takeway = takeway; }
    
    public String getPackageInfo() { return packageInfo; }
    public void setPackageInfo(String packageInfo) { this.packageInfo = packageInfo; }
    
    public Integer getPoint() { return point; }
    public void setPoint(Integer point) { this.point = point; }
    
    public Integer getPointType() { return pointType; }
    public void setPointType(Integer pointType) { this.pointType = pointType; }
    
    public String getOptionSubject() { return optionSubject; }
    public void setOptionSubject(String optionSubject) { this.optionSubject = optionSubject; }
    
    public String getItOrgId() { return itOrgId; }
    public void setItOrgId(String itOrgId) { this.itOrgId = itOrgId; }
    
    public Integer getUseFlag() { return useFlag; }
    public void setUseFlag(Integer useFlag) { this.useFlag = useFlag; }
    
    public Byte getItScType() { return itScType; }
    public void setItScType(Byte itScType) { this.itScType = itScType; }
    
    public Byte getItScMethod() { return itScMethod; }
    public void setItScMethod(Byte itScMethod) { this.itScMethod = itScMethod; }
    
    public Integer getItScPrice() { return itScPrice; }
    public void setItScPrice(Integer itScPrice) { this.itScPrice = itScPrice; }
    
    public Integer getItScMinimum() { return itScMinimum; }
    public void setItScMinimum(Integer itScMinimum) { this.itScMinimum = itScMinimum; }
    
    public Integer getItScQty() { return itScQty; }
    public void setItScQty(Integer itScQty) { this.itScQty = itScQty; }
    
    public Integer getSupplyPoint() { return supplyPoint; }
    public void setSupplyPoint(Integer supplyPoint) { this.supplyPoint = supplyPoint; }
    
    /**
     * isNew 플래그를 Boolean으로 변환 (it_type3: 1이면 신상품)
     */
    public Boolean getIsNew() {
        return isNewFlag != null && isNewFlag == 1;
    }
    
    /**
     * isBest 플래그를 Boolean으로 변환 (it_type4: 1이면 인기상품)
     */
    public Boolean getIsBest() {
        return isBestFlag != null && isBestFlag == 1;
    }
}

