package site.grigo.domain.account;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.accounttag.AccountTag;

import java.util.List;

/**
 * Account Update : Phone, Birth | ProfileDTO
 */
@Getter
@Setter
public class ProfileDTO {
    private String email;
    private String name;
    private Integer student_id;
    private String phone;
    private String birth;
    private String sex;
    private List<AccountTag> tags;

    public ProfileDTO() {
    }
}
