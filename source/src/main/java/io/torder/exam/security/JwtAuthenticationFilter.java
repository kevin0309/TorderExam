package io.torder.exam.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * HTTP 요청의 JWT token을 인증/갱신 하는 필터
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    //"Bearer {JWT token}" 형식의 api-key 필요
    private static final Pattern API_KEY_PATTERN = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
    private final String headerKey;
    private final JwtObject jwtObject;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //인증이 완료된 요청인지 먼저 확인
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            //아니라면 request header 확인
            String authenticationToken = obtainAuthenticationToken(req);

            if (authenticationToken != null) {
                try {
                    JwtObject.Claims claims = jwtObject.verify(authenticationToken);

                    //15분 미만으로 남았다면 토큰을 리프레시 시킴
                    if (checkRemainMillis(claims) < 15 * 60 * 1000) {
                        String refreshedToken = jwtObject.refreshToken(authenticationToken);
                        res.setHeader(headerKey, refreshedToken);
                    }

                    //token의 정보를 기반으로 인증절차 진행
                    String userId = claims.userId;
                    List<GrantedAuthority> authorities = obtainAuthorities(claims);

                    if (userId != null && authorities.size() > 0) {
                        JwtAuthenticationToken jwtAuthentication =
                                new JwtAuthenticationToken(userId, null, authorities);
                        jwtAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
                    }
                } catch (TokenExpiredException e) {
                    System.out.println("Expired token detected!\nremote IP : " + req.getRemoteAddr() + "\ntoken : " + authenticationToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * HTTPServletRequest의 헤더로부터 jwt token을 읽는 메서드
     * @param req "Bearer {JWT token}" 형식의 api-key 필요
     * @return JWT token
     */
    private String obtainAuthenticationToken(HttpServletRequest req) {
        String apiKey = req.getHeader(headerKey);
        if (apiKey != null) {
            try {
                apiKey = URLDecoder.decode(apiKey, StandardCharsets.UTF_8.toString());
                String[] tempApiKey = apiKey.split(" ");
                if (tempApiKey.length == 2) {
                    String scheme = tempApiKey[0]; //해당 어플리케이션에서는 "Bearer"를 고정적으로 사용
                    String jwtToken = tempApiKey[1];
                    return API_KEY_PATTERN.matcher(scheme).matches() ? jwtToken : null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * claims의 만료까지 남은 시간을 구하는 메서드
     */
    private long checkRemainMillis(JwtObject.Claims claims) {
        return claims.getExpiresAt() - System.currentTimeMillis();
    }

    /**
     * claims에서 roles를 읽어오는 메서드
     */
    private List<GrantedAuthority> obtainAuthorities(JwtObject.Claims claims) {
        String[] roles = claims.roles;
        return roles == null || roles.length == 0 ? Collections.emptyList() :
                Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
