package site.grigo.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountMemoryRepository implements AccountRepository{
    private static final Map<Long, Account> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Account save(Account account) {
        account.setId(++sequence);
        store.put(account.getId(), account);
        return account;
    }

    @Override
    public Account findByEmail(String email) {
        return store.get(email);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
