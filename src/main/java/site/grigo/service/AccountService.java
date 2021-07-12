package site.grigo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.grigo.domain.account.Account;
import site.grigo.domain.account.AccountRepository;
import site.grigo.domain.account.SignUpJson;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder; // Password 인코딩

    /**
     * 회원 가입
     */
    public void join(SignUpJson signUpJson) {
        // 계정 생성
        Account account = new Account(
                signUpJson.getEmail(),
                signUpJson.getName(),
                passwordEncoder.encode(signUpJson.getPassword()),
                signUpJson.getBirth(),
                signUpJson.getStudent_id(),
                signUpJson.getSex(),
                signUpJson.getPhone());
        // 등록
        accountRepository.save(account);
    };
}
