package site.grigo.domain.accounttag;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.account.Account;

@Getter
@Setter
public class AccountTag {
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
