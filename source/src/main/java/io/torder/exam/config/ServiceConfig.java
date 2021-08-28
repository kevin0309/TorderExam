package io.torder.exam.config;

import io.torder.exam.security.JwtObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

/**
 * 일반적인 설정을 정의하는 클래스
 */
@Configuration
public class ServiceConfig {

    /**
     * 인코딩 charset은 UTF-8로 고정
     */
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.toString());
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    /**
     * JwtObject를 빈으로 등록
     */
    @Bean
    public JwtObject jwtObject(JwtTokenConfig jwtTokenConfig) {
        return new JwtObject(jwtTokenConfig.getIssuer(), jwtTokenConfig.getClientSecret(), jwtTokenConfig.getExpirySeconds());
    }
}
