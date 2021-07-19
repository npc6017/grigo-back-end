package site.grigo.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.grigo.error.exception.BusinessException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(final BusinessException e){
        log.error("BusinessException : {}", e.getMessage());
        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(errorCode.getStatus()));
    }
}