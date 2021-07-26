package site.grigo.domain.accounttag;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.account.Account;
import site.grigo.domain.post.Post;
import site.grigo.domain.tag.Tag;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AccountTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public AccountTag(){

    }

    /**  ++ */
    public AccountTag(Account account, Tag saveTag) {
        this.account = account;
        this.tag = saveTag;
    }
}
