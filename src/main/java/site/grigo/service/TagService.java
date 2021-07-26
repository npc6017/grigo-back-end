package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.grigo.domain.account.Account;
import site.grigo.domain.account.AccountRepository;
import site.grigo.domain.accounttag.AccountTag;
import site.grigo.domain.accounttag.AccountTagRepository;
import site.grigo.domain.tag.Tag;
import site.grigo.domain.tag.TagRepository;
import site.grigo.jwt.JwtProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final AccountTagRepository accountTagRepository;
    private final AccountRepository accountRepository; // ++
    private final JwtProvider jwtProvider;

    //refactor  필요.
    public List<String> getAllTagNames() {
        List<String> tn = new ArrayList<>();
        List<Tag> tags = tagRepository.findAll();
        for(Tag t : tags)
            tn.add(t.getName());

        return tn;
    }

    public void saveTags(String token, List<String> tags) {
        token = jwtProvider.resolveToken(token);
        String userEmail = jwtProvider.getUserEmail(token);

        /** ++ */
        Optional<Account> account = accountRepository.findByEmail(userEmail);

        //tag에 존재하는지 확인한다.
        for(String tag : tags) {
            AccountTag accountTags = new AccountTag();
            //존재하지않는다면, tag를 만들어 repo에 넣는다.
            Optional<Tag> getTag = tagRepository.findByName(tag);

            // 태그가 존재하지 않을 때,
            if(getTag.isEmpty()) {
                Tag saveTag = tagRepository.save(new Tag(tag));
                checkAndSave(account, accountTags, saveTag);
            } else { // 태그는 이미 존재하고 계정에 추가만 필요할 때,
                checkAndSave(account, accountTags, getTag.get());
            };
        }
    }

    private void checkAndSave(Optional<Account> account, AccountTag accountTags, Tag tag) {
        accountTags.setAccount(account.get());
        accountTags.setTag(tag);
        if (!accountTagRepository.existsByAccountAndTag(account.get(), tag))
            accountTagRepository.save(accountTags);
    }
}
