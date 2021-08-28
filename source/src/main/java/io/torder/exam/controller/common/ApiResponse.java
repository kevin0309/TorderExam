package io.torder.exam.controller.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

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

    public static <T> ApiResponse<T> OK(T response) {
        return new ApiResponse<>(response, null);
    }

    public static ApiResponse<?> ERROR(HttpStatus status, Throwable throwable) {
        return new ApiResponse<>(null, new ApiError(status, throwable));
    }

    public static ApiResponse<?> ERROR(HttpStatus status, String message) {
        return new ApiResponse<>(null, new ApiError(status, message));
    }
}
