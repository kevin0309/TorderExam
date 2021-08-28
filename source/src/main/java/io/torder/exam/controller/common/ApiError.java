package io.torder.exam.controller.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ApiResponse에서 사용되는 error 형식을 정의하는 클래스
 */
@Getter
public class ApiError {

    private final int status;
    private final String message;

    public ApiError(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public ApiError(HttpStatus status, Throwable throwable) {
        this(status, throwable.getMessage());
    }
}
