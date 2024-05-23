package org.ict.boot_react.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // React, Vue, Angular 와 어플리케이션을 합쳐서 실행할 때, cross origin 문제 발생 처리가 목적임
    // 참고 : 하나의 웹 어플리케이션 구동시 port 한개로 구동이 원칙임
    // React 어플리케이션 port 에서 요청 <----> Boot 어플리케이션 port 응답

    // WebMvcConfigurer 인터페이스 오버라이딩한 메소드로, CORS 관련 설정을 추가하는데 사용
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 설정을 추가한다는 의미 (/** 는 모든 경로를 의미함)
        registry.addMapping("/**")
                // 오직 "http://localhost:3000" 이 오리진에서 오는 요청만 허용한다는 의미임
                // 개발 단계에서는 프론트앤드 서버의 주소가 됨
                .allowedOrigins("http://localhost:3000")
                // 해당 오리진에서 허용할 http 메소드를 지정함
                // GET, POST, PUT, DELETE, HEAD, OPTIONS 메소드만 허용함
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                // 모든 HTTP 해더를 요청에서 허용함
                .allowedHeaders("*")
                // 쿠키나 인장과 관련된 정보를 포함한 요청을 허용함
                .allowCredentials(true);
    }

}

