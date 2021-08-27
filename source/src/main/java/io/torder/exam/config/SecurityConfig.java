package io.torder.exam.config;

import io.torder.exam.security.JwtObject;
import io.torder.exam.security.errorHandler.JwtAccessDeniedHandler;
import io.torder.exam.security.JwtAuthenticationFilter;
import io.torder.exam.security.errorHandler.UnauthorizedEntryPointHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtObject jwtObject;
    private final JwtTokenConfig jwtTokenConfig;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UnauthorizedEntryPointHandler unauthorizedEntryPointHandler;

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //spring security가 요구하는 기본 csrf 토큰 무시
                .headers().disable() //spring security가 제공하는 보안응답헤더 비활성화
                .formLogin().disable() //spring security가 제공하는 폼로그인화면 비활성화
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(unauthorizedEntryPointHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                //.antMatchers("/api/**").hasRole(Role.USER.getCode())
                .accessDecisionManager(accessDecisionManager())
                .anyRequest().permitAll()
                .and()
                //Jwt 인증을 위해 필터체인 변경
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 인증이 완료된 사용자에 대하여 요청을 인가할지 판단
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter()); //특정 "ROLE_~" 을 갖고있는지 확인하는 기본 Voter
        return new UnanimousBased(decisionVoters); //모든 Voter를 통과해야 인가하도록
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenConfig.getHeaderKey(), jwtObject);
    }

}
