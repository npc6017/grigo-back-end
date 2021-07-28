package site.grigo.domain.notification;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.account.Account;
import site.grigo.domain.post.Post;

import javax.persistence.*;

/** Account - Post | 댜대다 관계 */
@Entity
@Getter @Setter
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Notification(Account account, Post post) {
        this.account = account;
        this.post = post;
    }

    public Notification() {

    }
}
