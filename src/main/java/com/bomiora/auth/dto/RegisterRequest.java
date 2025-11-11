package com.bomiora.auth.dto;

/**
 * 회원가입 요청 DTO
 */
public class RegisterRequest {
    private String email;
    private String password; // Flutter에서 평문 비밀번호 전송
    private String name;
    private String phone;
    
    // 기본 생성자
    public RegisterRequest() {}
    
    // 모든 필드를 받는 생성자
    public RegisterRequest(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

