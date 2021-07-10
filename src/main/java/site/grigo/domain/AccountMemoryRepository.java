package site.grigo.domain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AccountMemoryRepository implements AccountRepository{
    private static Map<String, UserDetails> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public UserDetails save(Account account) {
        account.setId(++sequence);
        store.put(account.getEmail(), account);
        return account;
    }

    @Override
    public Optional<UserDetails> findByEmail(String email) {
        return Optional.ofNullable(store.get(email));
    }

    @Override
    public List<Account> findAll() {
        return null;
    }
}
