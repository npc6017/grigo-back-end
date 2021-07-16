package site.grigo.domain.accounttag;

import org.springframework.stereotype.Repository;
import site.grigo.domain.tag.TagDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountTagRepositoryImpl implements AccountTagRepository{
    private final Map<Long, AccountTag> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    @Override
    public AccountTag save(AccountTag accountTag){
        accountTag.setId(sequence++);
        store.put(accountTag.getId(), accountTag);
        return accountTag;
    }

    @Override
    public Optional<List<TagDTO>> findAllByEmail(String email) {
        List<TagDTO> result = new ArrayList<>();
        for(AccountTag tag : store.values()) {
            if(tag.getEmail().equals(email))
                result.add(new TagDTO(tag.getTagName()));
        }
        return Optional.of(result);
    }

    @Override
    public Optional<List<AccountTag>> findByTagName(String tagName) {
        List<AccountTag> result = new ArrayList<>();
        for(AccountTag tag : store.values()) {
            if(tag.getTagName().equals(tagName))
                result.add(tag);
        }
        return Optional.of(result);
    }
}
