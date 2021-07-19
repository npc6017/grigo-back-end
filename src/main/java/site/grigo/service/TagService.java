package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.grigo.domain.accounttag.AccountTag;
import site.grigo.domain.accounttag.AccountTagRepository;
import site.grigo.domain.tag.Tag;
import site.grigo.domain.tag.TagRepository;
import site.grigo.jwt.JwtProvider;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final AccountTagRepository accountTagRepository;
    private final JwtProvider jwtProvider;

    @PostConstruct
    private void init(){
        Tag spring = new Tag("Spring", "backend");
        Tag android = new Tag("Android", "application");
        tagRepository.save(spring);
        tagRepository.save(android);
    }

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
        //tag에 존재하는지 확인한다.
        for(String tag : tags) {
            //존재하지않는다면, tag를 만들어 repo에 넣는다.
            boolean check = tagRepository.existsByName(tag);
            if(!check) {
                log.info("tag : {}, exist : {}", tag, check);
                tagRepository.save(new Tag(tag));
            }

            //AccountTag Repo에 넣는다.
            AccountTag res = new AccountTag(userEmail, tag);
            accountTagRepository.save(res);
        }
    }
}
