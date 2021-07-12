package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.grigo.domain.account.Account;
import site.grigo.domain.account.AccountRepository;
import site.grigo.domain.account.Profile;
import site.grigo.domain.account.SignUpJson;
import site.grigo.error.BusinessException;
import site.grigo.jwt.JwtProvider;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder; // Password 인코딩
    private final JwtProvider jwtProvider;

    public void join(SignUpJson signUpJson) {
        // 계정 생성
        Account account = new Account(
                signUpJson.getEmail(),
                passwordEncoder.encode(signUpJson.getPassword()),
                signUpJson.getName(),
                signUpJson.getBirth(),
                signUpJson.getStudent_id(),
                signUpJson.getSex(),
                signUpJson.getPhone());
        // 등록
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(username);
        return account.get();
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        return account.get();
    }

    public UserDetails save(Account account) {
        return accountRepository.save(account);
    }

    //아이디가 존재하는지 찾고, 비밀번호 맞는지 확인하기.
    public boolean checkAccount(String email, String password) {
        if (checkEmail(email) && checkPassword(email, password)) return true;
        else return false;
    }

    private boolean checkEmail(String email) {
        if (accountRepository.findByEmail(email).isPresent()) return true;
        throw new BusinessException("아이디가 존재하지 않습니다.");
    }

    private boolean checkPassword(String email, String password) {
        UserDetails account = accountRepository.findByEmail(email).get();

        if (passwordEncoder.matches((CharSequence) password, account.getPassword())) return true;
        log.info("pass : {}, saved pass : {}", password, accountRepository.findByEmail(email).get().getPassword());
        //if(password.equals(accountRepository.findByEmail(email).get().getPassword())) return true;
        throw new BusinessException("비밀번호가 틀립니다.");
    }

    /** User Info Update : Phone, Birth */
    public Account updateProfile(HttpServletRequest request, Profile profile) {
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        String userEmail = jwtProvider.getUserEmail(token);

        Account account = accountRepository.findByEmail(userEmail).get();
        account.setPhone(profile.getPhone());
        account.setBirth(profile.getBirth());

        return accountRepository.save(account);
    }

    /** TODO PassWord Update */
    public void updatePassWord() {

    }

}
