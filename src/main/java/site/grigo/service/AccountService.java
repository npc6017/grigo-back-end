package site.grigo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.grigo.domain.Account;
import site.grigo.domain.AccountRepository;

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

    public UserDetails save(Account account){
        return accountRepository.save(account);
    }
}
