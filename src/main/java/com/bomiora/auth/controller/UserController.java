package com.bomiora.auth.controller;

import com.bomiora.auth.dto.LoginRequest;
import com.bomiora.auth.dto.RegisterRequest;
import com.bomiora.auth.dto.UpdateProfileRequest;
import com.bomiora.auth.entity.User;
import com.bomiora.auth.repository.UserRepository;
import com.bomiora.auth.util.PasswordUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "User API", description = "사용자 관련 API")
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

    // 로그인 메서드 - MySQL PASSWORD() 함수 방식과 호환
    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 처리 완료 (성공/실패 여부는 응답 body 확인)"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("🔐 [LOGIN] 로그인 시도: " + request.getEmail());

            // 1. 이메일로 사용자 조회
            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            
            if (!userOpt.isPresent()) {
                System.out.println("❌ [LOGIN] 사용자를 찾을 수 없음");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "가입된 회원이 아니거나 비밀번호가 틀립니다.");
                return ResponseEntity.ok(errorResponse);
            }

            User user = userOpt.get();
            String storedHash = user.getPassword();
            String sha1PasswordFromFlutter = request.getPassword(); // Flutter에서 SHA1 해시된 값
            
            System.out.println("📋 [LOGIN] DB 저장된 해시: " + storedHash);
            System.out.println("📋 [LOGIN] Flutter에서 받은 SHA1: " + sha1PasswordFromFlutter);

            // 2. DB 저장된 해시 형식 확인
            boolean passwordMatch = false;
            
            if (storedHash.startsWith("sha256:")) {
                // PBKDF2 방식 (PHP password_hash)
                System.out.println("🔍 [LOGIN] PBKDF2 방식으로 검증");
                passwordMatch = PasswordUtil.verifyPBKDF2Password(sha1PasswordFromFlutter, storedHash);
                
            } else if (storedHash.startsWith("*") && storedHash.length() == 41) {
                // MySQL PASSWORD() 방식
                System.out.println("🔍 [LOGIN] MySQL PASSWORD() 방식으로 검증");
                String mysqlHash = PasswordUtil.mysqlPassword(sha1PasswordFromFlutter);
                System.out.println("🔐 [LOGIN] MySQL PASSWORD() 해시: " + mysqlHash);
                passwordMatch = mysqlHash.equals(storedHash);
                
            } else {
                // 알 수 없는 형식
                System.out.println("❌ [LOGIN] 알 수 없는 해시 형식");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "비밀번호 형식 오류");
                return ResponseEntity.ok(errorResponse);
            }

            System.out.println("✅ [LOGIN] 비밀번호 일치 여부: " + passwordMatch);

            if (passwordMatch) {
                // 로그인 성공 - 마지막 로그인 시간 업데이트
                user.setLastLoginAt(LocalDateTime.now());
                userRepository.save(user);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("user", createUserResponse(user));
                response.put("token", "token_" + System.currentTimeMillis()); // JWT 토큰으로 대체 가능
                response.put("message", "로그인 성공");

                System.out.println("✅ [LOGIN] 로그인 성공!");
                return ResponseEntity.ok(response);
            } else {
                System.out.println("❌ [LOGIN] 비밀번호 불일치");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "가입된 회원이 아니거나 비밀번호가 틀립니다.");
                return ResponseEntity.ok(errorResponse);
            }

        } catch (Exception e) {
            System.out.println("❌ [LOGIN] 로그인 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "로그인 중 오류가 발생했습니다.");
            return ResponseEntity.ok(errorResponse);
        }
    }

    @PostMapping("/auth/register")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 처리 완료"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    })
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            System.out.println("📝 [REGISTER] 회원가입 시도: " + request.getEmail());

            // 1. 이메일 중복 확인
            if (userRepository.existsByEmail(request.getEmail())) {
                System.out.println("❌ [REGISTER] 이미 존재하는 이메일");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "이미 존재하는 이메일입니다.");
                return ResponseEntity.ok(errorResponse);
            }

            // 2. Flutter에서 받은 SHA1 해시를 MySQL PASSWORD() 방식으로 해싱
            System.out.println("📋 [REGISTER] Flutter에서 받은 SHA1: " + request.getPassword());
            String mysqlHash = PasswordUtil.mysqlPassword(request.getPassword());
            System.out.println("🔐 [REGISTER] MySQL PASSWORD() 해시: " + mysqlHash);

            // 3. 사용자 생성 및 저장
            User user = new User(request.getEmail(), mysqlHash, request.getName(), request.getPhone());
            User savedUser = userRepository.save(user);

            System.out.println("✅ [REGISTER] 회원가입 성공!");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("user", createUserResponse(savedUser));
            response.put("message", "회원가입이 완료되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("❌ [REGISTER] 회원가입 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "회원가입 중 오류가 발생했습니다.");
            return ResponseEntity.ok(errorResponse);
        }
    }

    @PutMapping("/user/profile")
    @Operation(summary = "프로필 수정", description = "사용자 프로필 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 수정 완료"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        try {
            System.out.println("✏️ [UPDATE PROFILE] 프로필 수정 시도: " + request.getMbId());

            // 1. 사용자 조회 (mb_id로)
            Optional<User> userOpt = userRepository.findByMbId(request.getMbId());
            
            if (!userOpt.isPresent()) {
                System.out.println("❌ [UPDATE PROFILE] 사용자를 찾을 수 없음");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "사용자를 찾을 수 없습니다.");
                return ResponseEntity.ok(errorResponse);
            }

            User user = userOpt.get();
            
            // 2. 정보 업데이트
            if (request.getName() != null && !request.getName().isEmpty()) {
                user.setName(request.getName());
            }
            
            if (request.getNickname() != null) {
                user.setNickname(request.getNickname());
            }
            
            if (request.getPhone() != null) {
                user.setPhone(request.getPhone());
                user.setMbHp(request.getPhone()); // mb_hp도 동일하게 설정
            }
            
            // 3. 저장
            User updatedUser = userRepository.save(user);
            
            System.out.println("✅ [UPDATE PROFILE] 프로필 수정 완료");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("user", createUserResponse(updatedUser));
            response.put("message", "프로필이 수정되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("❌ [UPDATE PROFILE] 프로필 수정 오류: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "프로필 수정 중 오류가 발생했습니다.");
            return ResponseEntity.ok(errorResponse);
        }
    }

    /**
     * User 엔티티를 응답용 Map으로 변환
     */
    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> userResponse = new HashMap<>();
        // mb_id는 실제 DB의 mb_id 컬럼 값 사용 (중요!)
        userResponse.put("mb_id", user.getMbId() != null ? user.getMbId() : user.getEmail());
        userResponse.put("mb_no", user.getId()); // Primary Key
        userResponse.put("mb_email", user.getEmail());
        userResponse.put("email", user.getEmail());
        userResponse.put("mb_name", user.getName());
        userResponse.put("name", user.getName());
        userResponse.put("mb_nick", user.getNickname());
        userResponse.put("nickname", user.getNickname());
        userResponse.put("mb_phone", user.getPhone());
        userResponse.put("phone", user.getPhone());
        userResponse.put("mb_hp", user.getMbHp() != null ? user.getMbHp() : user.getPhone());
        return userResponse;
    }
}
