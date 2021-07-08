package site.grigo.domain;

import java.util.List;

public interface AccountRepository {
    Account save(Account account);
    Account findByEmail(String email);
    List<Account> findAll();
}
