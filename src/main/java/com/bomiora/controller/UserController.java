package com.bomiora.controller;

import com.bomiora.entity.User;
import com.bomiora.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.spec.KeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/api")
@Tag(name = "User API", description = "사용자 관련 API")
@CrossOrigin(origins = {"http://localhost:5000", "http://localhost:5001"}) // CORS 설정 추가
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    @Operation(summary = "서버 상태 확인", description = "Spring Boot 서버가 정상적으로 실행 중인지 확인합니다.")
    @ApiResponse(responseCode = "200", description = "서버 정상 실행")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Spring Boot 서버 실행 중!");
    }

    @GetMapping("/users")
    @Operation(summary = "사용자 목록 조회", description = "모든 사용자 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "사용자 목록 조회 성공")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // 로그인 메서드 수정
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("로그인 시도: " + request.getEmail());

            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            System.out.println("사용자 찾기 결과: " + userOpt.isPresent());

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                System.out.println("DB 비밀번호: " + user.getPassword());
                System.out.println("입력 비밀번호: " + request.getPassword());

                // jjy _ 임시로 평문 비교 _ 실제 운영에서는 올바른 해시 검증을 사용해야 함
                boolean passwordMatch = "test".equals(request.getPassword());
                System.out.println("비밀번호 일치 (임시): " + passwordMatch);

                if (passwordMatch) {
                    user.setLastLoginAt(LocalDateTime.now());
                    userRepository.save(user);

                    return ResponseEntity.ok(new LoginResponse(true, user, "로그인 성공"));
                }
            }

            return ResponseEntity.ok(new LoginResponse(false, null, "이메일 또는 비밀번호가 올바르지 않습니다."));
        } catch (Exception e) {
            System.out.println("로그인 오류: " + e.getMessage());
            return ResponseEntity.ok(new LoginResponse(false, null, "로그인 중 오류가 발생했습니다."));
        }
    }

    @PostMapping("/auth/register")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    })
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.ok(new RegisterResponse(false, null, "이미 존재하는 이메일입니다."));
            }

            String hashedPassword = sha256(request.getPassword());
            User user = new User(request.getEmail(), hashedPassword, request.getName(), request.getPhone());
            User savedUser = userRepository.save(user);

            return ResponseEntity.ok(new RegisterResponse(true, savedUser, "회원가입이 완료되었습니다."));
        } catch (Exception e) {
            System.out.println("회원가입 오류: " + e.getMessage());
            return ResponseEntity.ok(new RegisterResponse(false, null, "회원가입 중 오류가 발생했습니다."));
        }
    }

    // SHA256 해시화 메서드
    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("SHA256 해시화 오류", e);
        }
    }

    // 내부 클래스들 (기존과 동일)
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        private String email;
        private String password;
        private String name;
        private String phone;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }

    public static class LoginResponse {
        private boolean success;
        private User user;
        private String message;

        public LoginResponse(boolean success, User user, String message) {
            this.success = success;
            this.user = user;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public User getUser() { return user; }
        public String getMessage() { return message; }
    }

    public static class RegisterResponse {
        private boolean success;
        private User user;
        private String message;

        public RegisterResponse(boolean success, User user, String message) {
            this.success = success;
            this.user = user;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public User getUser() { return user; }
        public String getMessage() { return message; }
    }


    // PHP password_hash()와 동일한 방식으로 해시 생성
    private String phpPasswordHash(String password) {
        try {
            // PBKDF2 설정 (PHP의 기본 설정)
            String algorithm = "PBKDF2WithHmacSHA256";
            int iterations = 12000; // PHP 기본값
            int keyLength = 32; // 256 bits

            // Salt 생성 (랜덤)
            byte[] salt = new byte[16];
            java.security.SecureRandom.getInstanceStrong().nextBytes(salt);

            // PBKDF2로 해시 생성
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // PHP 형식으로 인코딩: sha256:iterations:salt:hash
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            return String.format("sha256:%d:%s:%s", iterations, saltBase64, hashBase64);
        } catch (Exception e) {
            throw new RuntimeException("PHP password_hash 오류", e);
        }
    }

    // PHP password_verify()와 동일한 방식으로 비밀번호 검증
    private boolean phpPasswordVerify(String password, String hash) {
        try {
            System.out.println("=== 비밀번호 검증 디버깅 ===");
            System.out.println("원본 해시: " + hash);

            // 해시 형식 파싱: sha256:iterations:salt:hash
            String[] parts = hash.split(":");
            if (parts.length != 4 || !parts[0].equals("sha256")) {
                System.out.println("해시 형식이 올바르지 않음");
                return false;
            }

            int iterations = Integer.parseInt(parts[1]);
            String saltBase64 = parts[2];
            String hashBase64 = parts[3];

            System.out.println("Iterations: " + iterations);
            System.out.println("Salt (Base64): " + saltBase64);
            System.out.println("Hash (Base64): " + hashBase64);

            // Base64 디코딩
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            byte[] storedHash = Base64.getDecoder().decode(hashBase64);

            System.out.println("Salt 길이: " + salt.length);
            System.out.println("Stored Hash 길이: " + storedHash.length);

            // 여러 알고리즘과 길이 조합 시도
            String[] algorithms = {"PBKDF2WithHmacSHA1", "PBKDF2WithHmacSHA256", "PBKDF2WithHmacSHA512"};
            int[] keyLengths = {storedHash.length, 16, 20, 24, 32}; // 다양한 길이 시도

            for (String algorithm : algorithms) {
                for (int keyLength : keyLengths) {
                    try {
                        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength * 8);
                        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
                        byte[] computedHash = factory.generateSecret(spec).getEncoded();

                        System.out.println("알고리즘: " + algorithm + ", 길이: " + keyLength + ", 결과 길이: " + computedHash.length);
                        System.out.println("Computed Hash (hex): " + bytesToHex(computedHash));

                        if (java.util.Arrays.equals(storedHash, computedHash)) {
                            System.out.println("해시 일치! 알고리즘: " + algorithm + ", 길이: " + keyLength);
                            return true;
                        }
                    } catch (Exception e) {
                        System.out.println("알고리즘 " + algorithm + ", 길이 " + keyLength + " 실패: " + e.getMessage());
                    }
                }
            }

            return false;
        } catch (Exception e) {
            System.out.println("비밀번호 검증 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 바이트 배열을 16진수 문자열로 변환
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}