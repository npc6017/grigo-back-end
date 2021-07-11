package site.grigo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.grigo.domain.Account;
import site.grigo.domain.AccountRepository;
import site.grigo.error.BusinessException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetails> account = accountRepository.findByEmail(username);
        return account.get();
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<UserDetails> account = accountRepository.findByEmail(email);
        return account.get();
    }

    public UserDetails save(Account account){
        return accountRepository.save(account);
    }

    //아이디가 존재하는지 찾고, 비밀번호 맞는지 확인하기.
    public boolean checkAccount(String email, String password){
        if(checkEmail(email) && checkPassword(email, password)) return true;
        else return false;
    }

    private boolean checkEmail(String email){
        if(accountRepository.findByEmail(email).isPresent()) return true;
        throw new BusinessException("아이디가 존재하지 않습니다.");
    }
    private boolean checkPassword(String email, String password){
        UserDetails account = accountRepository.findByEmail(email).get();
        if(account.getPassword().equals(password)) return true;
        throw new BusinessException("비밀번호가 틀립니다.");
    }
}
