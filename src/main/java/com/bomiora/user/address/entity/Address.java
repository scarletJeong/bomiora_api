package com.bomiora.user.address.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "bomiora_shop_order_address")
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private Long id;
    
    @JsonProperty("mb_id")
    @Column(name = "mb_id", nullable = false)
    private String mbId;
    
    @JsonProperty("ad_subject")
    @Column(name = "ad_subject", nullable = false)
    private String subject; // 배송지 이름 (예: "집", "회사")
    
    @JsonProperty("ad_default")
    @Column(name = "ad_default", nullable = false)
    private Integer isDefault = 0; // 기본 배송지 여부 (0 또는 1)
    
    @JsonProperty("ad_name")
    @Column(name = "ad_name", nullable = false)
    private String recipientName; // 수령인 이름
    
    @JsonProperty("ad_tel")
    @Column(name = "ad_tel")
    private String recipientTel; // 수령인 전화번호
    
    @JsonProperty("ad_hp")
    @Column(name = "ad_hp", nullable = false)
    private String recipientHp; // 수령인 휴대폰번호
    
    @JsonProperty("ad_zip1")
    @Column(name = "ad_zip1")
    private String zip1;
    
    @JsonProperty("ad_zip2")
    @Column(name = "ad_zip2")
    private String zip2;
    
    @JsonProperty("ad_addr1")
    @Column(name = "ad_addr1", nullable = false)
    private String address1; // 기본 주소
    
    @JsonProperty("ad_addr2")
    @Column(name = "ad_addr2")
    private String address2; // 상세 주소
    
    @JsonProperty("ad_addr3")
    @Column(name = "ad_addr3")
    private String address3; // 추가 주소
    
    @JsonProperty("ad_jibeon")
    @Column(name = "ad_jibeon")
    private String jibeon; // 지번 주소
    
    // 기본 생성자
    public Address() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public Integer getIsDefault() { return isDefault; }
    public void setIsDefault(Integer isDefault) { this.isDefault = isDefault; }
    
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    
    public String getRecipientTel() { return recipientTel; }
    public void setRecipientTel(String recipientTel) { this.recipientTel = recipientTel; }
    
    public String getRecipientHp() { return recipientHp; }
    public void setRecipientHp(String recipientHp) { this.recipientHp = recipientHp; }
    
    public String getZip1() { return zip1; }
    public void setZip1(String zip1) { this.zip1 = zip1; }
    
    public String getZip2() { return zip2; }
    public void setZip2(String zip2) { this.zip2 = zip2; }
    
    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }
    
    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }
    
    public String getAddress3() { return address3; }
    public void setAddress3(String address3) { this.address3 = address3; }
    
    public String getJibeon() { return jibeon; }
    public void setJibeon(String jibeon) { this.jibeon = jibeon; }
}

