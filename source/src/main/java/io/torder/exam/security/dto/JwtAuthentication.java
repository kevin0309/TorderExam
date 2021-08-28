package io.torder.exam.security.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtAuthentication {

    private final Integer seq;
    private final String userId;
}