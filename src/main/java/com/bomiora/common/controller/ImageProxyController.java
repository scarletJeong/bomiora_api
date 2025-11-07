package com.bomiora.common.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api/proxy")
public class ImageProxyController {
    
    /**
     * 이미지 프록시 엔드포인트
     * 외부 이미지 URL을 프록시하여 CORS 문제 해결
     * 
     * GET /api/proxy/image?url=https://bomiora0.mycafe24.com/data/editor/...
     */
    @GetMapping("/image")
    public ResponseEntity<byte[]> proxyImage(@RequestParam String url) {
        try {
            // URL 유효성 검사
            if (!url.startsWith("https://bomiora0.mycafe24.com") && 
                !url.startsWith("https://bomiora.kr") &&
                !url.startsWith("https://www.bomiora.kr") &&
                !url.startsWith("http://bomiora.kr") &&
                !url.startsWith("http://www.bomiora.kr")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            // 외부 이미지 다운로드
            URL imageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Content-Type 가져오기
                String contentType = connection.getContentType();
                
                // 이미지 데이터 읽기
                InputStream inputStream = connection.getInputStream();
                byte[] imageBytes = inputStream.readAllBytes();
                inputStream.close();
                
                // 응답 헤더 설정
                HttpHeaders headers = new HttpHeaders();
                if (contentType != null) {
                    headers.setContentType(MediaType.parseMediaType(contentType));
                } else {
                    // 파일 확장자로 Content-Type 추정
                    if (url.toLowerCase().endsWith(".png")) {
                        headers.setContentType(MediaType.IMAGE_PNG);
                    } else if (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".jpeg")) {
                        headers.setContentType(MediaType.IMAGE_JPEG);
                    } else if (url.toLowerCase().endsWith(".gif")) {
                        headers.setContentType(MediaType.IMAGE_GIF);
                    } else {
                        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    }
                }
                
                // CORS 헤더는 CorsConfig에서 전역적으로 처리됨 (중복 방지)
                
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.status(responseCode).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

