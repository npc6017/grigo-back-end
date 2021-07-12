package site.grigo.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    public ProfileDTO() {
    }
}
