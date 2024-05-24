package org.ict.boot_react.security.handler;//package org.ict.boot_react.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.member.model.dto.MemberDto;
import org.ict.boot_react.member.model.service.MemberService;
import org.ict.boot_react.security.jpa.entity.RefreshToken;
import org.ict.boot_react.security.model.service.RefreshTokenService;
import org.ict.boot_react.security.jwt.util.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Optional;
import java.util.UUID;

@Slf4j // 로그 출력 기능이 필요할 때 사용하는 어노테이션
public class CustomLogoutHandler implements LogoutHandler {
    private final JWTUtil jwtUtil;
    private final MemberService memberService;
    private final RefreshTokenService refreshService;

    // 의존성 주입을 위한 생성자
    public CustomLogoutHandler(JWTUtil jwtUtil, MemberService memberService, RefreshTokenService refreshService) {
        this.jwtUtil = jwtUtil;
        this.memberService = memberService;
        this.refreshService = refreshService;
    } // 의존성 주입을 위한 생성자

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 요청 헤더에서 'Authorization' 값을 추출합니다.
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            // 'Bearer ' 다음부터 시작하는 실제 토큰 값을 추출합니다.
            String token = authorization.split(" ")[1];
            // 토큰에서 사용자의 이메일(사용자명)을 추출합니다.
            String userId = jwtUtil.getUserIdFromToken(token);
            // 사용자의 아이디를 통해 사용자 정보를 조회합니다.
            MemberDto member = memberService.selectMember(userId);
            if (member != null) {
                // 해당 사용자의 리프레시 토큰을 데이터베이스에서 조회합니다.
                Optional<RefreshToken> refresh = refreshService.findByUserId(UUID.fromString(member.getUserId()));
                if (refresh.isPresent()) {
                    RefreshToken refreshToken = refresh.get();
                    // 리프레시 토큰을 데이터베이스에서 삭제합니다.
                    refreshService.deleteByRefresh(refreshToken.getTokenValue());
                }
            }
        }
        // 클라이언트에게 로그아웃 성공 응답을 보냅니다.
        response.setStatus(HttpServletResponse.SC_OK);
    } // method
} // class
