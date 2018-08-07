package com.ynthm.springbootdemo.infrastructure.aop;

import com.ynthm.springbootdemo.infrastructure.bean.ErrorDTO;
import com.ynthm.springbootdemo.infrastructure.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Author : Ynthm
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public void methodArgumentNotValidException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(request.getRequestURI(), ex);
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        try {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMsg.toString());
        } catch (IOException e) {
            log.error("failed to populate response error", e);
        }
    }


    /**
     * 异常处理
     *
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDTO> handException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        log.error(request.getRequestURI(), exception);

        ErrorDTO errorDTO = new ErrorDTO();
        if (exception instanceof ApiException) {//api异常
            ApiException apiException = (ApiException) exception;
            errorDTO.setCode(apiException.getErrorCode());
        } else if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                //打印验证不通过的信息
                System.out.println(item.getMessage());
            }
        } else {//未知异常
            errorDTO.setCode(0L);
        }
        errorDTO.setMessage(exception.getMessage());
        ResponseEntity<ErrorDTO> responseEntity = new ResponseEntity<>(errorDTO, HttpStatus.valueOf(response.getStatus()));
        return responseEntity;
    }

}
