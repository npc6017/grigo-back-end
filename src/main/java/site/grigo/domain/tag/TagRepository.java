package site.grigo.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag save(Tag tag);
    Optional<Tag> findByName(String name);
    Optional<Tag> findById(Long id);
    List<Tag> findAll();
    boolean existsByName(String tagName);
}