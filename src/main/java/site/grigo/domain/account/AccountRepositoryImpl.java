package site.grigo.domain.account;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepositoryImpl implements AccountRepository{

    private final Map<Long, Account> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    @Override
    public Account save(Account account) {
        account.setId(++sequence);
        store.put(account.getId(), account);
        return account;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        Optional<Account> user = store.values().stream().filter(
                account -> account.getEmail().equals(email)).findAny();
        return user;
    }

    @Override
    public Optional<Account> findByStudentNumber(Integer studentNumber) {
        Optional<Account> user = store.values().stream().filter(
                account -> account.getStudent_id().equals(studentNumber)).findAny();
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.findByEmail(email).isPresent();
    }

    @Override
    public boolean existsByStudentNumber(Integer studentNumber) {
        return this.findByStudentNumber(studentNumber).isPresent();
    }

}
