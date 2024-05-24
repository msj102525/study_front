package org.ict.boot_react.security.config;

import jakarta.servlet.http.HttpServletResponse;
import org.ict.boot_react.member.model.service.MemberService;
import org.ict.boot_react.security.handler.CustomLogoutHandler;
import org.ict.boot_react.security.jwt.filter.JWTFilter;
import org.ict.boot_react.security.jwt.filter.LoginFilter;
import org.ict.boot_react.security.jwt.util.JWTUtil;
import org.ict.boot_react.security.model.service.RefreshTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // 스프링부트의 설정 담당하는 클래스임을 나타내는 어노테이션
@EnableWebSecurity  // 스프링 시큐리티 설정을 활성화함
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshTokenService refreshService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;


    // 생성자를 통한 의존성 주입으로, 필요한 서비스와 설정을 초기화함
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
    RefreshTokenService refreshService, MemberService memberService, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.refreshService = refreshService;
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    // 인증(Authentication) 관리자를 스프링 컨테이너에  Bean으로 등록함. 인증 과정에서 중요한 클래스임
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // HTTP 관련 보안 설정을 정의함
    // SecurityFilterChain 을 Bean 으로 등록하고, http 요청에 대한 보안을 구성함
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // csrf(Cross-Site-Request-Forgery), form login, http basic 인증을 비활성화 함
                // 예 : <img src="http://kakao.developer.com/login/kakao.png> 사용 못하게 함
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable) // form 태그로 submit 해서 오는 로그인 사용 못 하게함
                .httpBasic(AbstractHttpConfigurer::disable) // 시큐리티 제공 로그인을 사용 못하게 함
                // HTTP 요청에 대한 접근 권한을 설정함
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/notices").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/notices").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/notices").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/boards/board").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/boards").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/boards").hasRole("USER")
                        // '/notice' path로 post 요청에 대해서는 'ADMIN' 롤 정보를 가진 사용자만 가능하다.
                        // 회원서비스의 마이페이지(GET), 내정보 수정(PUT), 탈퇴(PUT | DELETE) 로그인해야 접근 가능 => "USER"롤 가짐
                        .requestMatchers("/api/auth/login", "/members/**", "/notices/**", "/board**", "/logout").permitAll()
                        // 위의 path 글은 인증없이 접근 가능하다.
                        .anyRequest().authenticated())  // 그 외의 모든 요청은 인증을 요구한다
                // JWTFilter 와 LoginFilter를 필터 체인에 등록함 => SecurityConfig
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
                .addFilterAt(new LoginFilter(memberService, refreshService,
                        authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // 로그아웃 처리를 커스터마이징함
                .logout(logout -> logout
                        .addLogoutHandler(new CustomLogoutHandler(jwtUtil, memberService, refreshService))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }))
                // 세션 정책을 STATELESS 로 설정하고, 세션을 사용하지 않는 것을 명시함
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
