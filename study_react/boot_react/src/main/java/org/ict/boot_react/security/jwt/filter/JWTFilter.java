package org.ict.boot_react.security.jwt.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.security.jwt.model.CustomUserDetails;
import org.ict.boot_react.member.model.dto.MemberDto;
import org.ict.boot_react.security.jwt.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j  // Lombok의 Slf4j 어노테이션 사용해서 로깅 기능을 자동 추가함
// Spring Security 의 OncePerRequesetFilter 를 상속받음, 모든 요청에 대해 한번씩 실행되는 필터를 정의
public class JWTFilter extends OncePerRequestFilter {
    // JWT 관련 유틸리티 메서드를 제공하는 JWTUtil 의 인스턴스를 멤버로 선언함
    private final JWTUtil jwtUtil;

    // 생성자를 통해 멤버변수 의존성 주입함
    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 필터의 주요 로직을 구현하는 메서드임
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청(request)에서 'Authorization' 헤더를 추출함
        String authorization = request.getHeader("Authorization");

        String requestURI = request.getRequestURI();
        if ("/reissue".equals(requestURI)) { // 클라이언트가 토큰 재발행 요청을 했다면
            filterChain.doFilter(request, response); // 해당 요청을 처리할 컨트롤러 메서드로 전달해라. "/reissue" 에게로 보내라
            return;
        }
        if ("/logout".equals(requestURI)) {
            // 찾아갈 대상으로 그대로 전달함
            filterChain.doFilter(request, response);
            // SecurityConfig 에 설정된 LogoutHandler 를 상속받아 만든 CustomLogoutHandler 가 작동됨
            return;
        }

        // 토큰 확인이 필요없는 요청 (로그인하지 않고 이용하는 서비스 url)은 그대로 다음 단계로 넘김
        // 'Authorization' 이 헤더에 없거나 Bearer 토큰이 아니면 요청을 계속 진행함
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            // SecurityConfig 에 설정된 permitAll() 에 등록된 url 이면, 해당 컨트롤러 메서드로 전달됨
            // 토큰이 필요한 (로그인해야 요청되는) 요청은 에러를 클라이언트에게 보냄
            return;
        }

        // Bearer 토큰에서 JWT 를 추출함 (토큰 정보가 request 헤더에 있는 경우)
        // authorization 이 가진 값 : "Bearer 토큰문자열"
        String token = authorization.split(" ")[1];

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        if (jwtUtil.isTokenExpired(token)) {
            // response body
            PrintWriter writer = response.getWriter();
            writer.println("access token expired");
            // response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // token 에서 category (access, refresh) 추출
        String category = jwtUtil.getCategoryFromToken(token);
        // 토큰 category 가 access 가 아니라면 만료된 토큰으로 판단함
        if (!category.equals("access")) {
            // response body
            PrintWriter writer = response.getWriter();
            writer.println("invalid access token expired");

            // response status code
            // 응답 코드를 front 와 맞추는 부분 401 에러 외 다른 상태코드로 맞추면
            // 리프레시 토큰 발급 체크를 좀 더 빠르게 할 수 있음
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 위의 조건들에 해당되지 않으면, 정상적인 만료되지 않은 access 토큰으로 요청이 왔다면
        // 토큰에서 사용자 아이디 (또는 이메일), 관리자 여부 추출함
        String userId = jwtUtil.getUserIdFromToken(token);
        String adminYN = jwtUtil.getAdminFromToken(token);

        // 인증에 사용할 임시 Member 객체를 생성하고, 추출한 정보 저장함
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId(userId);
        memberDto.setAdminYN(adminYN);
        memberDto.setUserPwd("tempPassword");   // 실제 인증에서 사용되지 않는 임시 비밀번호 설정함

        // MemberDto(회원정보) 객체를 기반으로 한 CustomUserDetail 객체를 생성
        // Security 가 제공하는 UserDetails 를 상속받은 인증용 클래스로 만든 것 : security.jwt.model.CustomUserDetails
        CustomUserDetails customUserDetails = new CustomUserDetails(memberDto);


        // Spring Security 의 Authentication 객체를 생성하고, SecurityContext 에 등록 설정함
        // 이것으로 해당 요청에 대한 사용자 인증이 완료됨
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 엑세스 토큰(로그인 상태 확인)이 이상이 없다면 처리할 내용임

        // 필터 체인을 계속 진행함
        filterChain.doFilter(request, response); // 실제 컨트롤러 요청(로그인 상태에서 작동될 요청)을 넘김
    }
}
