package com.bomiora.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bomiora_member") // 실제 테이블명
public class User {
    @Id
    @Column(name = "mb_no") // Primary Key 추가
    private Long id;

    @Column(name = "mb_email", unique = true, nullable = false)
    private String email;

    @Column(name = "mb_password", nullable = false)
    private String password;

    @Column(name = "mb_name", nullable = false)
    private String name;

    @Column(name = "mb_phone")
    private String phone;

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

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
}