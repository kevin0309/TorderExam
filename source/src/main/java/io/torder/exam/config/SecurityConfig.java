package io.torder.exam.config;

import io.torder.exam.model.user.Role;
import io.torder.exam.security.JwtAuthenticationProvider;
import io.torder.exam.security.JwtObject;
import io.torder.exam.security.errorHandler.JwtAccessDeniedHandler;
import io.torder.exam.security.JwtAuthenticationFilter;
import io.torder.exam.security.errorHandler.UnauthorizedEntryPointHandler;
import io.torder.exam.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * spring security에 관련된 설정을 정의하는 클래스
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtObject jwtObject;
    private final JwtTokenConfig jwtTokenConfig;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UnauthorizedEntryPointHandler unauthorizedEntryPointHandler;

    /**
     * 보안설정의 예외가 될 URL 정의
     */
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/static/**");
    }

    /**
     * http 요청에 대하여 수행할 보안설정 정의
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //spring security가 요구하는 기본 csrf 토큰 비활성화
                .headers().disable() //spring security가 제공하는 보안응답헤더 비활성화
                .formLogin().disable() //spring security가 제공하는 폼로그인화면 비활성화
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(unauthorizedEntryPointHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/join").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/**").hasRole(Role.USER.name())
                .accessDecisionManager(accessDecisionManager())
                .anyRequest().permitAll()
                .and()
                //Jwt를 사용하여 인증을 구현하기 때문에 기존의 session management를 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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

    /**
     * 로그인 시 사용될 AuthenticationProvider 빈 등록
     */
    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(JwtObject jwtObject, UserService userService) {
        return new JwtAuthenticationProvider(jwtObject, userService);
    }

    /**
     * AuthenticationManager 빈 등록
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
