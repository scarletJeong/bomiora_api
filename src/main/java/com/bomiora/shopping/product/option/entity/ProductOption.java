package com.bomiora.shopping.product.option.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bomiora_shop_item_option")
public class ProductOption {
    
    @Id
    @Column(name = "io_id", nullable = false, length = 255)
    private String id; // io_id 형식: 옵션명 + 숫자 (예: "체험일수30", "체험일수60")
    
    @Column(name = "it_id", nullable = false, length = 20)
    private String productId;
    
    @Column(name = "io_price", nullable = false)
    private Integer price; // 옵션 가격
    
    @Column(name = "io_stock_qty", nullable = false)
    private Integer stock; // 옵션 재고
    
    @Column(name = "io_type", length = 1)
    private String type; // 옵션 타입
    
    @Column(name = "io_use", nullable = false)
    private Integer useFlag; // 사용 여부 (0 or 1)
    
    // 기본 생성자
    public ProductOption() {}
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Integer getUseFlag() { return useFlag; }
    public void setUseFlag(Integer useFlag) { this.useFlag = useFlag; }
    
    /**
     * io_id에서 단계 추출 (첫 번째 숫자 앞까지)
     * 예: "디톡스플러스3일(6포)" -> "디톡스플러스"
     *     "디톡스6일(12포)" -> "디톡스"
     */
    public String getOptionName() {
        if (id == null || id.isEmpty()) return "";
        
        // 첫 번째 숫자부터 끝까지 제거하여 숫자 앞까지의 문자열 추출
        String result = id.replaceAll("\\d+.*", "");
        return result;
    }
    
    /**
     * io_id에서 개월수 추출 (숫자 부분)
     * 예: "디톡스플러스30" -> 30, "디톡스플러스210" -> 210
     */
    public Integer getDays() {
        if (id == null || id.isEmpty()) return null;
        
        // 숫자 부분 추출 (첫 번째로 나오는 숫자 그룹)
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+");
        java.util.regex.Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}

