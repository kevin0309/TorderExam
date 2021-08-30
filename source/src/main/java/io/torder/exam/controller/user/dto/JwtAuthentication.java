package io.torder.exam.controller.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 인증주체(Principal)을 의미하는 클래스
 */
@Getter
@RequiredArgsConstructor
public class JwtAuthentication {

    private final String userId;
    
}
