package io.torder.exam.security.errorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.torder.exam.controller.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증되지 않은 요청에 대한 처리를 정의하는 클래스
 */
@RequiredArgsConstructor
@Component
public class UnauthorizedEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("content-type", "application/json");
        ApiResponse apiResponse = ApiResponse.ERROR(HttpStatus.UNAUTHORIZED, "Authentication error! (cause: Unauthorized)");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
