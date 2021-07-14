package site.grigo.domain.accounttag;

import java.util.List;
import java.util.Optional;

public interface AccountTagRepository {
    public AccountTag save(AccountTag accountTag);

    public Optional<List<AccountTag>> findAllByEmail(String email);

    public Optional<List<AccountTag>> findByTagName(String tagName);

}
