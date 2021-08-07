package site.grigo.domain.posttag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.grigo.domain.post.Post;
import site.grigo.domain.tag.Tag;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    public List<PostTag> findAllByPost(Post post);
    public PostTag findPostTagByPostAndTag(Post post, Tag tag);
}
