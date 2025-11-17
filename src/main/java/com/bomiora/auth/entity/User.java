package com.bomiora.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_member") // 실제 테이블명
public class User {
    @Id
    @Column(name = "mb_no") // Primary Key 추가
    private Long id;

    @JsonProperty("mb_id") // JSON 변환 시 mb_id로 출력
    @Column(name = "mb_id", unique = true) // 회원 아이디 (문자열)
    private String mbId;

    @Column(name = "mb_email", unique = true, nullable = false)
    private String email;

    @Column(name = "mb_password", nullable = false)
    private String password;

    @Column(name = "mb_name", nullable = false)
    private String name;

    @JsonProperty("mb_nick") // JSON 변환 시 mb_nick으로 출력
    @Column(name = "mb_nick")
    private String nickname; // 닉네임

    @Column(name = "mb_phone")
    private String phone;
    
    @JsonProperty("mb_hp") // JSON 변환 시 mb_hp로 출력
    @Column(name = "mb_hp")
    private String mbHp; // 전화번호 (mb_hp 필드)

    @Column(name = "mb_datetime")
    private LocalDateTime createdAt;

    @Column(name = "mb_today_login")
    private LocalDateTime lastLoginAt;

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMbId() { return mbId; }
    public void setMbId(String mbId) { this.mbId = mbId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getMbHp() { return mbHp; }
    public void setMbHp(String mbHp) { this.mbHp = mbHp; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
}