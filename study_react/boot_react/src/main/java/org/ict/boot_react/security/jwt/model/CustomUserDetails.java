package org.ict.boot_react.security.jwt.model;

import org.ict.boot_react.member.model.dto.MemberDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Spring Security 의 UserDetails 인터페이스를 상속받아서 구현함 : 인증을 위한 객체로 사용됨
@Component
public class CustomUserDetails implements UserDetails {
    private final MemberDto user;

    // 생성자로 의존성 주입
    public CustomUserDetails(MemberDto user) {
        this.user = user;
    }

    // 사용자의 권한 목록을 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자의 adminYN (관리자여부) 값에 따라 ADMIN 또는 USER 권한을 부여함
        // SecurityConfig 에서 사용함
        if (this.user.getAdminYN().equals("Y")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }

        return authorities;
    }

    // 사용자의 비밀번호 반환
    @Override
    public String getPassword() {
        return this.user.getUserId();
    }

    // 사용자 이름 반환
    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    // 계정이 만료되었는지를 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // 여기서는 만료되지 않았다고 가정함
    }

    // 계정이 잠겨있지 않은지를 반환
    @Override
    public boolean isAccountNonLocked() {
        return this.user.getLoginOk().equals("Y");
    }

    // 사용자의 크리덴셜(비밀번호 등)이 만료되지 않았는지를 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 여기서는 크리덴셜이 만료되지 않았다고 가정함 (db 테이블에 해당 정보 저장 컬럼 필요함)
    }

    // 사용자 계정이 사용가능 상태인지를 반환
    @Override
    public boolean isEnabled() {
        return this.user.getLoginOk().equals("Y"); // isActivated 컬럼을 db 회원테이블에 추가하고 사용하면 됨
    }
}
