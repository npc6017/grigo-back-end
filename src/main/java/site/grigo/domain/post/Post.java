package site.grigo.domain.post;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.grigo.domain.account.Account;
import site.grigo.domain.comment.Comment;
import site.grigo.domain.posttag.PostTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter @Getter
public class Post {
    // GenerationType을 seqeunce나 table로 변경할 필요가 있을지 확인.
    // saveAll과 같은 데이터 입력이 생길 수 있나 생각해보고 변경할지말지 결정.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    /** Post - Account 다대1 관계 설정 */
    @ManyToOne
    private Account account;

    private String content;


    @OneToMany(mappedBy = "post")
    private List<PostTag> tags = new ArrayList<>();
    /** Post - Comment | 1대다 관계 설정 */
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timeStamp;
}
