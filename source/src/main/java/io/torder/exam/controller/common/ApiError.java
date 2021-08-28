package io.torder.exam.controller.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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
