package io.torder.exam.controller.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Controller의 기본 응답형식을 정의하는 클래스
 */
@Getter
public class ApiResponse<T> {

    private final T response;
    private final ApiError error;
    private final LocalDateTime timestamp;

    public ApiResponse(T response, ApiError error) {
        this.response = response;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * 응답성공
     */
    public static <T> ApiResponse<T> OK(T response) {
        return new ApiResponse<>(response, null);
    }

    /**
     * 응답실패
     */
    public static ApiResponse<?> ERROR(HttpStatus status, Throwable throwable) {
        return new ApiResponse<>(null, new ApiError(status, throwable));
    }

    /**
     * 응답실패
     */
    public static ApiResponse<?> ERROR(HttpStatus status, String message) {
        return new ApiResponse<>(null, new ApiError(status, message));
    }

}
