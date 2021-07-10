package site.grigo.domain;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    UserDetails save(Account account);
    Optional<UserDetails> findByEmail(String email);
    List<Account> findAll();
}
