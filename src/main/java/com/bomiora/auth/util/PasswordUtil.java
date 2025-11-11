package com.bomiora.auth.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * 비밀번호 암호화 유틸리티
 * - PBKDF2 (PHP password_hash 호환)
 * - MySQL PASSWORD() 함수 호환
 */
public class PasswordUtil {
    
    /**
     * PBKDF2 방식으로 저장된 비밀번호 검증 (PHP password_hash 호환)
     * 
     * DB 저장 형식: sha256:12000:salt_base64:hash_base64
     * PHP: hash_pbkdf2('sha256', $password, $salt, 12000, 24, true)
     * 
     * @param plainPassword 평문 비밀번호
     * @param storedHash DB에 저장된 해시 (sha256:12000:salt:hash 형식)
     * @return 비밀번호 일치 여부
     */
    public static boolean verifyPBKDF2Password(String plainPassword, String storedHash) {
        try {
            // 1. 저장된 해시 파싱
            String[] parts = storedHash.split(":");
            if (parts.length != 4) {
                System.out.println("❌ [PBKDF2] 잘못된 해시 형식: " + storedHash);
                return false;
            }
            
            String algorithm = parts[0]; // "sha256"
            int iterations = Integer.parseInt(parts[1]); // "12000"
            String saltBase64 = parts[2]; // Base64 인코딩된 Salt
            String hashBase64 = parts[3]; // Base64 인코딩된 Hash
            
            System.out.println("📋 [PBKDF2] 알고리즘: " + algorithm);
            System.out.println("📋 [PBKDF2] 반복 횟수: " + iterations);
            System.out.println("📋 [PBKDF2] Salt (Base64 문자열): " + saltBase64);
            System.out.println("📋 [PBKDF2] 저장된 Hash (Base64): " + hashBase64);
            
            // 2. Salt는 Base64 문자열 그대로 사용 (PHP와 동일)
            // PHP의 hash_pbkdf2()는 Salt를 문자열로 받음 (디코딩 안 함)
            byte[] salt = saltBase64.getBytes("UTF-8");
            System.out.println("📋 [PBKDF2] Salt 길이: " + salt.length + " bytes");
            
            // 3. 저장된 해시 디코딩하여 길이 확인
            byte[] storedHashBytes = Base64.getDecoder().decode(hashBase64);
            int keyLength = storedHashBytes.length; // PHP에서 생성된 해시의 실제 길이
            System.out.println("📋 [PBKDF2] Hash 길이: " + keyLength + " bytes");
            
            // 4. 평문 비밀번호를 PBKDF2로 해싱
            // PHP: hash_pbkdf2('sha256', $password, $salt, 12000, 24, true)
            String pbkdf2Algorithm = "PBKDF2WithHmacSHA256";
            SecretKeyFactory factory = SecretKeyFactory.getInstance(pbkdf2Algorithm);
            KeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt, iterations, keyLength * 8); // bytes를 bits로 변환
            byte[] hash = factory.generateSecret(spec).getEncoded();
            
            // 5. Base64 인코딩
            String computedHashBase64 = Base64.getEncoder().encodeToString(hash);
            System.out.println("🔐 [PBKDF2] 계산된 Hash (Base64): " + computedHashBase64);
            System.out.println("🔐 [PBKDF2] 계산된 Hash 길이: " + hash.length + " bytes");
            
            // 6. 비교
            boolean match = hashBase64.equals(computedHashBase64);
            System.out.println("✅ [PBKDF2] 일치 여부: " + match);
            
            return match;
            
        } catch (Exception e) {
            System.out.println("❌ [PBKDF2] 검증 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * MySQL PASSWORD() 함수와 동일한 방식으로 비밀번호 해싱
     * @param password 원본 비밀번호
     * @return MySQL PASSWORD() 함수와 동일한 해시값 (41자, * prefix 포함)
     */
    public static String mysqlPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            
            // 1단계: SHA1 해싱
            byte[] firstHash = md.digest(password.getBytes("UTF-8"));
            
            // 2단계: SHA1 해시를 다시 SHA1 해싱 (double hash)
            md.reset();
            byte[] secondHash = md.digest(firstHash);
            
            // 3단계: HEX 인코딩
            String hexString = bytesToHex(secondHash);
            
            // 4단계: '*' prefix 추가
            return "*" + hexString.toUpperCase();
            
        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }
    
    /**
     * byte 배열을 HEX 문자열로 변환
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    /**
     * 테스트용 메인 메서드
     */
    public static void main(String[] args) {
        // MySQL PASSWORD() 테스트
        String password1 = "test1234";
        String hash1 = mysqlPassword(password1);
        System.out.println("Password: " + password1);
        System.out.println("MySQL PASSWORD() Hash: " + hash1);
        // 결과: *A4B6157319038724E3560894F7F932C8886EBFCF
        
        System.out.println("\n--- PBKDF2 테스트 ---");
        // PBKDF2 테스트
        String password2 = "1161e6ffd3637b302a5cd74076283a7bd1fc20d3"; // SHA1 해시된 값
        String storedHash = "sha256:12000:6qkfIdTvybGaJWsUZg+kUZ0H3o0WX7Mp:Xm4YCLsUzAr2Zn+XvdYQT+EUuHAqqMPw";
        boolean match = verifyPBKDF2Password(password2, storedHash);
        System.out.println("비밀번호 일치: " + match);
    }
}

