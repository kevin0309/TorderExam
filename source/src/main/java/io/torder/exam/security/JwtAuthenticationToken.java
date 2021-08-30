package io.torder.exam.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * AbstractAuthenticationToken을 상속받아
 * principal, credentials를 정의하는 클래스
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal; //userId
    private String credentials; //password

    public JwtAuthenticationToken(String principal, String credentials) {
        //인증이 되지 않은 상태로 초기화
        super(null);
        super.setAuthenticated(false);

        this.principal = principal;
        this.credentials = credentials;
    }

    protected JwtAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public String getCredentials() {
        return credentials;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }

}
