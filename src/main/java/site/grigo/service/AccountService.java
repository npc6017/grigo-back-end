package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.grigo.domain.ResponseDTO;
import site.grigo.domain.account.*;
import site.grigo.domain.accounttag.AccountTag;
import site.grigo.domain.accounttag.AccountTagRepositoryImpl;
import site.grigo.error.BusinessException;
import site.grigo.jwt.JwtProvider;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder; // Password 인코딩
    private final JwtProvider jwtProvider;
    private final AccountTagRepositoryImpl accountTagRepositoryImpl;

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

    /** Get Profile Info */
    public ProfileDTO getProfile(String header) {
        Account account = getAccountToToken(header);
        ProfileDTO profile = makeProfileDTO(account);
        return profile;
    }

    public ProfileDTO getProfileFromEmail(String email){
        return makeProfileDTO(accountRepository.findByEmail(email).get());
    }


    /** User Info Update : Phone, Birth */
    public ProfileDTO updateProfile(String header, ProfileDTO profile) {
        Account account = getAccountToToken(header);

        // 수정 및 반영
        account.setPhone(profile.getPhone());
        account.setBirth(profile.getBirth());
        accountRepository.save(account);

        // ProfileDTO 생성 및 반환
        return makeProfileDTO(account);
    }

    /** TODO PassWord Update
     * @param updatePassword
     * @param header*/
    public ResponseDTO updatePassWord(PasswordUpdateDTO updatePassword, String header) {
        Account account = getAccountToToken(header);

        boolean currentPasswordMatches = passwordEncoder.matches(updatePassword.getCurrentPassword(), account.getPassword());
        boolean passwordConfirm = updatePassword.getNewPassword().equals(updatePassword.getNewPasswordConfirm());

        if(!currentPasswordMatches)  {
            return new ResponseDTO(400, "비밀번호가 일치하지 않습니다.");
        }
        if(!passwordConfirm) {
            return new ResponseDTO(400, "새로운 비밀번호가 서로 일치하지 않습니다.");
        }

        account.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        accountRepository.save(account);

        return new ResponseDTO(200, "비밀번호가 성공적으로 변경되었습니다.");
    }

    /* Token으로 Account 조회 */
    private Account getAccountToToken(String header) {
        String token = jwtProvider.resolveToken(header);
        String userEmail = jwtProvider.getUserEmail(token);

        return accountRepository.findByEmail(userEmail).get();
    }
    /* account로 ProfileDTO 생성(필요 없는 데이터 제거) */
    public ProfileDTO makeProfileDTO(Account account) {
        ProfileDTO profile = new ProfileDTO();
        profile.setBirth(account.getBirth());
        profile.setEmail(account.getEmail());
        profile.setPhone(account.getPhone());
        profile.setSex(account.getSex());
        profile.setName(account.getName());
        profile.setStudent_id(account.getStudent_id());
        return profile;
    }

    public List<AccountTag> getAccountTagsFromEmail(String email) {
        return accountTagRepositoryImpl.findAllByEmail(email).get();
    }


}
