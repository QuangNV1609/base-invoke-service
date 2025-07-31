package com.quangnv.msb.configuration.exception;

import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.exception.UnExpectedException;
import com.quangnv.msb.core.funtional.Maybe;
import com.quangnv.msb.utils.ResponseCreator;
import com.quangnv.msb.utils.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
    private final ResponseCreator responseCreator;

    public ApiExceptionHandler(ResponseCreator responseCreator) {
        this.responseCreator = responseCreator;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<StandardResponse> handleException(Throwable ex) {
        log.error(">>>ApiExceptionHandler",ex);
        StandardResponse response = responseCreator.error(ex.getMessage());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(InvocationTargetException.class)
    public ResponseEntity<StandardResponse> handleInvocationTargetException(InvocationTargetException ex) {
        Throwable realException = ex.getTargetException();
        if (realException instanceof UnExpectedException) {
            return handleUnExpectedException((UnExpectedException) realException);
        } else if (realException instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) realException);
        } else if (realException instanceof HttpMessageNotReadableException) {
            return handleHttpMessageNotReadableException((HttpMessageNotReadableException) realException);
        }
        return handleException(realException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(">>>ApiExceptionHandler",ex);
        BindingResult binding = ex.getBindingResult();
        List<String> errors = binding.getFieldErrors().stream().sorted(Comparator.comparing(FieldError::getField)).map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        List<ObjectError> globalErrors = binding.getGlobalErrors();
        for (ObjectError error : globalErrors) {
            errors.add(error.getDefaultMessage());
        }
        String message = String.join(",", errors);
        StandardResponse response = responseCreator.badRequest(message);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(">>>ApiExceptionHandler",ex);
        Throwable rootCause = Maybe.of(ExceptionUtils.getRootCause(ex)).orElse(ex);
        StandardResponse response = responseCreator.error(MAErrorCode.INVALID_DATA_FORMAT, rootCause.getMessage());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(UnExpectedException.class)
    public ResponseEntity<StandardResponse> handleUnExpectedException(UnExpectedException ex) {
        log.error(">>>ApiExceptionHandler",ex);
        return ResponseEntity.ok(responseCreator.error(ex));
    }
}
