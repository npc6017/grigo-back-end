package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import site.grigo.domain.account.JoinDTO;
import site.grigo.domain.account.SignUpJson;
import site.grigo.service.AccountService;
import site.grigo.validator.SignUpValidator;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
@Slf4j
@RequestMapping("/account")
@RestController
@RequiredArgsConstructor
@ResponseBody
public class AccountController {

    private final SignUpValidator signUpValidator; // 이메일 검증 객체
    private final AccountService accountService; // Account Service

    /**
     * SignUpJson으로 파리미터가 들어오는 경우, 검증
     */
    @InitBinder("signUpJson")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator); // 검증 객체 등록
    }

    @PostMapping("/join")
    public JoinDTO accountJoin(@Valid @RequestBody SignUpJson signUpJson, HttpServletResponse response, Errors errors){
        /** 검증 조건에 부합하지 않는 경우, 메세지 응답. */
        if(errors.hasErrors()) {
            response.setStatus(404);
            return new JoinDTO(404, errors.getFieldError().getDefaultMessage());
        }
        /** Account 가입 */
        accountService.join(signUpJson);
        log.info(signUpJson.getEmail() + " " + signUpJson.getPassword());
        response.setStatus(200);
        return new JoinDTO(200, "Success"); // 리턴 내용은 언제든 수정 가능
    }

    /**
     * MethodArgumentNotValidException 핸들러,
     * 해당 예외가 잡히면, Bad Request 응답코드 400과 함께 에러 메시지를 응답해준다.
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JoinDTO processValidationError(MethodArgumentNotValidException exception, HttpServletResponse response) {
        String errorMessage = exception.getFieldError().getDefaultMessage();
        if(exception.hasErrors()) {
            response.setStatus(400);
            if(exception.hasFieldErrors("email")) {
                /** 1순위. 이메일이 올바른 형식이 아닙니다. 2순위. 이메일이 중복됩니다. */
                errorMessage = "이메일이 " + exception.getFieldError().getDefaultMessage();
            } else if (exception.hasFieldErrors("password")) {
                /** 비밀번호가 8자 이상 50자 미만이어야 합니다. */
                errorMessage = "비밀번호가 " + exception.getFieldError().getDefaultMessage();
            }
            return new JoinDTO(400, errorMessage);
        }
        return new JoinDTO();
    }

}
