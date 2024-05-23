package org.ict.boot_react.security.jwt.model.service;

import jakarta.transaction.Transactional;
import org.ict.boot_react.security.jpa.entity.RefreshToken;
import org.ict.boot_react.security.jpa.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {
    // 의존성 주입 방법 1 : @Autowired 사용
//    @Autowired
//    private RefreshTokenRepository refreshTokenRepository;

    // 의존성 주입 방법 2 : final 사용, 권장, 생성자를 통해 사용
    private final RefreshTokenRepository refreshTokenRepository;
    // 반드시 매개변수 있는 생성자 작성해야 함
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
    // 방법 2 -----------------------------------------------------------------------------
    
    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken); // insert 실행 처리
    }

    public Optional<RefreshToken> findBtTokenValue(String tokenValue) {
        return refreshTokenRepository.findByTokenValue(tokenValue); // select 조회 : 토큰객체 조회 (토큰값
    }

    public Boolean existsByRefresh(String tokenValue) {
        return refreshTokenRepository.existsByTokenValue(tokenValue); // select 조회 : 토큰객체 존재여부 확인
    }

    public void deleteByRefresh(String tokenValue) {
        refreshTokenRepository.deleteByTokenValue(tokenValue);
    }

    public Optional<RefreshToken> findByUserId(UUID id) {
        return refreshTokenRepository.findByUserId(id);
    }


}
