package site.grigo.domain.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.grigo.domain.accounttag.AccountTag;
import site.grigo.domain.comment.Comment;
import site.grigo.domain.notification.Notification;
import site.grigo.domain.post.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private Integer studentId;
    private String phone;
    private String birth;
    private String password;
    private String sex;

    /** 새로운 알림 확인 여부 false = 읽을 알림 없음 , true = 읽을 알림 존재*/
    private boolean checkNotice = false;

    /** Account - Post | 1대다 관계 설정 */
    @OneToMany(mappedBy = "account")
    private List<Post> posts = new ArrayList<>();
    /** Account - Comment | 1대다 관계 설정 */
    @OneToMany(mappedBy = "account")
    private List<Comment> comments = new ArrayList<>();
    /** Account - Post -> Notification | 다대다 관계 설정 ++*/
    @OneToMany(mappedBy = "account")
    private List<Notification> notifications = new ArrayList<>();
    /** Account - Tag | 다대다 관계 설정 ++*/
    @OneToMany(mappedBy = "account")
    private List<AccountTag> tags = new ArrayList<>();

    public Account() {
    }

    public Account(String email, String password, String name, String birth, Integer studentId, String sex, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.studentId = studentId;
        this.sex = sex;
        this.phone = phone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
