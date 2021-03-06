package io.torder.exam.controller.user;

import io.torder.exam.controller.common.ApiResponse;
import io.torder.exam.controller.user.dto.*;
import io.torder.exam.security.JwtAuthenticationToken;
import io.torder.exam.controller.user.dto.JwtAuthentication;
import io.torder.exam.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * 사용자와 관련된 요청을 핸들링하는 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserRestController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    /**
     * 새로운 사용자 회원가입을 처리하는 요청
     */
    @Async
    @PutMapping("join")
    public CompletableFuture<ApiResponse<JoinResponse>> join(@RequestBody JoinRequest joinRequest) {
        return CompletableFuture.completedFuture(ApiResponse.OK(
                new JoinResponse(userService.join(joinRequest).getUserId(), true)));
    }

    /**
     * 사용자 정보 수정을 처리하는 요청
     * 현재는 비밀번호만 수정함
     */
    @Async
    @PostMapping("info")
    public CompletableFuture<ApiResponse<ModResponse>> modInfo(@AuthenticationPrincipal JwtAuthentication auth,
                @RequestBody ModRequest modRequest) {
        //자기자신 이외의 유저의 경우 에러발생
        if (!modRequest.getId().equals(auth.getUserId()))
            throw new AccessDeniedException("Cannot access other user's info!");

        return CompletableFuture.completedFuture(ApiResponse.OK(
                new ModResponse(userService.modify(auth.getUserId(), modRequest).getUserId(), true)));
    }

    /**
     * 로그인을 처리하는 요청
     * 인증후 JWT 토큰을 발급함
     * @return (String) JWT token
     */
    @Async
    @PostMapping("login")
    public CompletableFuture<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        //임시 토큰 생성
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(loginRequest.getId(), loginRequest.getPw());

        //인증과정은 AuthenticationManger를 통해 진행
        //AuthenticationManger는 등록된 AuthenticationProvider 구현체인
        //io.torder.exam.security.JwtAuthenticationProvider의 supports 메서드를 실행해보고 true를 리턴한다면
        //authenticate 메서드를 최종적으로 실행하게 된다.
        Authentication authentication = authenticationManager.authenticate(authToken);

        //인증이 완료되었다면 SecurityContextHolder에 해당 인증정보 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return CompletableFuture.completedFuture(ApiResponse.OK((String) authentication.getDetails()));
    }
    
}
