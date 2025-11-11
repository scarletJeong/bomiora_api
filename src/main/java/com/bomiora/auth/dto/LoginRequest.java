package com.bomiora.auth.dto;

/**
 * 로그인 요청 DTO
 */
public class LoginRequest {
    private String email;
    private String password; // Flutter에서 평문 비밀번호 전송 (HTTPS로 보호)
    
    // 기본 생성자
    public LoginRequest() {}
    
    // 모든 필드를 받는 생성자
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}

