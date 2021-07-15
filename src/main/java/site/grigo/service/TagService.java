package site.grigo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.grigo.domain.tag.Tag;
import site.grigo.domain.tag.TagRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        List<Tag> tags = tagRepository.findAll().get();
        return tags;
    }

    public void test() {
        Tag t1 = new Tag("test", "test2");
        Tag t2 = new Tag("test1", "test3");
        tagRepository.save(t1);
        tagRepository.save(t2);
    }
}
