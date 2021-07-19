package site.grigo.domain.accounttag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.grigo.domain.tag.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountTagRepository extends JpaRepository<AccountTag, Long> {
    public AccountTag save(AccountTag accountTag);

    public Optional<List<Tag>> findAllByEmail(String email);

    public Optional<List<AccountTag>> findByTagName(String tagName);

}
