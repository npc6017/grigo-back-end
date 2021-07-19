package site.grigo.domain.posttag;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.post.Post;
import site.grigo.domain.tag.Tag;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class PostTag {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
