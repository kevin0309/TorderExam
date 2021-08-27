package io.torder.exam.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt.token")
@Component
public class JwtTokenConfig {

    private String headerKey;
    private String issuer;
    private String clientSecret;
    private int expirySeconds;

}
