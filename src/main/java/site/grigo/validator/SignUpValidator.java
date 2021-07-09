package site.grigo.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import site.grigo.domain.account.AccountRepository;
import site.grigo.domain.account.SignUpJson;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpJson.class);
    }

    /***
     * @param obj
     * @param errors
     * obj를 SignUpFrom으로 캐스팅,
     * 저장소에서 이메일과 학번이 중복되는지 체크,
     * 검증에 불합하면 해당 사유를 메시지로 설정.
     */
    @Override
    public void validate(Object obj, Errors errors) {
        SignUpJson signUpJson = (SignUpJson) obj;
        if(accountRepository.existsByEmail(signUpJson.getEmail())) {
            errors.rejectValue("email", "Invalid.email",
                    new Object[]{signUpJson.getEmail()}, "이미 사용중인 이메일입니다.");
        }

        if(accountRepository.existsByStudentNumber(signUpJson.getStudent_id())) {
            errors.rejectValue("student_id", "Invalid.student_id",
                    new Object[]{signUpJson.getStudent_id()}, "이미 가입되어 있는 학번입니다.");
        }
    }
}
