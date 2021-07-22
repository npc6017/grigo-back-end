package site.grigo.domain.comment;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.grigo.domain.account.Account;
import site.grigo.domain.post.Post;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Comment - Account | 다대1 관계 설정 */
    @ManyToOne
    private Account account;
    /** Comment - Post | 다대1 관계 설정 */
    @ManyToOne
    private Post post;

    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timeStamp;


}
