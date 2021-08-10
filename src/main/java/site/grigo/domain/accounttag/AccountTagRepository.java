package site.grigo.domain.accounttag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.grigo.domain.account.Account;
import site.grigo.domain.tag.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountTagRepository extends JpaRepository<AccountTag, Long> {
    AccountTag save(AccountTag accountTag);

    Optional<List<AccountTag>> findAllByAccount(Account account); /** ++
     * @param tag*/
    Optional<List<AccountTag>> findByTag(Tag tag);

    AccountTag findByAccount(Account account);

    boolean existsByAccountAndTag(Account account, Tag tag);

    AccountTag findByAccountAndTag(Account account, Tag tag);
}
