package site.grigo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.grigo.domain.account.Account;
import site.grigo.domain.account.AccountRepository;
import site.grigo.domain.account.SignUpForm;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder; // Password 인코딩

    /**
     * 회원 가입
     */
    public void join(SignUpForm signUpForm) {
        // 계정 생성
        Account account = new Account(
                signUpForm.getEmail(),
                signUpForm.getName(),
                passwordEncoder.encode(signUpForm.getPassword()),
                signUpForm.getBirth(),
                signUpForm.getStudent_id(),
                signUpForm.getSex(),
                signUpForm.getPhone());
        // 등록
        accountRepository.save(account);
    };
}
