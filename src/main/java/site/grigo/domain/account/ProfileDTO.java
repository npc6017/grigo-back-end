package site.grigo.domain.account;

import lombok.Getter;
import lombok.Setter;
import site.grigo.domain.accounttag.AccountTag;
import site.grigo.domain.tag.TagDTO;

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
    // Account tag를 바로쓰는 것 역시 안좋다고 했으니 이를 개선하는 게 좋을 것 같고,
    //
    private List<TagDTO> tags;

    public ProfileDTO() {
    }
}
