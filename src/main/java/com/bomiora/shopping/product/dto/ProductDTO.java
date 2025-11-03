package com.bomiora.shopping.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {
    
    private String id;
    
    private String name;
    
    private String description;
    
    private Integer price;
    
    @JsonProperty("originalPrice")
    private Integer originalPrice;
    
    @JsonProperty("imageUrl")
    private String imageUrl;
    
    @JsonProperty("categoryId")
    private String categoryId;
    
    @JsonProperty("categoryName")
    private String categoryName;
    
    @JsonProperty("productKind")
    private String productKind;
    
    @JsonProperty("isNew")
    private Boolean isNew;
    
    @JsonProperty("isBest")
    private Boolean isBest;
    
    private Integer stock;
    
    private Double rating;
    
    @JsonProperty("reviewCount")
    private Integer reviewCount;
    
    // 상세 정보를 포함하기 위한 Map (additionalInfo)
    private java.util.Map<String, Object> additionalInfo;
    
    // 기본 생성자
    public ProductDTO() {}
    
    // 전체 생성자
    public ProductDTO(String id, String name, String description, Integer price, 
                     Integer originalPrice, String imageUrl, String categoryId,
                     String categoryName, String productKind, Boolean isNew,
                     Boolean isBest, Integer stock, Double rating, Integer reviewCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.originalPrice = originalPrice;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productKind = productKind;
        this.isNew = isNew;
        this.isBest = isBest;
        this.stock = stock;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
    
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
    
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    
    public String getProductKind() { return productKind; }
    public void setProductKind(String productKind) { this.productKind = productKind; }
    
    public Boolean getIsNew() { return isNew; }
    public void setIsNew(Boolean isNew) { this.isNew = isNew; }
    
    public Boolean getIsBest() { return isBest; }
    public void setIsBest(Boolean isBest) { this.isBest = isBest; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
    
    public java.util.Map<String, Object> getAdditionalInfo() { return additionalInfo; }
    public void setAdditionalInfo(java.util.Map<String, Object> additionalInfo) { this.additionalInfo = additionalInfo; }
}

