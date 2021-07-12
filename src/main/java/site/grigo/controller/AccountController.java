package site.grigo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.account.Account;
import site.grigo.domain.account.ResponseDTO;
import site.grigo.domain.account.SignUpJson;
import site.grigo.jwt.JwtProvider;
import site.grigo.service.AccountService;
import site.grigo.validator.SignUpValidator;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AccountController {
    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    private final SignUpValidator signUpValidator; // 이메일 검증 객체

    /**
     * SignUpJson으로 파리미터가 들어오는 경우, 검증
     */
    @InitBinder("signUpJson")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator); // 검증 객체 등록
    }

    @PostMapping("/join")
    public ResponseDTO accountJoin(@Valid @RequestBody SignUpJson signUpJson, HttpServletResponse response, Errors errors) {
        /** 검증 조건에 부합하지 않는 경우, 메세지 응답. */
        if (errors.hasErrors()) {
            response.setStatus(404);
            return new ResponseDTO(404, errors.getFieldError().getDefaultMessage());
        }
        /** Account 가입 */
        accountService.join(signUpJson);
        log.info(signUpJson.getEmail() + " " + signUpJson.getPassword());
        response.setStatus(200);
        return new ResponseDTO(200, "Success"); // 리턴 내용은 언제든 수정 가능
    }

    /**
     * MethodArgumentNotValidException 핸들러(객체 변수 어노테이션(@NotBlank 등))
     * 해당 예외가 잡히면, Bad Request 응답코드 400과 함께 에러 메시지를 응답해준다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO processValidationError(MethodArgumentNotValidException exception, HttpServletResponse response) {
        String errorMessage = exception.getFieldError().getDefaultMessage();
        if (exception.hasErrors()) {
            response.setStatus(400);
            if (exception.hasFieldErrors("email")) {
                /** 이메일이 올바른 형식이 아닙니다. */
                errorMessage = "이메일이 " + exception.getFieldError().getDefaultMessage();
            } else if (exception.hasFieldErrors("password")) {
                /** 비밀번호가 8자 이상 50자 미만이어야 합니다. */
                errorMessage = "비밀번호가 " + exception.getFieldError().getDefaultMessage();
            }
            return new ResponseDTO(400, errorMessage);
        }
        return new ResponseDTO();
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseDTO login(@RequestBody Account account, HttpServletResponse response) {
        log.info("email : {}, password : {}", account.getEmail(), account.getPassword());
        //토큰을 만들기 전에, 아이디가 존재하는지, 그리고 비밀번호도 맞는지부터 판별할 것.
        if (accountService.checkAccount(account.getEmail(), account.getPassword())) {
            String token = jwtProvider.createToken(account);
            response.setHeader("Authorization", "bearer " + token);
            return new ResponseDTO(200);
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/test")
    public ResponseDTO test() {
        log.info("heelo?");
        return new ResponseDTO(200, "hello?");
    }
}
