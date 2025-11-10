package com.bomiora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * CORS(Cross-Origin Resource Sharing) 설정
 * Flutter 웹 앱에서 Spring Boot API를 호출할 수 있도록 허용
 */
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 모든 Origin 허용 (개발 환경)
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 모든 도메인 허용
        
        // 특정 Origin만 허용 (배포 환경에서 권장)
        // config.setAllowedOrigins(Arrays.asList(
        //     "http://localhost:5000",           // Flutter 웹 개발 서버
        //     "http://localhost:3000",           // React 개발 서버 (있을 경우)
        //     "https://bomiora.net",             // 실제 도메인
        //     "https://www.bomiora.net"
        // ));
        
        // 허용할 HTTP 메서드
        config.setAllowedMethods(Arrays.asList(
            "GET", 
            "POST", 
            "PUT", 
            "DELETE", 
            "PATCH", 
            "OPTIONS"
        ));
        
        // 허용할 헤더
        config.setAllowedHeaders(Arrays.asList(
            "Origin",
            "Content-Type",
            "Accept",
            "Authorization",
            "X-Requested-With",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers",
            "User-Agent"
        ));
        
        // 노출할 헤더
        config.setExposedHeaders(Arrays.asList(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Content-Disposition"
        ));
        
        // preflight 요청 캐시 시간 (초)
        config.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 적용
        
        return new CorsFilter(source);
    }
}
