package io.torder.exam.security;

import io.torder.exam.model.user.User;
import io.torder.exam.controller.user.dto.JwtAuthentication;
import io.torder.exam.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.ClassUtils;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtObject jwtObject;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

        try {
            //유효한 principal, credentials 인지 UserService를 통해 검증
            String id = String.valueOf(jwtAuthenticationToken.getPrincipal());
            String pw = jwtAuthenticationToken.getCredentials();
            User user = userService.validateUser(id, pw);
            
            //새로운 토큰 생성 (검증이 완료된 사용자를 뜻함)
            JwtAuthenticationToken authenticated =
                    new JwtAuthenticationToken(new JwtAuthentication(user.getUserId()),
                            null, AuthorityUtils.createAuthorityList(user.getRole().getCode()));

            //JWT token도 새로 발급
            String apiToken = jwtObject.newToken(JwtObject.Claims.getInstance(user.getUserId(),
                    new String[]{user.getRole().getCode()}));

            authenticated.setDetails(apiToken);
            return authenticated;
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    /**
     * authentication에 JwtAuthenticationToken를 할당할 수 있는지 확인
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return ClassUtils.isAssignable(JwtAuthenticationToken.class, authentication);
    }

}
