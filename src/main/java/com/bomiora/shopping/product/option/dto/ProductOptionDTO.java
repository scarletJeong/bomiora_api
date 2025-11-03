package com.bomiora.shopping.product.option.dto;

public class ProductOptionDTO {
    
    private String id; // io_id
    private String productId; // it_id
    private String optionName; // 옵션명 (숫자 앞까지)
    private Integer days; // 체험일수 (숫자 부분)
    private Integer price; // 옵션 가격
    private Integer stock; // 재고
    private String type; // 옵션 타입
    
    // 기본 생성자
    public ProductOptionDTO() {}
    
    // 전체 생성자
    public ProductOptionDTO(String id, String productId, String optionName, 
                          Integer days, Integer price, Integer stock, String type) {
        this.id = id;
        this.productId = productId;
        this.optionName = optionName;
        this.days = days;
        this.price = price;
        this.stock = stock;
        this.type = type;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public String getOptionName() { return optionName; }
    public void setOptionName(String optionName) { this.optionName = optionName; }
    
    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}

