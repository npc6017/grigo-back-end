package site.grigo.domain.posttag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.grigo.domain.post.Post;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    public List<PostTag> findAllByPost(Post post);
}
