package io.torder.exam.controller.common;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Controller에서 발생한 Exception을 핸들링하는 클래스
 */
@ControllerAdvice
public class GeneralExceptionHandler {

    /**
     * HttpHeaders와 ApiResponse를 같이 내려주기 위하여 ResponseEntity를 만드는 메서드
     */
    private ResponseEntity<ApiResponse<?>> response(HttpStatus status, Throwable throwable) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");
        return new ResponseEntity<>(ApiResponse.ERROR(status, throwable), headers, status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return response(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class,
            TypeMismatchException.class, HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class, MultipartException.class,
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        return response(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        return response(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return response(HttpStatus.METHOD_NOT_ALLOWED, e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(Exception e) {
        return response(HttpStatus.FORBIDDEN, e);
    }

    /**
     * 예측하지 못한 이외의 Exception Handling
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

}
