package io.torder.exam.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;

import java.util.Date;

/**
 * JWT 토큰에 대한 기능을 구현한 클래스
 */
@Getter
public class JwtObject {

    private final String issuer;
    private final String clientSecret;
    private final int expirySecond;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    public JwtObject(String issuer, String clientSecret, int expirySecond) {
        this.issuer = issuer;
        this.clientSecret = clientSecret;
        this.expirySecond = expirySecond;
        this.algorithm = Algorithm.HMAC512(clientSecret); //SHA-512 사용
        this.jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build(); //검증을 위한 객체
    }

    /**
     * 새로운 토큰 발급
     * @return JWT token
     */
    public String newToken(Claims claims) {
        Date currentDate = new Date();
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(issuer);
        builder.withIssuedAt(currentDate);
        if (expirySecond > 0)
            builder.withExpiresAt(new Date(currentDate.getTime() + expirySecond * 1000L));
        builder.withClaim("userId", claims.userId);
        builder.withArrayClaim("roles", claims.roles);
        return builder.sign(algorithm);
    }

    /**
     * JWT 토큰의 유효성을 검사하는 메서드
     * 검사 후 Claims로 wrapping하여 반환
     * @return Claims
     */
    public Claims verify(String token) throws JWTVerificationException {
        return Claims.getInstance(jwtVerifier.verify(token));
    }

    /**
     * 기존의 토큰 정보를 기반으로 새로운 토큰을 발급하는 메서드(기한연장)
     * @return JWT token
     */
    public String refreshToken(String token) throws JWTVerificationException {
        //한번 검증한 후 새로발급
        return newToken(verify(token));
    }

    /**
     * JWT payload에 담길 claim들을 묶어서 관리할 내부클래스 생성
     */
    static class Claims {
        String userId;
        String[] roles;
        Date issuedAt;
        Date expiresAt;

        private Claims() {}

        static Claims getInstance(DecodedJWT decodedJWT) {
            Claims result = new Claims();
            Claim userId = decodedJWT.getClaim("userId");
            Claim roles = decodedJWT.getClaim("roles");
            if (!userId.isNull())
                result.userId = userId.asString();
            if (!roles.isNull())
                result.roles = roles.asArray(String.class);
            result.issuedAt = decodedJWT.getIssuedAt();
            result.expiresAt = decodedJWT.getExpiresAt();
            return result;
        }

        /*static Claims getInstance(String userId, String[] roles) {
            Claims result = new Claims();
            result.userId = userId;
            result.roles = roles;
            return result;
        }*/

        long getIssuedAt() {
            return issuedAt != null ? issuedAt.getTime() : -1;
        }

        long getExpiresAt() {
            return expiresAt != null ? expiresAt.getTime() : -1;
        }
    }
}
