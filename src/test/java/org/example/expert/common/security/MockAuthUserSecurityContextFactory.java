package org.example.expert.common.security;

import org.example.expert.config.JwtAuthenticationToken;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockAuthUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // AuthUser 객체 생성
        AuthUser authUser = new AuthUser(
                customUser.userId(),
                customUser.email(),
                customUser.nickname(),
                customUser.role()
        );

        // JWT 인증 토큰 생성 (실제 JWT 토큰이 아닌 테스트용)
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(authUser);
        context.setAuthentication(authentication);

        return context;
    }
}
