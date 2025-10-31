package com.bomiora.user.questionnaire.dto;

public class ApiResponseDto<T> {
    
    private boolean success;
    private String message;
    private T data;
    private String error;
    
    // 기본 생성자
    public ApiResponseDto() {}
    
    // 모든 필드를 포함한 생성자
    public ApiResponseDto(boolean success, String message, T data, String error) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.error = error;
    }
    
    // Getter 메서드들
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public String getError() { return error; }
    
    // Setter 메서드들
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }
    public void setError(String error) { this.error = error; }
    
    // 정적 팩토리 메서드들
    public static <T> ApiResponseDto<T> success(T data) {
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setSuccess(true);
        response.setMessage("성공");
        response.setData(data);
        return response;
    }
    
    public static <T> ApiResponseDto<T> success(String message, T data) {
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
    
    public static <T> ApiResponseDto<T> error(String message) {
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setError(message);
        return response;
    }
    
    public static <T> ApiResponseDto<T> error(String message, String error) {
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setError(error);
        return response;
    }
}
