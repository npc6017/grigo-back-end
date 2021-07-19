package site.grigo.domain.accounttag;

import site.grigo.domain.tag.TagDTO;

import java.util.List;
import java.util.Optional;

public interface AccountTagRepository {
    public AccountTag save(AccountTag accountTag);

    public Optional<List<TagDTO>> findAllByEmail(String email);

    public Optional<List<AccountTag>> findByTagName(String tagName);

}
