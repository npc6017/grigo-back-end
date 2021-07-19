package site.grigo.domain.accounttag;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class AccountTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String tagName;

    public AccountTag(){

    }

    public AccountTag(String email, String tagName){
        this.email = email;
        this.tagName = tagName;
    }
}
