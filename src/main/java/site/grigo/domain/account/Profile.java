package site.grigo.domain.account;

import lombok.Getter;
import lombok.Setter;

/**
 * Account Update : Phone, Birth | ProfileDTO
 */
@Getter
@Setter
public class Profile {
    private String phone;
    private String birth;

    public Profile(String phone, String birth) {
        this.phone = phone;
        this.birth = birth;
    }
}
