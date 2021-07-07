package site.grigo.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import site.grigo.domain.account.SignUpForm;
import site.grigo.service.account.AccountService;
import site.grigo.validator.SignUpValidator;
import javax.validation.Valid;

@RequestMapping("/account")
@RestController
@RequiredArgsConstructor
@ResponseBody
public class AccountController {

    private final SignUpValidator signUpValidator; // 이메일 검증 객체
    private final AccountService accountService; // Account Service

    /**
     * SignUpForm으로 파리미터가 들어오는 경우, 검증
     */
    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator); // 검증 객체 등록
    }

    @PostMapping("/join")
    public String accountJoin(@Valid @RequestBody SignUpForm signUpForm, Errors errors) {
        /** 검증 조건에 부합하지 않는 경우, 메세지 응답. */
        if(errors.hasErrors()) {
            return errors.getFieldError().getDefaultMessage();
        }
        /** Account 가입 */
        accountService.join(signUpForm);

        return "회원가입 성공"; // 리턴 내용은 언제든 수정 가능
    }



}
