package site.grigo.domain.tag;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository{
    private final Map<Long, Tag> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Tag save(Tag tag) {
        tag.setId(sequence++);
        store.put(tag.getId(), tag);
        return tag;
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
}
