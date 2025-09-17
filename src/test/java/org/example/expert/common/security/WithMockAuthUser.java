package org.example.expert.common.security;

import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockAuthUserSecurityContextFactory.class)
public @interface WithMockAuthUser {
    long userId() default 1L;

    String email() default "test@example.com";

    String nickname() default "테스트유저";

    UserRole role() default UserRole.ROLE_USER;
}