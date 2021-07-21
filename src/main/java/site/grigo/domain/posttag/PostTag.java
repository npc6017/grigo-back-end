package site.grigo.domain.posttag;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.post.Post;
import site.grigo.domain.tag.Tag;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostTag {
    // IDENTITY를 쓰지않는 이유? : 오류나서..
    // postgreSQL에 해당하는 id생성 로직이 없어서 오류가 뜨는 것 같다.
    // 따라서, 모든 DB에서 사용할 수 있는 TABLE을 사용해줬다.
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    public PostTag() {

    }
}
