package org.ict.boot_react.security.jpa.repository;

import org.ict.boot_react.security.jpa.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByTokenValue(String tokenValue);
    Boolean existsByTokenValue(String tokenValue);
    void deleteByTokenValue(String tokenValue);
    Optional<RefreshToken> findByUserId(UUID userId);

}
