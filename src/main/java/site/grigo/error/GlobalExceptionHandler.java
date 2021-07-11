package site.grigo.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TestException.class)
    protected ResponseEntity<Object> handleTestException(TestException e){
        log.error("TestException", e);
        return new ResponseEntity<>("test", HttpStatus.BAD_REQUEST);
    }
}
