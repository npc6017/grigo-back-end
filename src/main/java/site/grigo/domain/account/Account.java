package site.grigo.domain.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter @Setter
public class Account implements UserDetails {
    private Long id;
    private String email;
    private String name;
    private Integer student_id;
    private String phone;
    private String birth;
    private String password;
    private String sex;

    public Account() {
    }

    public Account(String email, String password, String name, String birth, Integer student_id, String sex, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.student_id = student_id;
        this.sex = sex;
        this.phone = phone;
    }

    public void setId(Long id){
        this.id = id;
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
}
