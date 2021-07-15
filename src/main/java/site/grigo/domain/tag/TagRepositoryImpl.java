package site.grigo.domain.tag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class TagRepositoryImpl implements TagRepository {
    private final Map<Long, Tag> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    @Override
    public Tag save(Tag tag) {
        log.info("{}", tag.getName());
        tag.setId(++sequence);
        store.put(tag.getId(), tag);
        return tag;
    }

    @Override
    public Optional<List<Tag>> findAll() {
        return Optional.of(new ArrayList<>(store.values()));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Optional<Tag> tag = store.values().stream().filter(
                tag1 -> tag1.getName().equals(name)).findAny();
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Optional<Tag> tag = store.values().stream().filter(
                tag1 -> tag1.getId().equals(id)).findAny();
        return tag;
    }

    @Override
    public boolean tagExist(String tagName) {
        if(store.containsValue(tagName)) return true;
        return false;
    }
}
